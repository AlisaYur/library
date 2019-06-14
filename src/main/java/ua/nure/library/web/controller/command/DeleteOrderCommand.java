package ua.nure.library.web.controller.command;

import static java.lang.Long.valueOf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.model.order.dao.OrderRepository;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class DeleteOrderCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    OrderRepository orderRepository = RepositoryFactoryProducer.repositoryFactory()
        .getOrderRepository();

    String returnPage = Path.MANAGER_CABINET;
    try {
      Long idDeleteOrder = valueOf(request.getParameter("id"));
      Order order = orderRepository.getById(idDeleteOrder);
      orderRepository.delete(order);

      BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
          .getBookRepository();
      Book book = bookRepository.getByTitle(order.getBookId().getTitle());
      book.setCountInStock(book.getCountInStock() + 1);
      bookRepository.update(book.getId(), book);
    } catch (DaoException e) {
      log.error(Messages.ERROR_DELETE_ORDER, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_DELETE_ORDER;
    }
    return returnPage;
  }
}
