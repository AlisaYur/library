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
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.AjaxConstants;
import ua.nure.library.web.controller.ajax.reader.ajax.AjaxCountOrderBody;

/**
 * @author Artem Kudria
 */
@Log4j
public class GetCountAllOrdersCommand implements Command {


  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();

    Gson gson = new Gson();
    String ajaxFromRequest = request.getParameter(AjaxConstants.REQUEST_DATA);
    AjaxCountOrderBody ajaxCountOrderBody = gson
        .fromJson(ajaxFromRequest, AjaxCountOrderBody.class);
    try {
      PrintWriter out;
      out = response.getWriter();
      response.setContentType("application/json");
      response.setCharacterEncoding(ConsEncoding.UTF_8);

      Reader reader = readerRepository.getByLogin(ajaxCountOrderBody.getLogin());
      if (null != reader && null != reader.getLogin()) {
        ajaxCountOrderBody.setActiveCount(readerRepository.getActiveCountReaderOrders(reader));
        ajaxCountOrderBody.setAllCount(readerRepository.getAllCountReaderOrders(reader));
        ajaxCountOrderBody.setPenaltyCount(readerRepository.getPenaltyCountReaderOrders(reader));
        sendResponse(out, ajaxCountOrderBody);
      }
    } catch (IOException | DaoException e) {
      log.error(Messages.ERROR_GET_COUNT);
    }
    return "json";
  }

  private void sendResponse(PrintWriter out, AjaxCountOrderBody responseAjax) {
    Gson gson = new Gson();
    out.print(gson.toJson(responseAjax));
    out.flush();
  }
}
