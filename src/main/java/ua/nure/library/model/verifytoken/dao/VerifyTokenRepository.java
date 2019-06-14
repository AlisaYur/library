package ua.nure.library.model.verifytoken.dao;

import java.util.List;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.verifytoken.entity.VerifyToken;
import ua.nure.library.util.repository.Repository;

/**
 * Verify token entity, need for check reader registration, when user create new account, system
 * send message to user email, where user must click confirm, for activate your account.
 *
 * @author Artem Kudria
 */
public interface VerifyTokenRepository extends Repository<VerifyToken> {

  /**
   * Create verify token entity
   *
   * @param object Entity object
   * @throws DaoException If data not valid, throw exception
   */
  @Override
  void create(VerifyToken object) throws DaoException;

  /**
   * Update verify token by id
   *
   * @param id Id object in db
   * @param object Entity object
   * @throws DaoException Custom exception, if entity not found in db
   */
  @Override
  void update(Long id, VerifyToken object) throws DaoException;

  /**
   * Delete user entity
   *
   * @param object Entity
   * @throws DaoException If token not found throw exception
   */
  @Override
  void delete(VerifyToken object) throws DaoException;

  /**
   * Get token by id
   *
   * @param id Long
   * @return Verify token entity
   * @throws DaoException If not found, throw exception
   */
  @Override
  VerifyToken getById(Long id) throws DaoException;

  /**
   * Get entity by token
   *
   * @param token String token
   * @return entity verify token
   * @throws DaoException If not found, throw exception
   */
  VerifyToken getByToken(String token) throws DaoException;

  /**
   * Get all entities
   *
   * @return List tokens
   * @throws DaoException If not found, throw exception
   */
  List<VerifyToken> getAllVerifyToken() throws DaoException;
}
