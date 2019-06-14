package ua.nure.library.web.controller.command;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.order.dao.OrderRepository;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.util.PagesPath;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

@Log4j
public class HistoryManagerOrdersCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    try {
      OrderRepository orderRepository = RepositoryFactoryProducer.repositoryFactory()
          .getOrderRepository();
      List<Order> orders = orderRepository.getAll();
      request.setAttribute("orders", orders);
    } catch (DaoException e) {
      String errorMsq = "Error get history orders in cabinet!";
      log.error("Error get history orders cabinet!");
      return ErrorRedirect.ERROR_MAIN_MENU_URL + errorMsq;
    }
    return PagesPath.HISTORY_ORDERS_MANAGER_CAB;
  }
}
