package ua.nure.library.model.role.dao;

import ua.nure.library.exception.DaoException;
import ua.nure.library.model.role.entity.Role;
import ua.nure.library.model.role.entity.RoleName;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.repository.Repository;

/**
 * @author Artem Kudria
 */
public interface RoleRepository extends Repository<Role> {

  /**
   * Save role to database
   *
   * @param role to save in database
   * @throws DaoException if unable to save role from database
   */
  @Override
  void create(Role role) throws DaoException;

  /**
   * Update role by id
   *
   * @param id of role to update
   * @param role with new values
   * @throws DaoException if unable to update role from database
   */
  @Override
  void update(Long id, Role role) throws DaoException;

  /**
   * Delete role
   *
   * @param role to delete
   * @throws DaoException if unable to delete role from database
   */
  @Override
  void delete(Role role) throws DaoException;

  /**
   * Get role by id
   *
   * @param id of role to search
   * @return Role object
   * @throws DaoException if unable to get role from database
   */
  @Override
  Role getById(Long id) throws DaoException;

  /**
   * Get role by name
   *
   * @param roleName to search
   * @return Role object
   * @throws DaoException if unable to get role from database
   */
  Role getByName(RoleName roleName) throws DaoException;

  /**
   * Get User role
   *
   * @param user to get role
   * @return Role object
   * @throws DaoException if unable to get role from database
   */
  Role getUserRole(User user) throws DaoException;

  /**
   * Get Reader role
   *
   * @param reader to get rolex
   * @return Role object
   * @throws DaoException if unable to get role from database
   */
  Role getReaderRole(Reader reader) throws DaoException;
}
