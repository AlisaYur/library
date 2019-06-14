package ua.nure.library.web.controller.command;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.dao.user.UserRepository;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.email.ValidateFields;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.AjaxConstants;
import ua.nure.library.web.controller.ajax.manager.AjaxChangeManager;
import ua.nure.library.web.controller.ajax.manager.GetLibrarianDataFromJson;

/**
 * @author Artem Kudria
 */
@Log4j
public class ChangeManagerEmailCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    UserRepository userRepository = RepositoryFactoryProducer.repositoryFactory()
        .getUserRepository();
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();

    AjaxChangeManager changeLibrarian = GetLibrarianDataFromJson.getChangeLibrarian(request);
    AjaxChangeManager responseAjax = new AjaxChangeManager();

    try {
      PrintWriter out;
      out = response.getWriter();
      response.setContentType("application/json");
      response.setCharacterEncoding(ConsEncoding.UTF_8);

      boolean checkEmail = ValidateFields.validateEmail(changeLibrarian.getEmail());
      if (!checkEmail) {
        responseAjax.setMsq(AjaxConstants.ERROR_MSQ);
        sendResponse(out, responseAjax);
      }

      if (request.getSession(false) != null
          && request.getSession(false).getAttribute("user") != null) {
        User user = (User) request.getSession(false).getAttribute("user");
        user.setEmail(changeLibrarian.getEmail());
        try {
          if (readerRepository.checkUniqueEmailReader(user)) {
            userRepository.update(user.getId(), user);
            responseAjax.setMsq(AjaxConstants.SUCCESS_MSQ);
            sendResponse(out, responseAjax);
          } else {
            responseAjax.setMsq(AjaxConstants.ERROR_MSQ);
            sendResponse(out, responseAjax);
          }
        } catch (DaoException e) {
          responseAjax.setMsq(AjaxConstants.ERROR_MSQ);
          sendResponse(out, responseAjax);
        }
      }
    } catch (IOException e) {
      log.error(Messages.ERROR_GET_WRITER);
    }
    return "json";
  }

  private void sendResponse(PrintWriter out, AjaxChangeManager responseAjax) {
    Gson gson = new Gson();
    out.print(gson.toJson(responseAjax));
    out.flush();
  }
}
