package ua.nure.library.model.order.dao;

import java.sql.PreparedStatement;
import java.util.List;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.order.entity.OrderStatus;
import ua.nure.library.util.repository.Repository;

/**
 * @author Artem Kudria
 */
public interface OrderRepository extends Repository<Order> {

  /**
   * Create order
   *
   * @param object Order
   * @throws DaoException If get error, throw exception
   */
  @Override
  void create(Order object) throws DaoException;

  /**
   * Update order
   *
   * @param id Long
   * @param object Order object
   * @throws DaoException If get error, throw exception
   */
  @Override
  void update(Long id, Order object) throws DaoException;

  /**
   * Delete order by id
   *
   * @param object Order
   * @throws DaoException If get error, throw exception
   */
  @Override
  void delete(Order object) throws DaoException;

  /**
   * Get Order by id
   *
   * @param id Long
   * @return Order object
   * @throws DaoException If get error, throw exception
   */
  @Override
  Order getById(Long id) throws DaoException;

  /**
   * Get all orders
   *
   * @return List orders
   */
  List<Order> getAll() throws DaoException;

  List<Order> getOrdersFromResultSet(PreparedStatement preparedStatement) throws DaoException;

  /**
   * Update order status
   *
   * @param newStatus String new status
   * @param order Order with id
   */
  void changeStatus(OrderStatus newStatus, Order order) throws DaoException;

  /**
   * Find all orders by book title or reader login
   *
   * @param searchKey String
   * @return List orders
   * @throws DaoException DbException
   */
  List<Order> searchOrder(String searchKey) throws DaoException;

  /**
   * Get order by pay id
   *
   * @param payId String
   * @return Order
   */
  Order getOrderByPayId(String payId) throws DaoException;

  /**
   * Generate unqiue hash id for payment
   *
   * @param id Long order id
   * @return String
   * @throws DaoException DbException
   */
  String generateUniquePayId(Long id) throws DaoException;
}
