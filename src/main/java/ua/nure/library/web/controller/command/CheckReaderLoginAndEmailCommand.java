package ua.nure.library.web.controller.command;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.auth.validation.AjaxLoginAndEmail;

/**
 * @author Artem Kudria
 */
@Log4j
public class CheckReaderLoginAndEmailCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    try {
      Gson gson = new Gson();
      String requestParamFromAjax = "requestData";
      String requestData = request.getParameter(requestParamFromAjax);
      AjaxLoginAndEmail ajaxLoginAndEmail = gson.fromJson(requestData, AjaxLoginAndEmail.class);

      Reader reader = new Reader();
      reader.setLogin(ajaxLoginAndEmail.getLogin());
      reader.setEmail(ajaxLoginAndEmail.getEmail());

      return responseAjax(reader, response, gson);
    } catch (JsonSyntaxException e) {
      String errorMessage = "Error json syntax when check login and email " + e;
      log.error(errorMessage);
    } catch (DaoException | IOException e) {
      String errorMessage = "Error send response";
      log.error(errorMessage);
    }
    return "json";
  }

  private String responseAjax(Reader reader, final HttpServletResponse response, Gson gson)
      throws DaoException, IOException {
    boolean isUniqueReader = RepositoryFactoryProducer
        .repositoryFactory()
        .getReaderRepository()
        .isUniqueReaderAndUser(reader);

    String logOrEmailAlreadyInUse = "login or email already in use";
    String logAndEmailUnique = "login and email are unique";

    PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    AjaxLoginAndEmail responseAjax = new AjaxLoginAndEmail();
    if (!isUniqueReader) {
      responseAjax.setLogin(logOrEmailAlreadyInUse);
      out.print(gson.toJson(responseAjax));
      out.flush();
      return "json";
    } else {
      responseAjax.setLogin(logAndEmailUnique);
      out.print(gson.toJson(responseAjax));
      out.flush();
      return "json";
    }
  }
}
