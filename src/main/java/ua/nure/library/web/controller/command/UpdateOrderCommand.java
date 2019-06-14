package ua.nure.library.web.controller.command;

import static java.lang.Long.valueOf;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.model.order.dao.OrderRepository;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.order.entity.OrderStatus;
import ua.nure.library.model.order.entity.TypeIssue;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class UpdateOrderCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    OrderRepository orderRepository = RepositoryFactoryProducer.repositoryFactory()
        .getOrderRepository();
    BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
        .getBookRepository();
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();

    String returnPage = Path.LIST_ORDERS;
    try {
      String bookName = request.getParameter("bookName");
      String userName = request.getParameter("userLogin");
      OrderStatus orderStatus = OrderStatus.valueOf(request.getParameter("orderStatus"));
      TypeIssue typeIssue = TypeIssue.valueOf(request.getParameter("typeIssue"));
      Long penalty = Long.valueOf(request.getParameter("penalty"));
      String startDate = request.getParameter("currentDate");
      String dateOfDelivery = request.getParameter("dateOfDelivery");

      String datePattern = "dd-MM-yyyy";
      String startDatePattern = "dd/MM/yyyy";
      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(datePattern);
      DateTimeFormatter startDateFormat = DateTimeFormatter.ofPattern(startDatePattern);

      Reader reader = readerRepository.getByLogin(userName);
      Book book = bookRepository.getByTitle(bookName);

      Long id = valueOf(request.getParameter("id"));
      Order order = orderRepository.getById(id);
      order.setBookId(book);
      order.setUserLogin(reader);
      order.setStatus(orderStatus);
      order.setTypeIssue(typeIssue);
      order.setPenalty(penalty);

      LocalDate sqlStartDate = LocalDate.parse(startDate, startDateFormat);
      order.setStartDate(sqlStartDate);
      LocalDate sqlDateOfDelivery = LocalDate.parse(dateOfDelivery, dateFormat);
      order.setDateOfDelivery(sqlDateOfDelivery);
      orderRepository.update(id, order);
    } catch (DaoException e) {

      log.error(Messages.ERROR_UPDATE_ORDER, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_UPDATE_ORDER;
    } catch (NumberFormatException e) {
      log.error(Messages.ERROR_PARSE_LONG, e);
    }
    return returnPage;
  }
}
