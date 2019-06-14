package ua.nure.library.web.controller.command;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.passwordUtil.PasswordEncoder;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.reader.AjaxChangeReader;

/**
 * @author Artem Kudria
 */
@Log4j
public class ChangePassReaderCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();

    Gson gson = new Gson();
    AjaxChangeReader changeReader;
    String successMsq = "Success!";
    String errorMsq = "Error input!";
    AjaxChangeReader responseAjax = new AjaxChangeReader();

    String ajaxFromRequest = request.getParameter("requestData");
    changeReader = gson.fromJson(ajaxFromRequest, AjaxChangeReader.class);

    PrintWriter out;
    try {
      out = response.getWriter();
      response.setContentType("application/json");
      response.setCharacterEncoding(ConsEncoding.UTF_8);

      if (changeReader.getPassword().equalsIgnoreCase("") ||
          !PasswordEncoder.checkOldPassword(request, changeReader.getOldPassword())) {
        responseAjax.setMsq(errorMsq);
        sendResponse(out, responseAjax);
        return "json";
      }

      if (null != request.getSession(false) && null != request.getSession(false)
          .getAttribute("user")) {
        Reader reader = (Reader) request.getSession(false).getAttribute("user");

        String hashPass = PasswordEncoder.hash(changeReader.getPassword());

        reader.setPassword(hashPass);
        readerRepository.update(reader.getId(), reader);
        responseAjax.setMsq(successMsq);
        sendResponse(out, responseAjax);
      } else {
        responseAjax.setMsq(errorMsq);
        sendResponse(out, responseAjax);
      }
    } catch (IOException e) {
      log.error(Messages.ERROR_GET_WRITER);
    } catch (DaoException | NoSuchAlgorithmException e) {
      log.error(Messages.ERROR_HASHING_USER);
    }
    return "json";
  }

  private void sendResponse(PrintWriter out, AjaxChangeReader responseAjax) {
    Gson gson = new Gson();
    out.print(gson.toJson(responseAjax));
    out.flush();
  }
}
