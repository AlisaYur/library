package ua.nure.library.web.controller.command;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.role.dao.RoleRepository;
import ua.nure.library.model.role.entity.RoleName;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.model.verifytoken.dao.VerifyTokenRepository;
import ua.nure.library.model.verifytoken.entity.VerifyToken;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Path;
import ua.nure.library.util.email.ValidateFields;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.util.sendgrid.EmailSender;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class SignUpCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String returnPage;
    try {
      request.setCharacterEncoding(ConsEncoding.UTF_8);

      String login = request.getParameter("login");
      String pass = request.getParameter("password");
      String name = request.getParameter("name");
      String email = request.getParameter("email");

      boolean loginMatchesPattern = ValidateFields.validateLogin(login);
      boolean emailMatchesPattern = ValidateFields.validateEmail(email);
      boolean nameMatchesPattern = ValidateFields.validateName(name);
      if (checkUserInput(login, pass, email, name)
          || !loginMatchesPattern
          || !emailMatchesPattern
          || !nameMatchesPattern) {
        String errorMessage = "Error sign up, enter correct values in English";
        log.error(errorMessage);
        returnPage = ErrorRedirect.ERROR_SIGN_UP_URL + errorMessage;
      } else {
        Reader reader = configureReaderFromRequest(name, login, pass, email);
        returnPage = sendResponseReaderAfterRegistration(reader);
      }
    } catch (IOException e) {
      String errorMessage = "Error sign_up, server return error";
      log.error(errorMessage, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + errorMessage;
    } catch (DaoException e) {
      String errorMessage = "Error, sign_up, unable to select user";
      log.error(errorMessage + e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + errorMessage;
    }
    return returnPage;
  }

  private String sendResponseReaderAfterRegistration(Reader reader) throws DaoException {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();

    if (checkUniqueReader(reader, readerRepository)) {
      readerRepository.create(reader);

      EmailSender emailSender = new EmailSender();
      VerifyTokenRepository verifyTokenRepository =
          RepositoryFactoryProducer.repositoryFactory().getVerifyTokenRepository();
      VerifyToken verifyToken = new VerifyToken(reader);
      verifyTokenRepository.create(verifyToken);
      emailSender.sendHTML(reader.getEmail(),
          AuthVerificationMessage.generateValidateMessage(verifyToken.getToken()), "Welcome!");
      return Path.CONFIRM_SIGN_UP;
    } else {
      return Path.SIGN_UP;
    }
  }

  private boolean checkUniqueReader(Reader reader, ReaderRepository readerRepository)
      throws DaoException {
    return readerRepository.isUniqueReaderAndUser(reader);
  }

  private Reader configureReaderFromRequest(String name, String login, String pass, String email)
      throws DaoException {
    RoleRepository roleRepository = RepositoryFactoryProducer.repositoryFactory()
        .getRoleRepository();
    Reader reader = new Reader();
    reader.setName(name.trim());
    reader.setLogin(login.trim());
    reader.setPassword(pass.trim());
    reader.setEmail(email);
    reader.setActive(false);

    reader.setRole(roleRepository.getByName(RoleName.READER_ROLE));
    return reader;
  }

  private boolean checkUserInput(String login, String pass, String email, String name) {
    return login.equals("") || login.equals(" ") || pass.equals("") || pass.equals(" ")
        || name.equals("") || name.equals(" ") || email.equals("") || email.equals(" ");
  }
}
