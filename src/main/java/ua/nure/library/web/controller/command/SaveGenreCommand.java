package ua.nure.library.web.controller.command;

import java.io.IOException;
import java.util.Optional;
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
public class SaveGenreCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    GenreRepository genreRepository = RepositoryFactoryProducer.repositoryFactory()
        .getGenreRepository();

    String returnPage = Path.LIST_GENRE;
    try {
      request.setCharacterEncoding(ConsEncoding.UTF_8);
      String genreName = request.getParameter("genreName");

      Optional<String> genreNameFromDatabase = Optional
          .ofNullable(genreRepository.getByName(genreName).getName());
      if (genreName.equals("") || genreName.equals(" ")) {
        log.error(Messages.ERROR_SAVE_GENRE_WITH_EMPTY_VALUE);
        returnPage = ErrorRedirect.ERROR_GENRES_URL + Messages.ERROR_SAVE_GENRE_WITH_EMPTY_VALUE;
      } else if (genreNameFromDatabase.isPresent()) {
        log.error(Messages.ERROR_GENRE_EXISTS);
        returnPage = ErrorRedirect.ERROR_GENRES_URL + Messages.ERROR_GENRE_EXISTS;
      } else {
        genreRepository.create(Genre.builder().name(genreName).build());
      }
    } catch (IOException e) {
      log.error(Messages.ERROR, e);
    } catch (DaoException e) {
      log.error(Messages.ERROR_SAVE_GENRE, e);
      returnPage = ErrorRedirect.ERROR_GENRES_URL + Messages.ERROR_SAVE_GENRE;
    }
    return returnPage;
  }
}
