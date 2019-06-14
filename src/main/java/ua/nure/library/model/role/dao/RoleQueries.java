package ua.nure.library.model.role.dao;

/**
 * @author Artem Kudria
 */
public final class RoleQueries {

  public static final String ID = "id";
  public static final String NAME = "name";

  static final String INSERT_ROLE = "INSERT INTO role(name) VALUES (?)";
  static final String DELETE_ROLE = "DELETE FROM role WHERE id = ?";
  static final String SELECT_BY_ID = "SELECT * FROM role WHERE id = ?";
  static final String SELECT_BY_NAME = "SELECT * FROM role WHERE name = ?";
  static final String UPDATE_ROLE = "UPDATE role SET name = ? WHERE id = ?";
  static final String GET_ROLE_FOR_USER = "SELECT r.* FROM role r JOIN users us ON us.role_id = r.id WHERE us.login = ?";
  static final String GET_ROLE_FOR_READER = "SELECT r.* FROM role r JOIN reader rd ON rd.role_id = r.id WHERE rd.login = ?";

  private RoleQueries() {
  }
}
