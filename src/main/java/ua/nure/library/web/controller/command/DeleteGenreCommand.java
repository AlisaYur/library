package ua.nure.library.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.genre.GenreRepository;
import ua.nure.library.model.book.entity.Genre;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class DeleteGenreCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    GenreRepository genreRepository = RepositoryFactoryProducer.repositoryFactory()
        .getGenreRepository();

    String returnPage = Path.LIST_GENRE;
    try {
      final Long genreId = Long.parseLong(request.getParameter("id"));
      final Genre genreToDelete = genreRepository.getById(genreId);
      genreRepository.delete(genreToDelete);

    } catch (DaoException e) {
      log.error(Messages.UNABLE_DELETE_GENRE, e);
      returnPage = ErrorRedirect.ERROR_GENRES_URL + Messages.UNABLE_DELETE_GENRE;
    } catch (NumberFormatException e) {
      log.error(Messages.UNABLE_PARSE_LONG_FROM_GENRE, e);
      returnPage = ErrorRedirect.ERROR_GENRES_URL + Messages.UNABLE_PARSE_LONG_FROM_GENRE;
    }
    return returnPage;
  }
}
