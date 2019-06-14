package ua.nure.library.model.book.dao.author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.dao.book.BookService;
import ua.nure.library.model.book.entity.Author;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.util.transaction.Transactional;

/**
 * @author Artem Kudria
 */
@Log4j
public class AuthorService implements AuthorRepository {

  private Connection connection;

  public AuthorService(Connection connection) {
    this.connection = connection;
  }

  /**
   * @see AuthorRepository#create(Author);
   */
  @Override
  public void create(final Author author) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(AuthorQueries.INSERT_AUTHOR)) {
      preparedStatement.setString(1, author.getFirstName());
      preparedStatement.setString(2, author.getLastName());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      String errorMessage = "Error create Author " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see AuthorRepository#update(Long, Author);
   */
  @Transactional
  @Override
  public void update(final Long id, final Author author) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(AuthorQueries.UPDATE_AUTHOR)) {
      preparedStatement.setString(1, author.getFirstName());
      preparedStatement.setString(2, author.getLastName());
      preparedStatement.setLong(3, author.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      String errorMessage = "Error update Author " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see AuthorRepository#delete(Author);
   */
  @Override
  public void delete(final Author author) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(AuthorQueries.DELETE_AUTHOR)) {
      preparedStatement.setLong(1, author.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      String errorMessage = "Error delete Author " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see AuthorRepository#getById(Long);
   */
  @Override
  public Author getById(final Long id) throws DaoException {
    Author author = new Author();
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(AuthorQueries.GET_AUTHOR_BY_ID)) {
      preparedStatement.setLong(1, id);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          author.setId(resultSet.getLong(AuthorQueries.ID));
          author.setFirstName(resultSet.getString(AuthorQueries.FIRST_NAME));
          author.setLastName(resultSet.getString(AuthorQueries.LAST_NAME));
        }
      }
    } catch (SQLException e) {
      String errorMessage = "Error unable to find Author " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
    return author;
  }

  /**
   * @see AuthorRepository#getByLastName(String);
   */
  @Override
  public Author getByLastName(final String lastName) throws DaoException {
    Author author = new Author();
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(AuthorQueries.GET_AUTHOR_BY_LAST_NAME)) {
      preparedStatement.setString(1, lastName);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          author.setId(resultSet.getLong(AuthorQueries.ID));
          author.setLastName(resultSet.getString(AuthorQueries.LAST_NAME));
          author.setFirstName(resultSet.getString(AuthorQueries.FIRST_NAME));
        }
      }
    } catch (SQLException e) {
      String errorMessage = "Error unable to find Author by LastName" + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
    return author;
  }

  /**
   * @see AuthorRepository#getAll();
   */
  @Transactional
  @Override
  public List<Author> getAll() throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(AuthorQueries.GET_ALL_AUTHORS)) {
      return getAuthorsFromResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error unable to find Authors " + e.getMessage();
      log.error(errorMessage);
      throw new DaoException(e.getMessage());
    }
  }

  @Transactional
  public List<Author> searchAuthorsByFirstOrLastName(final String searchParam) throws DaoException {
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(AuthorQueries.SEARCH_AUTHORS)) {
      preparedStatement.setString(1, searchParam);
      preparedStatement.setString(2, "%" + searchParam + "%");
      return getAuthorsFromResultSet(preparedStatement);
    } catch (SQLException e) {
      String errorMessage = "Error, get order!";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
  }

  private List<Author> getAuthorsFromResultSet(PreparedStatement preparedStatement)
      throws DaoException {
    List<Author> authors = new ArrayList<>();
    List<Book> books;
    BookRepository bookRepository = new BookService(connection);
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        Author author = new Author();
        author.setId(resultSet.getLong(AuthorQueries.ID));
        author.setFirstName(resultSet.getString(AuthorQueries.FIRST_NAME));
        author.setLastName(resultSet.getString(AuthorQueries.LAST_NAME));
        books = bookRepository.getByAuthor(resultSet.getString(AuthorQueries.LAST_NAME));
        author.setBooks(books);

        authors.add(author);
      }
    } catch (SQLException e) {
      String errorMessage = "Error, get Authors!";
      log.error(errorMessage, e);
      throw new DaoException(e.getMessage());
    }
    return authors;
  }
}
