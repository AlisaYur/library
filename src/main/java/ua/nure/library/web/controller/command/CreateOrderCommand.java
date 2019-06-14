package ua.nure.library.web.controller.command;

import java.io.IOException;
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
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.order.OrderValidation;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class CreateOrderCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    OrderRepository orderRepository = RepositoryFactoryProducer.repositoryFactory()
        .getOrderRepository();
    BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
        .getBookRepository();

    String returnPage = Path.MAIN_MENU;

    String bookNameParam = "book_name";
    String bookName = request.getParameter(bookNameParam);

    try {
      request.setCharacterEncoding(ConsEncoding.UTF_8);
      Reader userFromSession = (Reader) request.getSession(false).getAttribute("user");
      OrderValidation.checkReaderPenalty(userFromSession);

      Book book = bookRepository.getByTitle(bookName);
      OrderValidation.checkIfReaderHaveBookAlreadyOrdered(book, userFromSession);

      String typeIssue = request.getParameter("select_type_issue");
      String datePattern = "yyyy-MM-dd";
      String startDatePattern = "MM/dd/yyyy";
      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(datePattern);
      DateTimeFormatter startDateFormat = DateTimeFormatter.ofPattern(startDatePattern);
      String startDate = request.getParameter("currentDate");
      LocalDate sqlStartDate = LocalDate.parse(startDate, startDateFormat);

      String dateOfDelivery = request.getParameter("dateOfDelivery");

      if (OrderValidation.isReturnDateAnEmptyString(dateOfDelivery)) {
        log.error(Messages.ERROR_ENTER_DATE);
        return ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_ENTER_DATE;
      }
      LocalDate sqlDateOfDelivery = LocalDate.parse(dateOfDelivery, dateFormat);
      Long defaultPenalty = 0L;

      if (OrderValidation.isBookCountIsOutOfStock(book)) {
        return ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_BOOKS_COUNT_IS_ZERO;
      }

      Order order = Order.builder()
          .bookId(book)
          .userLogin(userFromSession)
          .typeIssue(TypeIssue.valueOf(typeIssue))
          .startDate(sqlStartDate)
          .dateOfDelivery(sqlDateOfDelivery)
          .penalty(defaultPenalty)
          .status(OrderStatus.NEW)
          .build();
      orderRepository.create(order);
      book.setCountInStock(book.getCountInStock() - 1);
      bookRepository.update(book.getId(), book);
    } catch (DaoException | IOException e) {
      log.error(Messages.ERROR_CREATE_ORDER);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_CREATE_ORDER;
    }
    return returnPage;
  }
}
