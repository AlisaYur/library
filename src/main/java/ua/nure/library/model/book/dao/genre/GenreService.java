package ua.nure.library.model.book.dao.genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.entity.Genre;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.util.transaction.Transactional;

/**
 * @author Artem Kudria
 */
@Log4j
public class GenreService implements GenreRepository {

  private Connection connection;

  public GenreService(Connection connection) {
    this.connection = connection;
  }

  /**
   * @see GenreRepository#create(Genre);
   */
  @Override
  public void create(final Genre genre) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(GenreQueries.INSERT_GENRE)) {
      preparedStatement.setString(1, genre.getName());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      String errorMessage = "Error, unable to create Genre " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see GenreRepository#update(Long, Genre);
   */
  @Transactional
  @Override
  public void update(final Long id, final Genre genre) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(GenreQueries.UPDATE_GENRE)) {
      preparedStatement.setString(1, genre.getName());
      preparedStatement.setLong(2, genre.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      String errorMessage = "Error, unable to update Genre " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see GenreRepository#delete(Genre);
   */
  @Override
  public void delete(final Genre genre) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(GenreQueries.DELETE_GENRE)) {
      preparedStatement.setLong(1, genre.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      String errorMessage = "Error, unable to delete Genre " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see GenreRepository#getById(Long);
   */
  @Override
  public Genre getById(final Long id) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(GenreQueries.GET_GENRE_BY_ID)) {
      preparedStatement.setLong(1, id);
      return getGenreFromResultSer(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error, unable to select Genre " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see GenreRepository#getByName(String);
   */
  @Override
  public Genre getByName(final String genreName) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(GenreQueries.GET_GENRE_BY_NAME)) {
      preparedStatement.setString(1, genreName);
      return getGenreFromResultSer(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error, unable to select Genre " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see GenreRepository#getAll();
   */
  @Transactional
  @Override
  public List<Genre> getAll() throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(GenreQueries.GET_ALL_GENRES)) {
      return getAllGenresFromResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error, unable to find Genres " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * Get all genres from result set, requires prepared statement with query
   *
   * @param preparedStatement with query
   * @return List of Genre objects from result set
   * @throws DaoException if unable to get Genre from result set
   */
  private List<Genre> getAllGenresFromResultSet(PreparedStatement preparedStatement)
      throws DaoException {
    BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
        .getBookRepository();
    List<Genre> genres = new ArrayList<>();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong(GenreQueries.ID));
        genre.setName(resultSet.getString(GenreQueries.NAME));
        genre.setBooks(bookRepository.getByGenre(genre.getName()));
        genres.add(genre);
      }
    } catch (SQLException e) {
      String errorMessage = "Error, unable to get Genres from result set" + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
    return genres;
  }

  /**
   * Get genre from result set, requires prepared statement with query
   *
   * @param preparedStatement with query
   * @return Genre object from result set
   * @throws DaoException if unable to get Genre from result set
   */
  private Genre getGenreFromResultSer(PreparedStatement preparedStatement) throws DaoException {
    Genre genre = new Genre();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        genre.setId(resultSet.getLong(GenreQueries.ID));
        genre.setName(resultSet.getString(GenreQueries.NAME));
      }
    } catch (SQLException e) {
      String errorMessage = "Error, unable to get Genre from result set " + e.getMessage();
      log.info(errorMessage);
      throw new DaoException(e.getMessage());
    }
    return genre;
  }
}
