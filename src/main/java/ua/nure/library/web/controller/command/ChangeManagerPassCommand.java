package ua.nure.library.web.controller.command;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.user.UserRepository;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.passwordUtil.PasswordEncoder;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.AjaxConstants;
import ua.nure.library.web.controller.ajax.manager.AjaxChangeManager;

/**
 * @author Artem Kudria
 */
@Log4j
public class ChangeManagerPassCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    UserRepository userRepository = RepositoryFactoryProducer.repositoryFactory()
        .getUserRepository();

    Gson gson = new Gson();
    AjaxChangeManager updateLibrarian;

    AjaxChangeManager responseAjax = new AjaxChangeManager();

    String ajaxParam = "requestData";
    String ajaxFromRequest = request.getParameter(ajaxParam);
    updateLibrarian = gson.fromJson(ajaxFromRequest, AjaxChangeManager.class);

    PrintWriter out;
    try {
      out = response.getWriter();
      response.setContentType("application/json");
      response.setCharacterEncoding(ConsEncoding.UTF_8);

      if (updateLibrarian.getPassword().equalsIgnoreCase("") ||
          !PasswordEncoder.checkOldPassword(request, updateLibrarian.getOldPassword())) {
        responseAjax.setMsq(AjaxConstants.ERROR_MSQ);
        sendResponse(out, responseAjax);
        return "json";
      }

      if (null != request.getSession(false) && null != request.getSession(false)
          .getAttribute("user")) {
        User user = (User) request.getSession(false).getAttribute("user");

        String hashPass = PasswordEncoder.hash(updateLibrarian.getPassword());

        user.setPassword(hashPass);
        userRepository.update(user.getId(), user);
        responseAjax.setMsq(AjaxConstants.SUCCESS_MSQ);
        sendResponse(out, responseAjax);
      } else {
        responseAjax.setMsq(AjaxConstants.ERROR_MSQ);
        sendResponse(out, responseAjax);
      }
    } catch (IOException e) {
      log.error(Messages.ERROR_GET_WRITER);
    } catch (DaoException | NoSuchAlgorithmException e) {
      log.error(Messages.ERROR_HASHING_USER);
    }
    return "json";
  }

  private void sendResponse(PrintWriter out, AjaxChangeManager responseAjax) {
    Gson gson = new Gson();
    out.print(gson.toJson(responseAjax));
    out.flush();
  }
}
