package ua.nure.library.web.controller.command;

import static ua.nure.library.util.uservalidation.UserValidation.getUserOnValidationByLogin;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.passwordUtil.PasswordEncoder;
import ua.nure.library.web.controller.ajax.auth.validation.AjaxUserSignInData;
import ua.nure.library.web.controller.ajax.auth.validation.AuthStatus;

/**
 * @author Artem Kudria
 */
@Log4j
public class CheckUserLoginCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    try {
      Gson gson = new Gson();
      String requestParamFromAjax = "requestData";
      String ajaxFromRequest = request.getParameter(requestParamFromAjax);
      AjaxUserSignInData ajaxUserSignInData = gson
          .fromJson(ajaxFromRequest, AjaxUserSignInData.class);

      AjaxUserSignInData responseAjax = new AjaxUserSignInData();

      PrintWriter out = response.getWriter();
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");

      if (checkUserInput(ajaxUserSignInData, responseAjax, out)) {
        return "json";
      }

      User user = getUserOnValidationByLogin(ajaxUserSignInData.getLogin());
      checkUserData(ajaxUserSignInData, responseAjax, user);

      out.print(gson.toJson(responseAjax));
      out.flush();
    } catch (NoSuchAlgorithmException | JsonSyntaxException | DaoException e) {
      String errorMessage = "Error matches password " + e;
      log.error(errorMessage);
    } catch (IOException e) {
      String errorMessage = "Error write or read user from db " + e;
      log.error(errorMessage);
    }
    return "json";
  }

  private boolean checkUserInput(AjaxUserSignInData ajaxUserSignInData,
      AjaxUserSignInData responseAjax, PrintWriter out) {
    final String inputInvalid = "invalid input!";
    if (ajaxUserSignInData.getLogin() == null || ajaxUserSignInData.getLogin()
        .equalsIgnoreCase("")) {
      responseAjax.setMessage(inputInvalid);
      responseAjax.setStatus(AuthStatus.ERROR);
      out.print(responseAjax);
      out.flush();
      return true;
    }
    return false;
  }

  private void checkUserData(AjaxUserSignInData ajaxUserSignInData,
      AjaxUserSignInData responseAjax, User user)
      throws UnsupportedEncodingException, NoSuchAlgorithmException {
    if (!checkUserOnNull(responseAjax, user) && checkUserActive(responseAjax, user)) {
      checkUserPass(ajaxUserSignInData, responseAjax, user);
    }
  }

  private boolean checkUserOnNull(AjaxUserSignInData responseAjax, User user) {
    final String noUserFoundMessage = "no user found!";
    if (null == user) {
      responseAjax.setStatus(AuthStatus.ERROR);
      responseAjax.setMessage(noUserFoundMessage);
      return true;
    }
    return false;
  }

  private boolean checkUserActive(AjaxUserSignInData responseAjax, User user) {
    final String userNotActive = "User not active!";
    if (!user.isActive()) {
      responseAjax.setStatus(AuthStatus.ERROR);
      responseAjax.setMessage(userNotActive);
      return false;
    }
    return true;
  }

  private void checkUserPass(AjaxUserSignInData ajaxUserSignInData,
      AjaxUserSignInData responseAjax, User user)
      throws UnsupportedEncodingException, NoSuchAlgorithmException {
    final String passValid = "password valid!";
    final String inputInvalid = "invalid input!";
    Optional<String> userPassword = Optional.ofNullable(Objects.requireNonNull(user).getPassword());
    if (userPassword.isPresent()) {
      if (PasswordEncoder
          .matches(ajaxUserSignInData.getPassword(), userPassword.get())) {
        responseAjax.setStatus(AuthStatus.SUCCESS);
        responseAjax.setMessage(passValid);
      } else {
        responseAjax.setStatus(AuthStatus.ERROR);
        responseAjax.setMessage(inputInvalid);
      }
    } else {
      responseAjax.setMessage(inputInvalid);
      responseAjax.setStatus(AuthStatus.ERROR);
    }
  }
}
