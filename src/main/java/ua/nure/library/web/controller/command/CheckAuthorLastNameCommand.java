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
import ua.nure.library.model.book.dao.author.AuthorRepository;
import ua.nure.library.model.book.entity.Author;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.author.validation.AjaxAuthorData;

/**
 * @author Artem Kudria
 */
@Log4j
public class CheckAuthorLastNameCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String returnResult = "json";
    try {
      Gson gson = new Gson();
      Optional<AjaxAuthorData> authorDataFromRequest = Optional
          .ofNullable(getAuthorDataFromRequest(gson, request));
      returnResult = sendResponse(authorDataFromRequest, response, gson);
    } catch (IOException e) {
      String errorMsq = "Error check Author last name, server return error";
      log.error(errorMsq, e);
    } catch (JsonSyntaxException e) {
      String errorMsq = "Json syntax Error ";
      log.error(errorMsq, e);
    }
    return returnResult;
  }

  /**
   * Send response after validation author input
   *
   * @param ajaxAuthorData request parameters
   * @param response HttpServletRespnse
   * @param gson json parser
   * @throws IOException If error getting print writer from response
   */
  @SuppressWarnings("Optional<AjaxAuthorData> from ajax request")
  private String sendResponse(Optional<AjaxAuthorData> ajaxAuthorData, HttpServletResponse response,
      Gson gson) throws IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    response.setCharacterEncoding(ConsEncoding.UTF_8);

    final String inputInvalid = "invalid value!";
    final String authorFound = "author found!";

    AjaxAuthorData responseAjax = new AjaxAuthorData();
    if (ajaxAuthorData.isPresent()
        && (null == ajaxAuthorData.get().getLastName()
        || ajaxAuthorData.get().getLastName().equalsIgnoreCase(""))) {
      responseAjax.setLastName(inputInvalid);
      out.print(responseAjax);
      out.flush();
      return "json";
    }

    Author author =
        ajaxAuthorData.map(ajaxAuthorData1 -> getAuthorOnValidation(ajaxAuthorData1.getLastName()))
            .orElse(null);
    if (author != null && author.getLastName() != null) {
      responseAjax.setLastName(authorFound);
    }
    out.print(gson.toJson(responseAjax));
    out.flush();
    return "json";
  }

  private AjaxAuthorData getAuthorDataFromRequest(Gson gson, HttpServletRequest request) {
    String ajaxParam = "requestData";
    String ajaxFromRequest = request.getParameter(ajaxParam);
    return gson.fromJson(ajaxFromRequest, AjaxAuthorData.class);
  }

  private Author getAuthorOnValidation(String authorLastName) {
    AuthorRepository authorRepository = RepositoryFactoryProducer.repositoryFactory()
        .getAuthorRepository();
    try {
      Author author = authorRepository.getByLastName(authorLastName);

      if (author != null) {
        return author;
      }
    } catch (DaoException e) {
      String messageError = "Error get Author from db" + e;
      log.error(messageError);
    }
    return null;
  }
}
