package ua.nure.library.model.user.dao.reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.dao.book.BookService;
import ua.nure.library.model.order.dao.OrderQueries;
import ua.nure.library.model.order.dao.OrderRepository;
import ua.nure.library.model.order.dao.OrderService;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.order.entity.OrderStatus;
import ua.nure.library.model.order.entity.TypeIssue;
import ua.nure.library.model.role.dao.RoleRepository;
import ua.nure.library.model.role.dao.RoleService;
import ua.nure.library.model.role.entity.Role;
import ua.nure.library.model.user.dao.user.UserRepository;
import ua.nure.library.model.user.dao.user.UserService;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.model.user.exception.UserLoginNotUniqueException;
import ua.nure.library.util.transaction.Transactional;

/**
 * @author Artem Kudria
 */
@Log4j
public class ReaderService implements ReaderRepository {

  private Connection connection;

  public ReaderService(Connection connection) {
    this.connection = connection;
  }

  /**
   * @see ReaderRepository#create(Reader)
   */
  @Override
  public void create(Reader object) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(ReaderQueries.INSERT_READER)) {
      if (isUniqueReaderAndUser(object)) {
        executeCreateOrUpdate(preparedStatement, object, null);
      }
    } catch (SQLException e) {
      String errorMessage = "Error insert Reader";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see ReaderRepository#update(Long, Reader)
   */
  @Override
  public void update(Long id, Reader reader) throws DaoException {
    try (PreparedStatement updateStatement = connection
        .prepareStatement(ReaderQueries.UPDATE_READER)) {
      executeCreateOrUpdate(updateStatement, reader, id);
    } catch (SQLException e) {
      String errorMessage = "Error update Reader";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see ReaderRepository#delete(Reader)
   */
  @Override
  public void delete(Reader object) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(ReaderQueries.DELETE_READER)) {
      preparedStatement.setLong(1, object.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      String errorMessage = "Error delete Reader";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see ReaderRepository#getById(Long)
   */
  @Override
  public Reader getById(Long id) throws DaoException {
    Reader reader;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(ReaderQueries.SELECT_BY_ID)) {
      preparedStatement.setLong(1, id);
      reader = getUserFormResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error select by id Reader, db return error";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
    return reader;
  }

  /**
   * @see ReaderRepository#getByLogin(String)
   */
  @Transactional
  @Override
  public Reader getByLogin(String login) throws DaoException {
    Reader reader;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(ReaderQueries.SELECT_BY_LOGIN)) {
      preparedStatement.setString(1, login);
      reader = getUserFormResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error select by login Reader, db return error";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
    return reader;
  }

  /**
   * @see ReaderRepository#getByName(String)
   */
  @Override
  public Reader getByName(String name) throws DaoException {
    Reader reader;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(ReaderQueries.SELECT_BY_NAME)) {
      preparedStatement.setString(1, name);
      reader = getUserFormResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error select by name Reader, db return error";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
    return reader;
  }

  /**
   * @see ReaderRepository#getAll();
   */
  @Transactional
  @Override
  public List<Reader> getAll() throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(ReaderQueries.SELECT_ALL_READERS)) {
      return getReadersFromResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error, unable to connect to database";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see ReaderRepository#getByEmail(String)
   */
  @Override
  public Reader getByEmail(String name) throws DaoException {
    Reader reader;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(ReaderQueries.SELECT_BY_EMAIL)) {
      preparedStatement.setString(1, name);
      reader = getUserFormResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error select by email Reader, db return error";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
    return reader;
  }

  /**
   * @see ReaderRepository#searchReadersByNameLoginOrEmail(String);
   */
  @Override
  public List<Reader> searchReadersByNameLoginOrEmail(final String criteria) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(ReaderQueries.SEARCH_READERS)) {
      String searchWithAttributes = "%" + criteria + "%";
      preparedStatement.setString(1, searchWithAttributes);
      preparedStatement.setString(2, searchWithAttributes);
      preparedStatement.setString(3, searchWithAttributes);
      return getReadersFromResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error search Readers, db return error";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * Get Reader from result set
   *
   * @param preparedStatement PreparedStatement with queries
   * @return Reader object
   * @throws DaoException If error select Reader, throw exception
   */
  private Reader getUserFormResultSet(PreparedStatement preparedStatement) throws DaoException {
    Reader reader = null;
    try (ResultSet set = preparedStatement.executeQuery()) {
      while (set.next()) {
        reader = configureReaderFromResultSet(set);
      }
      return reader;
    } catch (SQLException e) {
      String errorMessage = "Error search Reader, db return error";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
  }

  private void executeCreateOrUpdate(final PreparedStatement prepStatement,
      final Reader object, final Long id) throws SQLException {
    new UserService(connection).executeCreateOrUpdate(prepStatement, object, id);
  }

  /**
   * @see ReaderRepository#getAllOrdersReader(String)
   */
  @Transactional
  @Override
  public List<Order> getAllOrdersReader(String login) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(ReaderQueries.GET_ALL_ORDERS_BY_USER)) {
      preparedStatement.setString(1, login);

      List<Order> orders = new ArrayList<>();
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          orders.add(configureOrderFromResultSet(resultSet));
        }
      }
      return sortOrderByStatusName(orders);
    } catch (SQLException e) {
      String errorMessage = "Error get book or user from db";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
  }

  private List<Order> sortOrderByStatusName(List<Order> orders) {
    return orders.stream()
        .sorted(Comparator.comparing(x -> x.getStatus().name()))
        .collect(Collectors.toList());
  }

  /**
   * Get list readers from result set
   *
   * @param preparedStatement with query
   * @return List of Readers
   */
  private List<Reader> getReadersFromResultSet(PreparedStatement preparedStatement)
      throws DaoException {
    List<Reader> readers = new ArrayList<>();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        Reader reader = new Reader();
        reader.setId(resultSet.getLong(ReaderQueries.ID));
        reader.setLogin(resultSet.getString(ReaderQueries.LOGIN));
        reader.setEmail(resultSet.getString(ReaderQueries.EMAIL));
        reader.setName(resultSet.getString(ReaderQueries.NAME));
        reader.setPassword(resultSet.getString(ReaderQueries.PASS));
        reader.setActive(resultSet.getBoolean(ReaderQueries.IS_ACTIVE));

        List<Order> readerOrders = getAllOrdersReader(reader.getLogin());
        reader.setOrders(readerOrders);

        readers.add(reader);
      }
    } catch (SQLException e) {
      String errorMessage = "Error, unable to select Readers";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
    return readers;
  }

  private Order configureOrderFromResultSet(ResultSet resultSet)
      throws SQLException, DaoException {
    BookRepository bookRepository = new BookService(connection);

    Order order = new Order();
    order.setId(resultSet.getLong(OrderQueries.ID));
    order.setPayId(resultSet.getString(OrderQueries.PAY_ID));
    order.setDateOfDelivery(resultSet.getDate(OrderQueries.DATE_OF_DELIVERY).toLocalDate());
    order.setStartDate(resultSet.getDate(OrderQueries.START_DATE).toLocalDate());
    order.setPenalty(resultSet.getLong(OrderQueries.PENALTY));
    order.setBookId(bookRepository.getById(resultSet.getLong(OrderQueries.BOOK_ID)));
    order.setTypeIssue(TypeIssue.valueOf(resultSet.getString(OrderQueries.TYPE_ISSUE)));
    order.setStatus(OrderStatus.valueOf(resultSet.getString(OrderQueries.STATUS)));
    order.setUserLogin(configureReaderWithoutOrders(resultSet));
    return order;
  }

  @Override
  public Reader configureReaderWithoutOrders(ResultSet resultSet)
      throws DaoException, SQLException {
    Reader reader = new Reader();
    reader.setId(resultSet.getLong(ReaderQueries.USER_ID));
    reader.setName(resultSet.getString(ReaderQueries.NAME));
    reader.setLogin(resultSet.getString(ReaderQueries.LOGIN));
    reader.setActive(resultSet.getBoolean(ReaderQueries.IS_ACTIVE));
    reader.setEmail(resultSet.getString(ReaderQueries.EMAIL));
    reader.setPassword(resultSet.getString(ReaderQueries.PASS));
    reader.setRole(configureRoleFromReader(reader));
    return reader;
  }

  private Reader configureReaderFromResultSet(ResultSet resultSetFromDb)
      throws SQLException, DaoException {
    Reader reader = new Reader();
    reader.setId(resultSetFromDb.getLong(ReaderQueries.ID));
    reader.setName(resultSetFromDb.getString(ReaderQueries.NAME));
    reader.setLogin(resultSetFromDb.getString(ReaderQueries.LOGIN));
    reader.setPassword(resultSetFromDb.getString(ReaderQueries.PASS));
    reader.setEmail(resultSetFromDb.getString(ReaderQueries.EMAIL));
    reader.setActive(resultSetFromDb.getBoolean(ReaderQueries.IS_ACTIVE));

    reader.setRole(configureRoleFromReader(reader));
    reader.setOrders(getAllOrdersReader(reader.getLogin()));
    return reader;
  }

  @Override
  public Role configureRoleFromReader(Reader reader) throws DaoException {
    RoleRepository roleRepository = new RoleService(connection);
    return roleRepository.getReaderRole(reader);
  }

  /**
   * @see ReaderRepository#isUniqueReaderAndUser(User);
   */
  @Override
  public boolean isUniqueReaderAndUser(User object) throws DaoException {
    UserRepository userRepository = new UserService(connection);
    try {
      if (getByLogin(object.getLogin()) != null
          || getByEmail(object.getEmail()) != null
          || userRepository.getByLogin(object.getLogin()) != null
          || userRepository.getByEmail(object.getEmail()) != null) {
        throw new UserLoginNotUniqueException();
      }
      return true;
    } catch (UserLoginNotUniqueException e) {
      String errorMessage = "Error create Reader, not unique login or email";
      log.error(errorMessage, e);
      return false;
    }
  }

  /**
   * @see ReaderRepository#checkUniqueLoginReader(User)
   */
  @Override
  public boolean checkUniqueLoginReader(User object) throws DaoException {
    UserRepository userRepository = new UserService(connection);
    try {
      if (getByLogin(object.getLogin()) != null ||
          userRepository.getByLogin(object.getLogin()) != null) {
        throw new UserLoginNotUniqueException();
      }
      return true;
    } catch (UserLoginNotUniqueException e) {
      String errorMessage = "Error create/update reader!";
      log.error(errorMessage, e);
      return false;
    }
  }

  /**
   * @see ReaderRepository#checkUniqueEmailReader(User)
   */
  @Override
  public boolean checkUniqueEmailReader(User object) throws DaoException {
    UserRepository userRepository = new UserService(connection);
    try {
      if (getByEmail(object.getEmail()) != null ||
          userRepository.getByEmail(object.getLogin()) != null) {
        throw new UserLoginNotUniqueException();
      }
      return true;
    } catch (UserLoginNotUniqueException e) {
      String errorMessage = "Error create/update reader!";
      log.error(errorMessage, e);
      return false;
    }
  }

  /**
   * @see ReaderRepository#getReaderOrderByBookId(Long, Reader)
   */
  @Override
  public Order getReaderOrderByBookId(Long id, Reader reader) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(ReaderQueries.GET_ORDER_READERS_BY_BOOK_ID)) {
      preparedStatement.setLong(1, id);
      preparedStatement.setString(2, reader.getLogin());
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        Order order = new Order();
        while (resultSet.next()) {
          order = configureOrderFromResultSet(resultSet);
        }
        return order;
      }
    } catch (SQLException e) {
      String errorMessage = "Error, get order!";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see ReaderRepository#updateAllReaderOrdersWithNewLogin(Reader, String)
   **/
  @Override
  public void updateAllReaderOrdersWithNewLogin(Reader newReader, String oldLogin)
      throws DaoException {
    OrderRepository orderRepository = new OrderService(connection);
    List<Order> orders = getAllOrdersReader(oldLogin);
    for (Order x : orders) {
      x.setUserLogin(newReader);
      orderRepository.update(x.getId(), x);
    }
  }

  /**
   * @see ReaderRepository#searchOrderReader(String, Reader)
   */
  @Override
  public List<Order> searchOrderReader(String searchKey, Reader reader) throws DaoException {
    List<Order> orders = new ArrayList<>();
    String searchStatement = "%" + searchKey + "%";

    try (PreparedStatement preparedStatement = connection
        .prepareStatement(ReaderQueries.SEARCH_ORDER)) {
      preparedStatement.setString(1, reader.getLogin());
      preparedStatement.setString(2, searchStatement);
      try (ResultSet set = preparedStatement.executeQuery()) {
        while (set.next()) {
          orders.add(configureOrderFromResultSet(set));
        }
      }
      return sortOrderByStatusName(orders);
    } catch (SQLException e) {
      String errorMessage = "Error, get order!";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see ReaderRepository#getAllReaderOrderWithStatusPenalty(Reader)
   */
  @Transactional
  @Override
  public List<Order> getAllReaderOrderWithStatusPenalty(Reader reader) throws DaoException {
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(ReaderQueries.GET_ALL_READER_ORDER_PENALTY)) {
      preparedStatement.setString(1, reader.getLogin());
      OrderRepository orderRepository = new OrderService(connection);
      return orderRepository.getOrdersFromResultSet(preparedStatement);
    } catch (SQLException e) {
      String error = "Error get orders with status penalty";
      log.error(error + e.getMessage());
      throw new DaoException(error);
    }
  }

  @Transactional
  @Override
  public Integer getAllCountReaderOrders(Reader reader) throws DaoException {
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(ReaderQueries.GET_COUNT_ALL_ORDERS)) {
      preparedStatement.setString(1, reader.getLogin());
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return getCountFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      String error = "Error get count all orders reader";
      log.error(error + e.getMessage());
      throw new DaoException(error);
    }
  }

  @Transactional
  @Override
  public Integer getActiveCountReaderOrders(Reader reader) throws DaoException {
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(ReaderQueries.GET_COUNT_ACTIVE_ORDERS)) {
      preparedStatement.setString(1, reader.getLogin());
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return getCountFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      String error = "Error get count active orders reader";
      log.error(error + e.getMessage());
      throw new DaoException(error);
    }
  }

  @Transactional
  @Override
  public Integer getPenaltyCountReaderOrders(Reader reader) throws DaoException {
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(ReaderQueries.GET_COUNT_PENALTY_ORDERS)) {
      preparedStatement.setString(1, reader.getLogin());
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return getCountFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      String error = "Error get count penalty orders reader";
      log.error(error + e.getMessage());
      throw new DaoException(error);
    }
  }

  private int getCountFromResultSet(ResultSet resultSet) throws SQLException {
    int count = 0;
    while (resultSet.next()) {
      count = resultSet.getInt(ReaderQueries.COUNT);
    }
    return count;
  }
}
