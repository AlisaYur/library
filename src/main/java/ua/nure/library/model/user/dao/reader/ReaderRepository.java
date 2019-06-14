package ua.nure.library.model.user.dao.reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.role.entity.Role;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.repository.Repository;

/**
 * @author Artem Kudria
 */
public interface ReaderRepository extends Repository<Reader> {

  /**
   * Create Reader
   *
   * @param object Reader object
   * @throws DaoException If User not created, throw exception
   */
  @Override
  void create(Reader object) throws DaoException;

  /**
   * Update Reader
   *
   * @param id Long id Reader
   * @param object Reader object
   * @throws DaoException If User not updated, throw exception
   */
  @Override
  void update(Long id, Reader object) throws DaoException;

  /**
   * Delete Reader
   *
   * @param object Reader object
   * @throws DaoException If User not deleted, throw exception
   */
  @Override
  void delete(Reader object) throws DaoException;

  /**
   * Get Reader by id
   *
   * @param id Long id
   * @return Reader object
   * @throws DaoException If when get user from bd by id, and if error select then throw exception
   */
  @Override
  Reader getById(Long id) throws DaoException;

  /**
   * Get Reader by login
   *
   * @param login String
   * @return Reader object
   * @throws DaoException If when get user from bd by login, and if error select then throw
   * exception
   */
  Reader getByLogin(String login) throws DaoException;

  /**
   * Get Reader by name
   *
   * @param name String
   * @return Reader object
   * @throws DaoException If when get user from bd by name, and if error select then throw
   * exception
   */
  Reader getByName(String name) throws DaoException;

  /**
   * Get all readers from database
   *
   * @return List of readers
   */
  List<Reader> getAll() throws DaoException;

  /**
   * Get all user orders
   *
   * @param login String
   * @return List orders
   */
  List<Order> getAllOrdersReader(String login) throws DaoException;

  /**
   * Get reader from database by email
   *
   * @param email of reader
   * @return Reader object
   */
  Reader getByEmail(String email) throws DaoException;

  /**
   * Search Readers in db by name, login or email
   *
   * @param criteria name, email or login
   * @return List of found Readers
   */
  List<Reader> searchReadersByNameLoginOrEmail(String criteria) throws DaoException;

  Reader configureReaderWithoutOrders(ResultSet resultSet) throws DaoException, SQLException;

  Role configureRoleFromReader(Reader reader) throws DaoException;

  /**
   * Check login and email between Users and Readers
   *
   * @param object to check
   * @return true if object unique or false if already in use
   */
  boolean isUniqueReaderAndUser(User object) throws DaoException;

  /**
   * Check unique login
   *
   * @param object User(Reader)
   * @return if unique return true, else false
   * @throws DaoException Db Exception
   */
  boolean checkUniqueLoginReader(User object) throws DaoException;

  /**
   * Check unique email
   *
   * @param object User(Reader)
   * @return if unique return true, else false
   * @throws DaoException Db Exception
   */
  boolean checkUniqueEmailReader(User object) throws DaoException;

  /**
   * Get Order by book id
   *
   * @param id Long book id
   * @param reader Reader object
   * @return Order
   * @throws DaoException Db exception, if not found user or order
   */
  Order getReaderOrderByBookId(Long id, Reader reader) throws DaoException;

  /**
   * Update all readers orders by new login
   *
   * @param newReader Reader
   * @param oldLogin String
   * @throws DaoException DbException
   */
  void updateAllReaderOrdersWithNewLogin(Reader newReader, String oldLogin) throws DaoException;

  /**
   * Search all order reader
   *
   * @param searchKey String
   * @param reader Reader object
   * @return List orders
   */
  List<Order> searchOrderReader(String searchKey, Reader reader) throws DaoException;

  /**
   * Get all orders reader where status equals penalty
   *
   * @param reader Reader entity
   * @return List orders
   */
  List<Order> getAllReaderOrderWithStatusPenalty(Reader reader) throws DaoException;

  /**
   * Get count all orders by reader name
   *
   * @param reader Reader
   * @return Count orders
   */
  Integer getAllCountReaderOrders(Reader reader) throws DaoException;

  /**
   * Get count active orders by reader
   *
   * @param reader Reader
   * @return Count active orders
   */
  Integer getActiveCountReaderOrders(Reader reader) throws DaoException;

  /**
   * Get count penalty orders by reader
   *
   * @param reader Reader
   * @return Count penalty orders
   */
  Integer getPenaltyCountReaderOrders(Reader reader) throws DaoException;
}
