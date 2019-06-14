package ua.nure.library.model.user.dao.user;

/**
 * @author Artem Kudria
 */
public final class UserQueries {

  public static final String ID = "id";
  public static final String NAME = "name";
  static final String LOGIN = "login";
  static final String PASS = "password";
  static final String EMAIL = "email";
  static final String IS_ACTIVE = "isActive";

  static final String INSERT_USER =
      "INSERT INTO users(name, login, password, email, isActive, role_id) " +
          "VALUES(?, ?, ?, ?, ?, ?)";
  static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
  static final String SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
  static final String SELECT_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
  static final String SELECT_BY_NAME = "SELECT * FROM users WHERE name = ?";
  static final String SELECT_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
  static final String UPDATE_USER =
      "UPDATE users SET name = ?, login = ?, password = ?, email = ?, " +
          "isActive = ?, role_id = ? WHERE id = ?";
  static final String GET_ALL_MANAGERS = "SELECT * FROM users WHERE role_id = 3";

  private UserQueries() {
  }
}
