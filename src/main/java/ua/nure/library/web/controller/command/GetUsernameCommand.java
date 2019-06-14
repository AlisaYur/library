package ua.nure.library.web.controller.command;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.web.controller.ajax.auth.validation.AjaxLoginAndEmail;

/**
 * @author Artem Kudria
 */
@Log4j
public class GetUsernameCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    try {
      Gson gson = new Gson();

      PrintWriter out = response.getWriter();
      AjaxLoginAndEmail ajaxLoginAndEmail = new AjaxLoginAndEmail();

      String userAttributeFromSession = "user";
      User user = (User) request.getSession(false).getAttribute(userAttributeFromSession);

      if (user != null) {
        ajaxLoginAndEmail.setLogin(user.getLogin());
        ajaxLoginAndEmail.setEmail(user.getEmail());
        out.print(gson.toJson(ajaxLoginAndEmail));
        out.flush();
      }
    } catch (IOException e) {
      String errorMessage = "Error get username and login ";
      log.error(errorMessage);
    }
    return "json";
  }
}
