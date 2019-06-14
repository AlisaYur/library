package ua.nure.library.web.controller.ajax.order;

import java.util.Optional;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.order.entity.OrderStatus;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.util.repository.RepositoryFactoryProducer;

/**
 * @author Artem Kudria
 */
@Log4j
public final class OrderValidation {

  private OrderValidation() {
  }

  /**
   * Check if Reader have already ordered this book. Reader can`t have multiply orders with same
   * book.
   *
   * @param book to check
   * @param reader to check
   * @throws DaoException if Reader already have ordered this book
   */
  public static void checkIfReaderHaveBookAlreadyOrdered(Book book, Reader reader)
      throws DaoException {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();
    Order checkOrder = readerRepository.getReaderOrderByBookId(book.getId(), reader);
    Optional<Long> orderId = Optional.ofNullable(checkOrder.getId());
    if (orderId.isPresent() && checkOrder.getStatus() != OrderStatus.CLOSE) {
      String errorMsq = "Reader is have this book in orders!";
      log.info(errorMsq);
      throw new DaoException(errorMsq);
    }
  }

  /**
   * Validate is Book count in stock is more than zero
   *
   * @param book to validate
   * @return true if book count in stock is more than zero or false is not
   */
  public static boolean isBookCountIsOutOfStock(Book book) {
    return book.getCountInStock() == 0 || book.getCountInStock() < 0;
  }

  /**
   * Validate is Return boo date is an empty string.
   *
   * @param date field to validate
   * @return true if input is an empty string or false is not
   */
  public static boolean isReturnDateAnEmptyString(String date) {
    return date.equals(" ") || date.equals("");
  }

  /**
   * Check if Reader have Penalty. If yes, throws DaoExceptionGF
   *
   * @param reader reader to check.
   */
  public static void checkReaderPenalty(Reader reader) throws DaoException {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();
    if (!readerRepository.getAllReaderOrderWithStatusPenalty(reader).isEmpty()) {
      String errorMsq = "Reader have order with penalty!";
      log.debug(errorMsq);
      throw new DaoException(errorMsq);
    }
  }
}
