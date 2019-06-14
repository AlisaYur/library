package ua.nure.library.web.controller.command;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.author.AuthorRepository;
import ua.nure.library.model.book.entity.Author;
import ua.nure.library.util.Messages;
import ua.nure.library.util.PagesPath;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class AuthorsCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {

    AuthorRepository authorRepository = RepositoryFactoryProducer.repositoryFactory()
        .getAuthorRepository();
    String returnPage = PagesPath.ALL_AUTHORS;
    try {
      List<Author> authors = authorRepository.getAll();

      String authorsAttribute = "authors";
      request.setAttribute(authorsAttribute, authors);
    } catch (DaoException e) {
      log.info(Messages.ERROR_GET_AUTHORS, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_GET_AUTHORS;
    }
    return returnPage;
  }
}
