package ua.nure.library.web.controller.command;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.genre.GenreRepository;
import ua.nure.library.model.book.entity.Genre;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class UpdateGenreCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    GenreRepository genreRepository = RepositoryFactoryProducer.repositoryFactory()
        .getGenreRepository();

    String returnPage = Path.LIST_GENRE;
    try {
      request.setCharacterEncoding(ConsEncoding.UTF_8);

      final Long genreId = Long.parseLong(request.getParameter("id"));
      final String genreName = request.getParameter("genreName");
      if (genreName.equals("") || genreName.equals(" ")) {
        log.error(Messages.ERROR_UPDATE_GENRE_WITH_EMPTY_VALUE);
        returnPage = ErrorRedirect.ERROR_GENRES_URL + Messages.ERROR_UPDATE_GENRE_WITH_EMPTY_VALUE;
      } else {
        Genre genreToUpdate = genreRepository.getById(genreId);
        genreToUpdate.setName(genreName);
        genreRepository.update(genreId, genreToUpdate);
        String booksAttr = "books";
        request.getServletContext().removeAttribute(booksAttr);
      }
    } catch (IOException e) {
      log.info(Messages.ERROR, e);
    } catch (DaoException e) {
      log.error(Messages.ERROR_UPDATE_GENRE, e);
      returnPage = ErrorRedirect.ERROR_GENRES_URL + Messages.ERROR_UPDATE_GENRE;
    } catch (NumberFormatException e) {
      log.error(Messages.UNABLE_PARSE_LONG_FROM_GENRE, e);
      returnPage = ErrorRedirect.ERROR_GENRES_URL + Messages.UNABLE_PARSE_LONG_FROM_GENRE;
    }
    return returnPage;
  }
}
