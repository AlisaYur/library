package ua.nure.library.web.controller.command;

import static ua.nure.library.web.controller.ajax.reader.penalty.LiqPayConstants.PRIVATE_KEY;
import static ua.nure.library.web.controller.ajax.reader.penalty.LiqPayConstants.PUBLIC_KEY;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.log4j.Log4j;
import ua.nure.library.model.order.dao.OrderRepository;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.order.entity.OrderStatus;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.reader.penalty.LiqPayWrapper;
import ua.nure.library.web.controller.ajax.reader.penalty.ResponseLiqPay;

/**
 * @author Artem Kudria
 */
@Log4j
@WebServlet(urlPatterns = "/checkPayPenalty", name = "PayFineReaderServlet")
public class PayFineReaderServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    try {
      execute(request, response);
    } catch (IOException e) {
      log.error("Error check liqPay payment!");
    }
  }

  public void execute(final HttpServletRequest request, final HttpServletResponse response)
      throws IOException {
    OrderRepository orderRepository = RepositoryFactoryProducer.repositoryFactory()
        .getOrderRepository();

    String returnPage;
    try {
      String data = request.getParameter("data");
      String signature = request.getParameter("signature");

      LiqPayWrapper checkCallBack = new LiqPayWrapper(PUBLIC_KEY, PRIVATE_KEY);
      String sign = checkCallBack.str_to_sign(
          PRIVATE_KEY + data + PRIVATE_KEY
      );

      if (!sign.equals(signature)) {
        log.error(Messages.ERROR_PAY);
        response.sendRedirect(Path.READER_CABINET + "&error=" + Messages.ERROR_PAY);
      }

      Gson gson = new Gson();
      String decoded = new String(DatatypeConverter.parseBase64Binary(data));
      ResponseLiqPay responseLiqPay = gson.fromJson(decoded, ResponseLiqPay.class);

      if (responseLiqPay.getStatus().equalsIgnoreCase("sandbox")
          || responseLiqPay.getStatus().equalsIgnoreCase("success")) {

        Order order = orderRepository.getOrderByPayId(responseLiqPay.getOrder_id());
        order.setStatus(OrderStatus.RETURNS);
        order.setPenalty(0L);
        orderRepository.update(order.getId(), order);

        if (request.getSession(false) != null) {
          request.getSession(false).removeAttribute("readerIsPenalty");
        }
      }
      returnPage = Path.READER_CABINET;
    } catch (Exception e) {
      log.error(Messages.ERROR_AFTER_PAY);
      returnPage = Path.READER_CABINET;
    }
    response.sendRedirect(returnPage);
  }
}
