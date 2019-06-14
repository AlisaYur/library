package ua.nure.library.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.user.UserRepository;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class DeleteManagerCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    UserRepository userRepository = RepositoryFactoryProducer.repositoryFactory()
        .getUserRepository();

    String returnPage = Path.ADMIN_MANAGERS_LIST;
    try {
      Long readerId = Long.parseLong(request.getParameter("id"));

      final User user = userRepository.getById(readerId);
      if (user == null) {
        return returnPage;
      }
      userRepository.delete(user);
    } catch (NumberFormatException e) {
      log.error(Messages.NUMBER_FORMAT_EX, e);
    } catch (DaoException e) {
      log.error(Messages.ERROR_DELETE_MANAGER);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_DELETE_MANAGER;
    }
    return returnPage;
  }
}
