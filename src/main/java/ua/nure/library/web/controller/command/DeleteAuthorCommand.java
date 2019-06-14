package ua.nure.library.web.controller.command;

import java.util.Optional;
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
public class DeleteAuthorCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    AuthorRepository authorRepository = RepositoryFactoryProducer.repositoryFactory()
        .getAuthorRepository();

    String returnPage = Path.LIST_AUTHORS;
    try {
      Long authorId = getAuthorId(request);
      Author authorToDelete = authorRepository.getById(authorId);
      authorRepository.delete(authorToDelete);
    } catch (DaoException | NumberFormatException e) {
      log.error(Messages.ERROR_DELETE_AUTHOR, e);
      returnPage = ErrorRedirect.ERROR_AUTHORS_URL + Messages.ERROR_DELETE_AUTHOR;
    }
    return returnPage;
  }

  private Long getAuthorId(HttpServletRequest request) {
    final String idParam = "id";
    final Optional<String> authorId = Optional.of(request.getParameter(idParam).trim());
    return Long.parseLong(authorId.get());
  }
}
