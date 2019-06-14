package ua.nure.library.model.verifytoken.dao;

/**
 * @author Artem Kudria
 */
final class VerifyTokenQueries {

  public static final String ID = "id";
  public static final String INSERT = "INSERT INTO verify_token (token, user_login, expiry_date) VALUES (?, ?, ?)";
  public static final String DELETE = "DELETE FROM verify_token WHERE id = ?";
  static final String TOKEN = "token";
  static final String USER = "user_login";
  static final String EXPIRY_DATE = "expiry_date";
  static final String UPDATE = "UPDATE verify_token SET token = ?, user_login = ?, expiry_date = ? WHERE id = ?";
  static final String GET_BY_ID = "SELECT * FROM verify_token WHERE id = ?";
  static final String GET_BY_TOKEN = "SELECT * FROM verify_token WHERE token = ?";
  static final String GET_ALL = "SELECT * FROM verify_token";


  private VerifyTokenQueries() {
  }
}
