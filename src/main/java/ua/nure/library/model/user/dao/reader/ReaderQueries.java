package ua.nure.library.model.user.dao.reader;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Artem Kudria
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReaderQueries {

  public static final String ID = "id";
  public static final String NAME = "name";
  static final String LOGIN = "login";
  static final String PASS = "password";
  static final String EMAIL = "email";
  static final String IS_ACTIVE = "isActive";
  static final String USER_ID = "user_id";
  static final String COUNT = "count";

  static final String GET_ORDER_READERS_BY_BOOK_ID =
      "SELECT o.*, l.*, l.id as user_id FROM orders o " +
          "JOIN books b on o.book_id = b.id " +
          "JOIN reader l ON o.user_login = l.login WHERE b.id = ? AND l.login = ?";
  static final String GET_ALL_ORDERS_BY_USER =
      "SELECT o.*, l.*, l.id as user_id FROM orders o JOIN books b ON o.book_id = b.id " +
          "JOIN reader l ON o.user_login = l.login WHERE o.user_login = ?";
  static final String INSERT_READER =
      "INSERT INTO reader(name, login, password, email, isActive, role_id) " +
          "VALUES(?, ?, ?, ?, ?, ?)";
  static final String DELETE_READER = "DELETE FROM reader WHERE id = ?";
  static final String SELECT_BY_ID = "SELECT * FROM reader WHERE id = ?";
  static final String SELECT_BY_LOGIN = "SELECT * FROM reader WHERE login = ?";
  static final String SELECT_BY_NAME = "SELECT * FROM reader WHERE name = ?";
  static final String SELECT_BY_EMAIL = "SELECT * FROM reader WHERE email = ?";
  static final String UPDATE_READER = "UPDATE reader SET name = ?, login = ?, password = ?, " +
      "email = ?, isActive = ?, role_id = ? WHERE id = ?";
  static final String SELECT_ALL_READERS = "SELECT * FROM reader";
  static final String SEARCH_READERS = "SELECT * FROM reader WHERE name LIKE ? OR login LIKE ? OR email LIKE ?";
  static final String SEARCH_ORDER =
      "SELECT o.* FROM orders o " +
          "JOIN books b on o.book_id = b.id " +
          "JOIN reader r ON o.user_login = r.login WHERE o.user_login = ? AND b.title LIKE ?";
  static final String GET_ALL_READER_ORDER_PENALTY =
      "SELECT *, l.*, l.id as user_id FROM orders o " +
          "JOIN books b ON o.book_id = b.id " +
          "JOIN reader l ON o.user_login = l.login " +
          "WHERE o.user_login = ? AND o.status = 'PENALTY'";
  private static final String SELECT_COUNT = "SELECT count(*) as count FROM orders ";
  static final String GET_COUNT_ACTIVE_ORDERS = SELECT_COUNT +
      "WHERE user_login = ? AND NOT status = 'PENALTY' AND NOT status = 'CLOSE'";
  static final String GET_COUNT_ALL_ORDERS = SELECT_COUNT +
      "WHERE user_login = ?";
  static final String GET_COUNT_PENALTY_ORDERS = SELECT_COUNT +
      "WHERE user_login = ? AND status = 'PENALTY'";
}
