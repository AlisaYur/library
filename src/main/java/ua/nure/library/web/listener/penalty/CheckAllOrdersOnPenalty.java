package ua.nure.library.web.listener.penalty;

import java.util.List;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.order.dao.OrderRepository;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.reader.penalty.CheckOrder;

/**
 * @author Artem Kudria
 */
@Log4j
public class CheckAllOrdersOnPenalty implements Runnable {

  @Override
  public void run() {
    OrderRepository orderRepository = RepositoryFactoryProducer.repositoryFactory()
        .getOrderRepository();
    try {
      List<Order> orderList = orderRepository.getAll();
      CheckOrder.checkListOrdersOnPenalty(orderList);
    } catch (DaoException e) {
      log.error("Error get orders");
    }
  }
}
