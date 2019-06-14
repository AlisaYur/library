package ua.nure.library.web.controller.ajax.reader.penalty;

import static java.time.temporal.ChronoUnit.DAYS;
import static ua.nure.library.web.listener.penalty.PenaltyConst.PENALTY_SIZE;

import java.time.LocalDate;
import java.util.List;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.order.dao.OrderRepository;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.order.entity.OrderStatus;
import ua.nure.library.util.repository.RepositoryFactoryProducer;

/**
 * @author Artem Kudria
 */
@Log4j
public final class CheckOrder {

  private CheckOrder() {
  }

  public static boolean checkListOrdersOnPenalty(List<Order> orderList) {
    boolean isPenalty = false;

    OrderRepository orderRepository = RepositoryFactoryProducer.repositoryFactory()
        .getOrderRepository();
    LocalDate currentDate = LocalDate.now();
    for (Order order : orderList) {
      if (currentDate.isAfter(order.getDateOfDelivery())
          && (order.getStatus() == OrderStatus.APPROVED
          || order.getStatus() == OrderStatus.PENALTY)) {

        long daysBetween = Math.abs(DAYS.between(currentDate, order.getDateOfDelivery()));
        order.setPenalty(daysBetween * PENALTY_SIZE);
        if (order.getStatus() == OrderStatus.APPROVED) {
          order.setStatus(OrderStatus.PENALTY);
        }
        try {
          orderRepository.update(order.getId(), order);
          isPenalty = true;
        } catch (DaoException e) {
          String errorMsq = "Error update status on penalty!";
          log.error(errorMsq + e.getMessage());
        }
      } else if (order.getStatus() == OrderStatus.PENALTY) {
        isPenalty = true;
      }
    }
    return isPenalty;
  }
}
