package ua.nure.library.web.controller.command;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.user.UserRepository;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.Messages;
import ua.nure.library.util.PagesPath;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class ManagersCommand implements Command {


  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    UserRepository userRepository = RepositoryFactoryProducer.repositoryFactory()
        .getUserRepository();

    String returnPage = PagesPath.LIST_MANAGERS;
    try {
      List<User> managers = userRepository.getAllManagers()
          .stream()
          .sorted(Comparator.comparing(User::getId))
          .collect(Collectors.toList());
      request.setAttribute("managers", managers);
    } catch (DaoException e) {
      log.error(Messages.ERROR_GET_MANAGERS);
      returnPage = ErrorRedirect.ERROR_MANAGERS_URL + Messages.ERROR_GET_MANAGERS;
    }
    return returnPage;
  }
}
