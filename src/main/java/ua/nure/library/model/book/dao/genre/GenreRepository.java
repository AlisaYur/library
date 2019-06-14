package ua.nure.library.model.book.dao.genre;

import java.util.List;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.entity.Genre;
import ua.nure.library.util.repository.Repository;

/**
 * @author Artem Kudria
 */

public interface GenreRepository extends Repository<Genre> {

  /**
   * Save Genre in database
   *
   * @param genre to save
   * @throws DaoException if unable to save Genre
   */
  @Override
  void create(Genre genre) throws DaoException;

  /**
   * Update Genre in database
   *
   * @param id of genre to update
   * @param genre with new values
   * @throws DaoException if unable to update Genre
   */
  @Override
  void update(Long id, Genre genre) throws DaoException;

  /**
   * Delete Genre from database
   *
   * @param genre to delete
   * @throws DaoException if unable to delete Genre
   */
  @Override
  void delete(Genre genre) throws DaoException;

  /**
   * Get Genre by id from database
   *
   * @param id of Genre to select
   * @return Genre object
   * @throws DaoException if unable to select Genre
   */
  @Override
  Genre getById(Long id) throws DaoException;

  /**
   * Get Genre by name from database
   *
   * @param genreName to select
   * @return Genre object
   * @throws DaoException if unable to select Genre
   */
  Genre getByName(final String genreName) throws DaoException;

  /**
   * Get all Genres from database
   *
   * @return List of Genres
   * @throws DaoException if unable to select Genres
   */
  List<Genre> getAll() throws DaoException;
}
