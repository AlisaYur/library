package ua.nure.library.model.book.dao.author;

import java.util.List;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.entity.Author;
import ua.nure.library.util.repository.Repository;

/**
 * @author Artem Kudria
 */

public interface AuthorRepository extends Repository<Author> {

  /**
   * Save Author in database
   *
   * @param author to save
   * @throws DaoException if unable to save
   */
  @Override
  void create(Author author) throws DaoException;

  /**
   * Update Author in database if exists
   *
   * @param id of author to update
   * @param author with new values
   */
  @Override
  void update(Long id, Author author) throws DaoException;

  /**
   * Delete Author from db
   *
   * @param author to delete
   * @throws DaoException if unable to delete Author
   */
  @Override
  void delete(Author author) throws DaoException;

  /**
   * Get Author by id
   *
   * @param id of author
   * @return Author object
   * @throws DaoException if unable to select Author
   */
  @Override
  Author getById(Long id) throws DaoException;

  /**
   * Get Author by lastName
   *
   * @param lastName of author
   * @return Author object
   */
  Author getByLastName(final String lastName) throws DaoException;

  /**
   * Get all Authors from database
   *
   * @return List of Authors
   * @throws DaoException if unable to select
   */
  List<Author> getAll() throws DaoException;

  /**
   * Search authors by search param
   *
   * @param searchParam to search
   * @return List of Authors
   */
  List<Author> searchAuthorsByFirstOrLastName(final String searchParam) throws DaoException;
}
