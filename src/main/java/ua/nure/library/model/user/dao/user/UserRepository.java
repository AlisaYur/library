package ua.nure.library.model.user.dao.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.model.user.exception.UserLoginNotUniqueException;
import ua.nure.library.util.repository.Repository;

/**
 * @author Artem Kudria
 */
public interface UserRepository extends Repository<User> {

  /**
   * Create User
   *
   * @param object User object
   * @throws DaoException If User not created, throw exception
   */
  @Override
  void create(User object) throws DaoException;

  /**
   * Update User
   *
   * @param id Long id User
   * @param object User object
   * @throws DaoException If User not updated, throw exception
   */
  @Override
  void update(Long id, User object) throws DaoException;

  /**
   * Delete User
   *
   * @param object User object
   * @throws DaoException If User not deleted, throw exception
   */
  @Override
  void delete(User object) throws DaoException;

  /**
   * Get User by id
   *
   * @param id Long id
   * @return User object
   * @throws DaoException If when get user from bd by id, and if error select then throw exception
   */
  @Override
  User getById(Long id) throws DaoException;

  /**
   * Get User by login
   *
   * @param login String
   * @return User object
   * @throws DaoException If when get user from bd by login, and if error select then throw
   * exception
   */
  User getByLogin(String login) throws DaoException;

  /**
   * Get User by name
   *
   * @param name String
   * @return User object
   * @throws DaoException If when get user from bd by name, and if error select then throw
   * exception
   */
  User getByName(String name) throws DaoException;

  /**
   * Get User by email
   *
   * @param email of user
   * @return User object
   */
  User getByEmail(String email) throws DaoException;

  /**
   * If id equals null, then create, else update First insert user, then insert roles for user,
   * which we send
   *
   * @param preparedStatement Sql object
   * @param object User
   * @param id Long
   * @throws SQLException If db return error, throw exception
   */
  void executeCreateOrUpdate(PreparedStatement preparedStatement,
      User object, Long id) throws SQLException, UserLoginNotUniqueException, DaoException;

  /**
   * Get all librarians from db
   *
   * @return List User
   */
  List<User> getAllManagers() throws DaoException;
}
