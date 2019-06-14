package ua.nure.library.model.user.dao.user;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.role.dao.RoleRepository;
import ua.nure.library.model.role.dao.RoleService;
import ua.nure.library.model.role.entity.Role;
import ua.nure.library.model.user.dao.reader.ReaderService;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.passwordUtil.PasswordEncoder;
import ua.nure.library.util.transaction.Transactional;

/**
 * @author Artem Kudria
 */
@Log4j
public class UserService implements UserRepository {

  private Connection connection;

  public UserService(Connection connection) {
    this.connection = connection;
  }

  /**
   * @see UserRepository#create(User)
   */
  @Transactional
  @Override
  public void create(User object) throws DaoException {
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(UserQueries.INSERT_USER)) {
      boolean uniqueReaderAndUser = new ReaderService(connection).isUniqueReaderAndUser(object);
      if (uniqueReaderAndUser) {
        executeCreateOrUpdate(preparedStatement, object, null);
      }
    } catch (SQLException e) {
      String errorMessage = "Error insert User " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see UserRepository#update(Long, User)
   */
  @Transactional
  @Override
  public void update(Long id, User user) throws DaoException {
    try (PreparedStatement updateStatement = connection
        .prepareStatement(UserQueries.UPDATE_USER)) {
      executeCreateOrUpdate(updateStatement, user, id);
    } catch (SQLException e) {
      String errorMessage = "Error update User " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see UserRepository#delete(User)
   */
  @Transactional
  @Override
  public void delete(User object) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(UserQueries.DELETE_USER)) {
      preparedStatement.setLong(1, object.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      String errorMessage = "Error delete User " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see UserRepository#getById(Long)
   */
  @Transactional
  @Override
  public User getById(Long id) throws DaoException {
    User user;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(UserQueries.SELECT_BY_ID)) {
      preparedStatement.setLong(1, id);
      user = getUserFormResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error select by id User, db return error " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
    return user;
  }

  /**
   * @see UserRepository#getByLogin(String)
   */
  @Override
  public User getByLogin(String login) throws DaoException {
    User user;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(UserQueries.SELECT_BY_LOGIN)) {
      preparedStatement.setString(1, login);
      user = getUserFormResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error select by login User, db return error " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
    return user;
  }

  /**
   * @see UserRepository#getByName(String)
   */
  @Override
  public User getByName(String name) throws DaoException {
    User user;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(UserQueries.SELECT_BY_NAME)) {
      preparedStatement.setString(1, name);
      user = getUserFormResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error select by name User, db return error " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
    return user;
  }

  /**
   * @see UserRepository#getByEmail(String)
   */
  @Override
  public User getByEmail(String email) throws DaoException {
    User user;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(UserQueries.SELECT_BY_EMAIL)) {
      preparedStatement.setString(1, email);
      user = getUserFormResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error select by email User, db return error " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
    return user;
  }

  /**
   * Get User from result set
   *
   * @param preparedStatement PreparedStatement with queries
   * @return User object
   * @throws DaoException If error select User, throw exception
   */
  private User getUserFormResultSet(PreparedStatement preparedStatement) throws DaoException {
    User user = null;
    try (ResultSet set = preparedStatement.executeQuery()) {
      while (set.next()) {
        user = configureUserFromResultSet(set);
      }
      return user;
    } catch (SQLException e) {
      String errorMessage = "Error select User " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * If id equals null, then create, else update First insert user, then insert roles for user,
   * which we send
   *
   * @param preparedStatement Sql object
   * @param object User
   * @param id Long
   * @throws SQLException If db return error, throw exception
   */
  @Override
  public void executeCreateOrUpdate(final PreparedStatement preparedStatement,
      final User object, final Long id) throws SQLException {

    preparedStatement.setString(1, object.getName());
    preparedStatement.setString(2, object.getLogin());
    preparedStatement.setString(4, object.getEmail());
    preparedStatement.setBoolean(5, object.isActive());

    Optional<Long> optionalId = Optional.ofNullable(id);
    if (optionalId.isPresent()) {
      preparedStatement.setString(3, object.getPassword());
      preparedStatement.setLong(7, optionalId.get());
    } else {
      try {
        preparedStatement.setString(3, PasswordEncoder.hash(object.getPassword()));
      } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
        String errorMessage = "Error encoding reader password!";
        log.error(errorMessage);
      }
    }

    preparedStatement.setLong(6, object.getRole().getId());
    preparedStatement.executeUpdate();
  }

  private User configureUserFromResultSet(ResultSet resultSet) throws SQLException, DaoException {
    RoleRepository roleRepository = new RoleService(connection);
    User user = new User();
    user.setId(resultSet.getLong(UserQueries.ID));
    user.setLogin(resultSet.getString(UserQueries.LOGIN));
    user.setName(resultSet.getString(UserQueries.NAME));
    user.setPassword(resultSet.getString(UserQueries.PASS));
    user.setEmail(resultSet.getString(UserQueries.EMAIL));
    user.setActive(resultSet.getBoolean(UserQueries.IS_ACTIVE));
    Role role = roleRepository.getUserRole(user);
    user.setRole(role);
    return user;
  }

  /**
   * @see UserRepository#getAllManagers()
   */
  @Transactional
  @Override
  public List<User> getAllManagers() throws DaoException {
    List<User> users = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(UserQueries.GET_ALL_MANAGERS)) {
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          users.add(configureUserFromResultSet(resultSet));
        }
        return users;
      }
    } catch (SQLException e) {
      String errMsq = "Error get user form db" + e.getMessage();
      log.error(errMsq);
      throw new DaoException(errMsq);
    }
  }
}
