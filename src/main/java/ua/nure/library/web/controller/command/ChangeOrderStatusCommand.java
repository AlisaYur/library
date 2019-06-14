package ua.nure.library.web.controller.command;

import static java.time.temporal.ChronoUnit.DAYS;
import static ua.nure.library.model.order.entity.OrderStatus.PENALTY;
import static ua.nure.library.web.listener.penalty.PenaltyConst.PENALTY_SIZE;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.model.order.dao.OrderRepository;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.order.entity.OrderStatus;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.order.AjaxOrderStatus;

/**
 * @author Artem Kudria
 */
@Log4j
public class ChangeOrderStatusCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    OrderRepository orderRepository = RepositoryFactoryProducer.repositoryFactory()
        .getOrderRepository();

    Gson gson = new Gson();
    AjaxOrderStatus ajaxOrderStatus;
    String successMsq = "Status Changed!";
    String errorMsq = "Status not changed!";

    String ajaxParamName = "requestData";
    String orderId = request.getParameter(ajaxParamName);
    ajaxOrderStatus = gson.fromJson(orderId, AjaxOrderStatus.class);

    AjaxOrderStatus responseAjax = new AjaxOrderStatus();
    PrintWriter out = null;
    try {
      out = response.getWriter();
      response.setContentType("application/json");
      response.setCharacterEncoding(ConsEncoding.UTF_8);

      Order findOrder = orderRepository.getById(ajaxOrderStatus.getOrderId());
      findOrder.setStatus(
          changedStatus(findOrder, OrderStatus.valueOf(ajaxOrderStatus.getNewStatus()),
              orderRepository));
      orderRepository.update(findOrder.getId(), findOrder);

      responseAjax.setNewStatus(findOrder.getStatus().name());
      responseAjax.setMsq(successMsq);
      responseAjax.setOrderId(findOrder.getPenalty());
      sendResponse(out, responseAjax);
    } catch (DaoException | IOException e) {
      responseAjax.setMsq(errorMsq);
      log.info(Messages.ERROR_GET_ORDERS);
      Optional<PrintWriter> printWriterOut = Optional.ofNullable(out);
      if (printWriterOut.isPresent()) {
        sendResponse(out, responseAjax);
      }
    }
    return "json";
  }

  private void sendResponse(PrintWriter out, AjaxOrderStatus responseAjax) {
    Gson gson = new Gson();
    out.print(gson.toJson(responseAjax));
    out.flush();
  }

  private OrderStatus changedStatus(Order order, OrderStatus newOrderStatus,
      OrderRepository orderRepository) throws DaoException {
    BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
        .getBookRepository();
    OrderStatus currentStatus = order.getStatus();
    switch (newOrderStatus) {
      case CLOSE:
        if (currentStatus == OrderStatus.NEW || currentStatus == OrderStatus.RETURNS ||
            currentStatus == PENALTY) {
          orderRepository.changeStatus(newOrderStatus, order);
          Book book = bookRepository.getById(order.getBookId().getId());
          book.setCountInStock(book.getCountInStock() + 1);
          bookRepository.update(book.getId(), book);
          return OrderStatus.CLOSE;
        }
        break;
      case APPROVED:
        if (currentStatus == OrderStatus.NEW) {
          orderRepository.changeStatus(newOrderStatus, order);
          return OrderStatus.APPROVED;
        }
        break;
      case RETURNS:
        if (currentStatus == OrderStatus.APPROVED) {
          try {
            LocalDate currentDate = LocalDate.now();
            if (currentDate.isAfter(order.getDateOfDelivery())
                && (order.getStatus() == OrderStatus.APPROVED || order.getStatus() == PENALTY)) {

              long daysBetween = Math.abs(DAYS.between(currentDate, order.getDateOfDelivery()));
              order.setPenalty(daysBetween * PENALTY_SIZE);
              if (order.getStatus() == OrderStatus.APPROVED) {
                order.setStatus(PENALTY);
              }
              orderRepository.update(order.getId(), order);
              orderRepository.changeStatus(PENALTY, order);
              return PENALTY;
            }
          } catch (DaoException e) {
            log.error("Error get return order!");
          }

          orderRepository.changeStatus(newOrderStatus, order);
          return OrderStatus.RETURNS;
        }
        break;
      case PENALTY:
        if (currentStatus == OrderStatus.RETURNS) {
          orderRepository.changeStatus(newOrderStatus, order);
          order.setDateOfDelivery(LocalDate.now());
          order.setPenalty(PENALTY_SIZE);
          orderRepository.update(order.getId(), order);
          return PENALTY;
        }
        break;
      default:
        throw new DaoException("Change status error!");
    }
    throw new DaoException("Change status error!");
  }
}
