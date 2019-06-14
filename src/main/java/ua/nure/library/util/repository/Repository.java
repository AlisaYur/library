package ua.nure.library.util.repository;

import ua.nure.library.exception.DaoException;

/**
 * Main interface for creating entity dao
 *
 * @author Artem Kudria
 */
public interface Repository<T> {

  /**
   * Create object
   *
   * @param object Entity object
   * @throws DaoException Custom exception if create not finish
   */
  void create(T object) throws DaoException;

  /**
   * Update entity object
   *
   * @param id Id object in db
   * @param object Entity object
   * @throws DaoException Custom exception if update throw error
   */
  void update(Long id, T object) throws DaoException;

  /**
   * Delete entity object in db
   *
   * @param object Entity
   * @throws DaoException If object not find, throw exception
   */
  void delete(T object) throws DaoException;

  /**
   * Get entity object from db by id
   *
   * @param id Long
   * @return Object entity
   * @throws DaoException If object not found, throw exception
   */
  T getById(Long id) throws DaoException;
}
