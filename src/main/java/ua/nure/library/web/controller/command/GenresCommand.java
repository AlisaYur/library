package ua.nure.library.web.controller.command;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.genre.GenreRepository;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.PagesPath;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class GenresCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    GenreRepository genreRepository = RepositoryFactoryProducer.repositoryFactory()
        .getGenreRepository();

    String returnPage = PagesPath.LIST_GENRES;
    try {
      request.setCharacterEncoding(ConsEncoding.UTF_8);
      request.setAttribute("genres", genreRepository.getAll());
    } catch (IOException e) {
      log.error(Messages.ERROR, e);
      returnPage = ErrorRedirect.ERROR_GENRES_URL + Messages.ERROR;
    } catch (DaoException e) {
      log.error(Messages.ERROR_GET_GENRES, e);
      returnPage = ErrorRedirect.ERROR_GENRES_URL + Messages.ERROR_GET_GENRES;
    }
    return returnPage;
  }
}
