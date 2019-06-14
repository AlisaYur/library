package ua.nure.library.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.author.AuthorRepository;
import ua.nure.library.model.book.entity.Author;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class SaveAuthorCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    AuthorRepository authorService = RepositoryFactoryProducer.repositoryFactory()
        .getAuthorRepository();

    String returnPage = Path.LIST_AUTHORS;
    try {
      String firstNameParam = "firstName";
      String lastNameParam = "lastName";

      String authorFirstName = request.getParameter(firstNameParam);
      String authorLastName = request.getParameter(lastNameParam);

      Author author = Author.builder().firstName(authorFirstName).lastName(authorLastName).build();

      if (checkAuthorLastName(authorService, authorLastName)) {
        authorService.create(author);
      } else {
        returnPage = ErrorRedirect.ERROR_AUTHORS_URL + Messages.ERROR_CREATE_AUTHOR;
      }
    } catch (DaoException e) {
      log.error(Messages.ERROR_CREATE_AUTHOR, e);
      returnPage = ErrorRedirect.ERROR_AUTHORS_URL + Messages.ERROR_CREATE_AUTHOR;
    }
    return returnPage;
  }

  private boolean checkAuthorLastName(AuthorRepository authorService, String authorLastName)
      throws DaoException {
    return authorService.getByLastName(authorLastName).getLastName() == null;
  }
}
