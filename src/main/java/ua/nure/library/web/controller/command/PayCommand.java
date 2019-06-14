package ua.nure.library.web.controller.command;

import static ua.nure.library.web.controller.ajax.reader.penalty.LiqPayConstants.PRIVATE_KEY;
import static ua.nure.library.web.controller.ajax.reader.penalty.LiqPayConstants.PUBLIC_KEY;

import com.google.gson.Gson;
import com.liqpay.LiqPay;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.order.dao.OrderRepository;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.AjaxConstants;
import ua.nure.library.web.controller.ajax.reader.penalty.AjaxPayment;

/**
 * @author Artem Kudria
 */
@Log4j
public class PayCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    OrderRepository orderRepository = RepositoryFactoryProducer.repositoryFactory()
        .getOrderRepository();
    try {

      Gson gson = new Gson();
      String ajaxFromRequest = request.getParameter(AjaxConstants.REQUEST_DATA);
      AjaxPayment ajaxPayment = gson.fromJson(ajaxFromRequest, AjaxPayment.class);

      PrintWriter out;
      out = response.getWriter();
      response.setContentType("application/json");
      response.setCharacterEncoding(ConsEncoding.UTF_8);

      Order findOrder = orderRepository.getById(Long.parseLong(ajaxPayment.getOrderId()));

      Map params = new HashMap();
      params.put("amount", findOrder.getPenalty());
      params.put("currency", "USD");
      params.put("description", "Pay of Fine for " + findOrder.getBookId().getTitle());
      params.put("sandbox", "1");
      params.put("server_url", Path.READER_CABINET);
      params.put("order_id", findOrder.getPayId());
      params.put("email", findOrder.getUserLogin().getEmail());
      params.put("result_url", "localhost:8080/checkPayPenalty"); //Redirect after payment
      LiqPay liqpay = new LiqPay(PUBLIC_KEY, PRIVATE_KEY);
      String html = liqpay.cnb_form(params);
      response.setContentType("text/html");
      AjaxPayment payment = new AjaxPayment();
      payment.setMsq("Success!");
      payment.setBtn(html);
      sendResponse(out, payment);
    } catch (DaoException e) {
      log.error(Messages.ERROR_GET_PAGE_FINE, e);
    } catch (IOException e) {
      log.error(Messages.ERROR_GET_WRITER, e);
    }
    return "json";
  }

  private void sendResponse(PrintWriter out, AjaxPayment payBtn) {
    Gson gson = new Gson();
    out.print(gson.toJson(payBtn));
    out.flush();
  }
}
