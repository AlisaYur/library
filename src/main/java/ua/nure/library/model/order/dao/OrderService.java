package ua.nure.library.model.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.book.BookQueries;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.dao.book.BookService;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.order.entity.OrderStatus;
import ua.nure.library.model.order.entity.TypeIssue;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.dao.reader.ReaderService;
import ua.nure.library.util.connection.DBWorker;
import ua.nure.library.util.transaction.Transactional;

/**
 * @author Artem Kudria
 */
@Log4j
public class OrderService implements OrderRepository {

  private Connection connection;

  public OrderService(Connection connection) {
    this.connection = connection;
  }

  /**
   * @see OrderRepository#create(Order)
   */
  @Override
  public void create(Order object) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(OrderQueries.INSERT_ORDER)) {
      executeCreateOrUpdate(preparedStatement, object, null);
    } catch (SQLException e) {
      String errorMessage = "Error insert Order " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see OrderRepository#create(Order)
   */
  @Override
  public void update(Long id, Order object) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(OrderQueries.UPDATE_ORDER)) {
      executeCreateOrUpdate(preparedStatement, object, id);
    } catch (SQLException e) {
      String errorMessage = "Error update Orders " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see OrderRepository#create(Order)
   */
  @Override
  public void delete(Order object) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(OrderQueries.DELETE_ORDER)) {
      preparedStatement.setLong(1, object.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      String errorMessage = "Error delete Orders " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see OrderRepository#create(Order)
   */
  @Override
  public Order getById(Long id) throws DaoException {
    Order order = new Order();
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(OrderQueries.SELECT_BY_ID)) {
      preparedStatement.setLong(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          order = configureOrderFromResultSet(resultSet);
        }
      }
      return order;
    } catch (SQLException e) {
      String errorMessage = "Error select order by id, db return error " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see OrderRepository#getAll()
   */
  @Transactional
  @Override
  public List<Order> getAll() throws DaoException {
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(OrderQueries.GET_ALL_ORDERS)) {
      return getOrdersFromResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error select all orders, db return error " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  @Override
  public List<Order> getOrdersFromResultSet(PreparedStatement preparedStatement)
      throws DaoException {
    Book book;
    BookRepository bookRepository = new BookService(connection);
    ReaderRepository readerRepository = new ReaderService(connection);
    List<Order> orderList = new ArrayList<>();
    try (ResultSet set = preparedStatement.executeQuery()) {
      while (set.next()) {
        Order order = new Order();
        order.setId(set.getLong(OrderQueries.ID));
        book = bookRepository.getByTitle(set.getString(BookQueries.NAME));
        order.setBookId(book);
        order.setUserLogin(readerRepository.configureReaderWithoutOrders(set));
        order.setStartDate(set.getObject(OrderQueries.START_DATE, LocalDate.class));
        order.setDateOfDelivery(set.getObject(OrderQueries.DATE_OF_DELIVERY, LocalDate.class));
        order.setPenalty(set.getLong(OrderQueries.PENALTY));
        order.setTypeIssue(TypeIssue.valueOf(set.getString(OrderQueries.TYPE_ISSUE)));
        order.setStatus(OrderStatus.valueOf(set.getString(OrderQueries.STATUS)));
        order.setPayId(set.getString(OrderQueries.PAY_ID));
        orderList.add(order);
      }
      return orderList;
    } catch (SQLException e) {
      String errorMessage = "Error set user or book into orders " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  private void executeCreateOrUpdate(final PreparedStatement preparedStatement,
      final Order object, final Long id) throws SQLException, DaoException {
    preparedStatement.setLong(1, object.getBookId().getId());
    preparedStatement.setString(2, object.getUserLogin().getLogin());
    preparedStatement.setString(3, object.getTypeIssue().name());
    preparedStatement.setObject(4, object.getStartDate());
    preparedStatement.setObject(5, object.getDateOfDelivery());
    preparedStatement.setDouble(6, object.getPenalty());
    preparedStatement.setString(7, object.getStatus().name());
    Optional<Long> optionalId = Optional.ofNullable(id);
    if (optionalId.isPresent()) {
      preparedStatement.setString(8, object.getPayId());
      preparedStatement.setLong(9, optionalId.get());

    } else {
      preparedStatement.setString(8, generateUniquePayId(object.getId()));
    }
    preparedStatement.executeUpdate();
  }

  /**
   * @see OrderRepository#changeStatus(OrderStatus, Order)
   */
  @Transactional
  @Override
  public void changeStatus(OrderStatus newStatus, Order order) throws DaoException {
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(OrderQueries.CHANGE_STATUS)) {
      preparedStatement.setString(1, newStatus.name());
      preparedStatement.setLong(2, order.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      String errorMessage = "Error set new status for order " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * Configure order from result set
   *
   * @param resultSet ResultSet
   * @return Order
   * @throws SQLException DataBase exception
   * @throws DaoException Throw user exception if error get order from db
   */
  private Order configureOrderFromResultSet(ResultSet resultSet) throws SQLException, DaoException {
    Order order = new Order();
    order.setId(resultSet.getLong(OrderQueries.ID));
    order.setDateOfDelivery(resultSet.getDate(OrderQueries.DATE_OF_DELIVERY).toLocalDate());
    order.setPenalty(resultSet.getLong(OrderQueries.PENALTY));
    order.setStartDate(resultSet.getDate(OrderQueries.START_DATE).toLocalDate());
    order.setStatus(OrderStatus.valueOf(resultSet.getString(OrderQueries.STATUS)));
    order.setPayId(resultSet.getString(OrderQueries.PAY_ID));
    order.setTypeIssue(TypeIssue.valueOf(resultSet.getString(OrderQueries.TYPE_ISSUE)));

    BookRepository bookRepository = new BookService(connection);
    order.setBookId(bookRepository.getById(resultSet.getLong(OrderQueries.BOOK_ID)));

    ReaderRepository readerRepository = new ReaderService(connection);
    order.setUserLogin(readerRepository.getByLogin(resultSet.getString(OrderQueries.USER_LOGIN)));
    return order;
  }

  @Transactional
  @Override
  public List<Order> searchOrder(String searchKey) throws DaoException {
    List<Order> orders = new ArrayList<>();
    try (PreparedStatement preparedStatement =
        DBWorker.getDbWorker().getConnection()
            .prepareStatement(OrderQueries.GET_ALL_ORDERS_BY_TITLEBOOK_OR_READER_NAME)) {
      String searchPatternKey = "%" + searchKey + "%";
      preparedStatement.setString(1, searchPatternKey);
      preparedStatement.setString(2, searchPatternKey);
      try (ResultSet set = preparedStatement.executeQuery()) {
        while (set.next()) {
          Order order = configureOrderFromResultSet(set);
          orders.add(order);
        }
      }
      return orders.stream()
          .sorted(Comparator.comparing(x -> x.getStatus().name()))
          .collect(Collectors.toList());
    } catch (SQLException e) {
      String errorMessage = "Error, get order!";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
  }

  public String generateUniquePayId(Long id) throws DaoException {
    String data;
    do {
      data = "unique_pay_id_" + UUID.randomUUID().toString();
    } while (getOrderByPayId(data) == null);
    return DatatypeConverter.printBase64Binary(data.getBytes());
  }

  @Override
  public Order getOrderByPayId(String payId) throws DaoException {
    Order order = new Order();
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(OrderQueries.GET_ORDER_BY_PAY_ID)) {
      preparedStatement.setString(1, payId);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          order = configureOrderFromResultSet(resultSet);
        }
      }
      return order;
    } catch (SQLException e) {
      String errorMessage = "Error select order by pay_id, db return error " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }
}
