package ua.nure.library.model.order.dao;

/**
 * @author Artem Kudria
 */
public final class OrderQueries {

  public static final String ID = "id";
  public static final String BOOK_ID = "book_id";
  public static final String TYPE_ISSUE = "type_issue";
  public static final String START_DATE = "start_date";
  public static final String DATE_OF_DELIVERY = "date_of_delivery";
  public static final String PENALTY = "penalty";
  public static final String STATUS = "status";
  public static final String PAY_ID = "pay_id";
  static final String USER_LOGIN = "user_login";
  static final String INSERT_ORDER =
      "INSERT INTO orders(book_id, user_login, type_issue, start_date, " +
          "date_of_delivery, penalty, status, pay_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
  static final String DELETE_ORDER = "DELETE FROM orders WHERE id = ?";
  static final String SELECT_BY_ID = "SELECT * FROM orders WHERE id = ?";
  static final String UPDATE_ORDER =
      "UPDATE orders SET book_id = ?, user_login = ?, type_issue = ?, start_date = ?, " +
          "date_of_delivery = ?, penalty = ?, status = ?, pay_id = ? WHERE id = ?";

  static final String GET_ALL_ORDERS =
      "SELECT *, r.id as user_id FROM orders o JOIN books b ON o.book_id = b.id " +
          "JOIN reader r ON o.user_login = r.login";

  static final String CHANGE_STATUS = "UPDATE orders SET status = ? WHERE id = ?";
  static final String GET_ALL_ORDERS_BY_TITLEBOOK_OR_READER_NAME =
      "SELECT o.* FROM orders o " +
          "JOIN books b on o.book_id = b.id " +
          "JOIN reader r ON o.user_login = r.login " +
          "WHERE b.title LIKE ? OR r.login LIKE ?";
  static final String GET_ORDER_BY_PAY_ID =
      "SELECT o.* FROM orders o " +
          "JOIN books b on o.book_id = b.id " +
          "JOIN reader r ON r.login = o.user_login " +
          "WHERE o.pay_id = ?";

  private OrderQueries() {
  }
}
