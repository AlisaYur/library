package ua.nure.library.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.user.UserRepository;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.Messages;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class ChangeManagerStateCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    UserRepository userRepository = RepositoryFactoryProducer.repositoryFactory()
        .getUserRepository();

    try {
      Long userId = Long.parseLong(request.getParameter("id"));
      boolean isManagerActive = Boolean.parseBoolean(request.getParameter("active"));
      User userToUpdate;
      userToUpdate = userRepository.getById(userId);
      userToUpdate.setActive(isManagerActive);
      userRepository.update(userId, userToUpdate);
    } catch (NumberFormatException e) {
      log.error(Messages.NUMBER_FORMAT_EX, e);
    } catch (DaoException e) {
      String errorMessage = "Error delete reader!";
      log.error(errorMessage);
      return ErrorRedirect.ERROR_MANAGERS_URL + Messages.ERROR_GET_MANAGER;
    }
    return "json";
  }
}
