package ua.nure.library.web.controller.command;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.email.ValidateFields;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.AjaxConstants;
import ua.nure.library.web.controller.ajax.reader.AjaxChangeReader;

/**
 * @author Artem Kudria
 */
@Log4j
public class ChangeEmailReaderCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();

    Gson gson = new Gson();
    AjaxChangeReader changeReader;

    AjaxChangeReader responseAjax = new AjaxChangeReader();

    String ajaxFromRequest = request.getParameter("requestData");
    changeReader = gson.fromJson(ajaxFromRequest, AjaxChangeReader.class);

    PrintWriter out;
    String successMsq = "Success!";
    String errorMsq = "Error input!";
    try {
      out = response.getWriter();
      response.setContentType("application/json");
      response.setCharacterEncoding(ConsEncoding.UTF_8);

      boolean checkEmail = ValidateFields.validateEmail(changeReader.getEmail());
      if (!checkEmail) {
        responseAjax.setMsq(AjaxConstants.ERROR_MSQ);
        sendResponse(out, responseAjax);
        return "json";
      }

      if (null != request.getSession(false) && null != request.getSession(false)
          .getAttribute("user")) {
        Reader reader = (Reader) request.getSession(false).getAttribute("user");
        reader.setEmail(changeReader.getEmail());

        try {
          if (readerRepository.checkUniqueEmailReader(reader)) {
            readerRepository.update(reader.getId(), reader);
            responseAjax.setMsq(successMsq);
            sendResponse(out, responseAjax);
          } else {
            responseAjax.setMsq(errorMsq);
            sendResponse(out, responseAjax);
          }
        } catch (DaoException e) {
          responseAjax.setMsq(errorMsq);
          sendResponse(out, responseAjax);
        }
      }
    } catch (IOException e) {
      log.error(Messages.ERROR_GET_WRITER);
    }
    return "json";
  }

  private void sendResponse(PrintWriter out, AjaxChangeReader responseAjax) {
    Gson gson = new Gson();
    out.print(gson.toJson(responseAjax));
    out.flush();
  }
}
