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
public class UpdateAuthorCommand implements Command {


  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    AuthorRepository authorService = RepositoryFactoryProducer.repositoryFactory()
        .getAuthorRepository();

    String returnPage = Path.LIST_AUTHORS;
    try {
      String idParam = "id";
      String firstNameParam = "firstName";
      String lastNameParam = "lastName";

      final Long authorId = Long.parseLong(request.getParameter(idParam));
      final String authorFirstName = request.getParameter(firstNameParam);
      final String authorLastName = request.getParameter(lastNameParam);
      Author authorToUpdate = authorService.getById(authorId);
      if (authorService.getByLastName(authorLastName).getLastName() != null) {
        log.error(Messages.ERROR_UPDATE_AUTHOR_ALREADY_EXISTS);
        return ErrorRedirect.ERROR_AUTHORS_URL + Messages.ERROR_UPDATE_AUTHOR_ALREADY_EXISTS;
      }
      authorToUpdate.setFirstName(authorFirstName);
      authorToUpdate.setLastName(authorLastName);
      authorService.update(authorId, authorToUpdate);
      String booksAttr = "books";
      request.getServletContext().removeAttribute(booksAttr);

    } catch (DaoException | NumberFormatException e) {
      log.error(Messages.ERROR_UPDATE_AUTHOR);
      returnPage = ErrorRedirect.ERROR_AUTHORS_URL + Messages.ERROR_UPDATE_AUTHOR;
    }
    return returnPage;
  }
}
