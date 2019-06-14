package ua.nure.library.web.controller.command;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.role.entity.RoleName;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.passwordUtil.PasswordEncoder;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.util.uservalidation.UserValidation;
import ua.nure.library.web.controller.ajax.reader.penalty.CheckOrder;
import ua.nure.library.web.controller.command.constans.SessionConstant;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class SignInCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String returnPage;
    try {
      returnPage = validateUser(request);
    } catch (NoSuchAlgorithmException e) {
      log.error(Messages.PAS_NOT_MATCH, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.PAS_NOT_MATCH;
    } catch (IOException e) {
      log.error(Messages.ERROR_SIGH_IN, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_SIGH_IN;
    } catch (DaoException e) {
      log.error(Messages.ERROR_LOGIN);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_LOGIN;
    }
    return returnPage;
  }

  private String validateUser(HttpServletRequest request)
      throws DaoException, IOException, NoSuchAlgorithmException {
    String userNameParam = "username";
    String passParam = "password";
    String username = request.getParameter(userNameParam);
    String password = request.getParameter(passParam);

    User user = UserValidation.getUserOnValidationByLogin(username);
    if (user.getRole().getName() == RoleName.READER_ROLE && checkReaderOrdersOnPenalty(
        (Reader) user)) {
      request.getSession().setAttribute("readerIsPenalty", true);
    }
    return checkUserAndSendRedirect(user, password, request);
  }

  private String checkUserAndSendRedirect(User user, String password, HttpServletRequest request)
      throws IOException, NoSuchAlgorithmException {
    if (user.isActive() && null != user.getLogin() && PasswordEncoder
        .matches(password, user.getPassword())) {
      request.getSession(false).setAttribute("user", user);
      request.getSession(false).setAttribute("roles", user.getRole());
      request.getSession(false).setMaxInactiveInterval(SessionConstant.MAX_INACTIVE_INTERVAL);
      return Path.MAIN_MENU;
    }
    return Path.SIGN_IN;
  }

  private boolean checkReaderOrdersOnPenalty(Reader reader) throws DaoException {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();
    List<Order> orderList = readerRepository.getAllOrdersReader(reader.getLogin());
    return CheckOrder.checkListOrdersOnPenalty(orderList);
  }
}
