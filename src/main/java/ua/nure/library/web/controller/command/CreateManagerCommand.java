package ua.nure.library.web.controller.command;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.role.dao.RoleRepository;
import ua.nure.library.model.role.entity.RoleName;
import ua.nure.library.model.user.dao.user.UserRepository;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class CreateManagerCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    UserRepository userRepository = RepositoryFactoryProducer.repositoryFactory()
        .getUserRepository();
    RoleRepository roleRepository = RepositoryFactoryProducer.repositoryFactory()
        .getRoleRepository();

    String returnPage = Path.ADMIN_MANAGERS_LIST;
    try {
      request.setCharacterEncoding(ConsEncoding.UTF_8);
      String managerName = request.getParameter("managerName");
      String managerLogin = request.getParameter("managerLogin");
      String managerEmail = request.getParameter("managerEmail");
      String managerPass = request.getParameter("managerPass");

      Optional<User> managerLoginFromDatabase = Optional
          .ofNullable(userRepository.getByLogin(managerLogin));
      Optional<User> managerEmailFromDatabase = Optional
          .ofNullable(userRepository.getByEmail(managerEmail));
      if (validateManagerData(managerName, managerLogin, managerEmail, managerPass)) {
        log.error(Messages.ERROR_SAVE_MANAGER_EMPTY);
        returnPage = ErrorRedirect.ERROR_MANAGERS_URL + Messages.ERROR_SAVE_MANAGER_EMPTY;
      } else if (managerLoginFromDatabase.isPresent() || managerEmailFromDatabase.isPresent()) {
        log.error(Messages.ERROR_MANAGER_ALREADY_EXISTS);
        returnPage = ErrorRedirect.ERROR_MANAGERS_URL + Messages.ERROR_SAVE_MANAGER_EMPTY;
      } else {
        userRepository.create(User.builder()
            .name(managerName)
            .login(managerLogin)
            .email(managerEmail)
            .password(managerPass)
            .isActive(true)
            .role(roleRepository.getByName(RoleName.LIBRARIAN_ROLE))
            .build());
      }
    } catch (IOException e) {
      String errorMessage = "Error!";
      log.error(errorMessage, e);
    } catch (DaoException e) {
      String errorMessage = "Error, unable to save manager!";
      log.error(errorMessage, e);
      returnPage = ErrorRedirect.ERROR_MANAGERS_URL + errorMessage;
    }
    return returnPage;
  }

  private boolean validateManagerData(String managerName, String managerLogin,
      String managerEmail,
      String managerPass) {
    return managerName.equals("")
        || managerName.equals(" ")
        || managerLogin.equals("")
        || managerLogin.equals(" ")
        || managerEmail.equals(" ")
        || managerEmail.equals("")
        || managerPass.equals("")
        || managerPass.equals(" ");
  }
}
