package ua.nure.library.web.controller.command;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.genre.GenreRepository;
import ua.nure.library.model.book.entity.Genre;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.genre.validation.AjaxGenreData;

/**
 * @author Artem Kudria
 */
@Log4j
public class CheckGenreNameCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    try {
      Gson gson = new Gson();
      String ajaxFromRequest = request.getParameter("requestData");
      AjaxGenreData ajaxGenreData = gson.fromJson(ajaxFromRequest, AjaxGenreData.class);

      AjaxGenreData responseAjax = new AjaxGenreData();

      PrintWriter out = response.getWriter();
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      final String inputInvalid = "invalid input!";
      final String authorFound = "genre found!";

      if (ajaxGenreData.getName() == null || ajaxGenreData.getName().equalsIgnoreCase("")) {
        responseAjax.setName(inputInvalid);
        out.print(responseAjax);
        out.flush();
        return "json";
      }

      Genre genre = getGenreOnValidation(ajaxGenreData.getName());
      if (genre != null && genre.getName() != null) {
        responseAjax.setName(authorFound);
      }

      out.print(gson.toJson(responseAjax));
      out.flush();
    } catch (IOException e) {
      log.error("Error check Genre name, server return error", e);
    } catch (JsonSyntaxException e) {
      log.error("Json syntax Error ", e);
    }
    return "json";
  }

  private Genre getGenreOnValidation(String genreName) {
    GenreRepository genreRepository = RepositoryFactoryProducer.repositoryFactory()
        .getGenreRepository();
    try {
      Optional<Genre> genre = Optional.ofNullable(genreRepository.getByName(genreName));

      if (genre.isPresent()) {
        return genre.get();
      }

    } catch (DaoException e) {
      String messageError = "Error get Genre from db" + e;
      log.error(messageError);
    }
    return null;
  }
}
