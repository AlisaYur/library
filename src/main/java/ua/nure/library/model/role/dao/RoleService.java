package ua.nure.library.model.role.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.role.entity.Role;
import ua.nure.library.model.role.entity.RoleName;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.transaction.Transactional;

/**
 * @author Artem Kudria
 */
@Log4j
@NoArgsConstructor
public class RoleService implements RoleRepository {

  private Connection connection;

  public RoleService(Connection connection) {
    this.connection = connection;
  }

  /**
   * Get Role from result set
   *
   * @param preparedStatement PreparedStatement with queries
   * @return Role object
   * @throws DaoException if error select Role, throw exception
   */
  private Role getRoleFromResultSet(PreparedStatement preparedStatement) throws DaoException {
    Role role = new Role();
    try (ResultSet set = preparedStatement.executeQuery()) {
      while (set.next()) {
        role.setId(set.getLong(RoleQueries.ID));
        role.setName(RoleName.valueOf(set.getString(RoleQueries.NAME)));
      }
      return role;
    } catch (SQLException e) {
      String errorMessage = "Error select Role " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see RoleRepository#create(Role)
   */
  @Override
  public void create(Role object) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(RoleQueries.INSERT_ROLE, Statement.RETURN_GENERATED_KEYS)) {
      executeCreateOrUpdate(preparedStatement, object, null);
      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          object.setId(generatedKeys.getLong(1));
        }
      }
    } catch (SQLException e) {
      String errorMessage = "Error insert Role " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see RoleRepository#update(Long, Role)
   */
  @Override
  public void update(Long id, Role object) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(RoleQueries.UPDATE_ROLE)) {
      executeCreateOrUpdate(preparedStatement, object, id);
    } catch (SQLException e) {
      String errorMessage = "Error update Role " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see RoleRepository#delete(Role)
   */
  @Override
  public void delete(Role object) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(RoleQueries.DELETE_ROLE)) {
      preparedStatement.setLong(1, object.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      String errorMessage = "Error delete Role " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see RoleRepository#getById(Long)
   */
  @Override
  public Role getById(Long id) throws DaoException {
    Role role;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(RoleQueries.SELECT_BY_ID)) {
      preparedStatement.setLong(1, id);
      role = getRoleFromResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error select by id Role, db return error " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
    return role;
  }

  /**
   * @see RoleRepository#getByName(RoleName)
   */
  @Override
  public Role getByName(RoleName roleName) throws DaoException {
    Role role;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(RoleQueries.SELECT_BY_NAME)) {
      preparedStatement.setString(1, roleName.name());
      role = getRoleFromResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error select by name Role, db return error " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
    return role;
  }

  /**
   * If id equals null, then create, else update
   *
   * @param preparedStatement Sql object
   * @param object Role
   * @param id Long
   * @throws SQLException If db return error, throw exception
   */
  private void executeCreateOrUpdate(final PreparedStatement preparedStatement,
      final Role object, final Long id) throws SQLException {
    preparedStatement.setString(1, String.valueOf(object.getName()));
    Optional<Long> optionalId = Optional.ofNullable(id);
    if (optionalId.isPresent()) {
      preparedStatement.setLong(2, optionalId.get());
    }
    preparedStatement.executeUpdate();
  }

  @Transactional
  @Override
  public Role getUserRole(User user) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(RoleQueries.GET_ROLE_FOR_USER)) {
      preparedStatement.setString(1, user.getLogin());
      return getRoleFromResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error get roles " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  @Transactional
  @Override
  public Role getReaderRole(Reader reader) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(RoleQueries.GET_ROLE_FOR_READER)) {
      preparedStatement.setString(1, reader.getLogin());
      return getRoleFromResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error get reader role " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }
}
