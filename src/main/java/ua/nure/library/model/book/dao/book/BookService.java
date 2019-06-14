package ua.nure.library.model.book.dao.book;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.author.AuthorQueries;
import ua.nure.library.model.book.dao.book.sorting.BookSortProcessor;
import ua.nure.library.model.book.dao.genre.GenreQueries;
import ua.nure.library.model.book.entity.Author;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.model.book.entity.Genre;
import ua.nure.library.util.Messages;
import ua.nure.library.util.transaction.Transactional;

/**
 * @author Artem Kudria
 */
@Log4j
public class BookService implements BookRepository {

  private Connection connection;

  public BookService(Connection connection) {
    this.connection = connection;
  }

  /**
   * @see BookRepository#create(Book);
   */
  @Override
  public void create(final Book book) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(BookQueries.INSERT_BOOK)) {
      createOrUpdateBook(preparedStatement, book, null);
    } catch (SQLException e) {
      log.error(Messages.ERROR_CREATE_BOOK, e);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see BookRepository#update(Long, Book);
   */
  @Override
  public void update(final Long id, final Book book) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(BookQueries.UPDATE_BOOK)) {
      createOrUpdateBook(preparedStatement, book, id);
    } catch (SQLException e) {
      log.error(Messages.ERROR_UPDATE_BOOK, e);
      throw new DaoException(e.getMessage());
    }
  }

  private void createOrUpdateBook(PreparedStatement preparedStatement, Book book, Long id)
      throws SQLException, DaoException {
    preparedStatement.setString(1, book.getTitle());
    preparedStatement.setLong(2, book.getAuthor().getId());
    preparedStatement.setLong(3, book.getGenre().getId());
    preparedStatement.setDate(4, book.getDateOfPublication());
    preparedStatement.setLong(5, book.getCountInStock());
    preparedStatement.setString(6, book.getPublishingHouse());
    if (isImageLength(book)) {
      preparedStatement.setBytes(7, book.getImage());
    } else {
      preparedStatement.setBytes(7, new byte[]{});
    }

    Optional<Long> updateId = Optional.ofNullable(id);
    if (updateId.isPresent()) {
      if (isBookImageNotNull(book)) {
        preparedStatement.setBytes(7, Base64.getDecoder().decode(book.getImage()));
      } else if (isImageLength(book)) {
        preparedStatement.setBytes(7, book.getImage());
      } else {
        Book findBook = getByTitle(book.getTitle());
        if (isFindBookNotNull(findBook)) {
          preparedStatement.setBytes(7, Base64.getDecoder().decode(findBook.getImage()));
        } else {
          preparedStatement.setBytes(7, null);
        }
      }
      preparedStatement.setLong(8, id);
    }
    preparedStatement.executeUpdate();
  }

  private boolean isFindBookNotNull(Book findBook) {
    return findBook != null && findBook.getImage() != null;
  }

  private boolean isImageLength(Book book) {
    return null != book.getImage() && book.getImage().length > 0;
  }

  private boolean isBookImageNotNull(Book book) {
    return null != book.getImage() && book.getImage().length > 0
        && org.apache.commons.codec.binary.Base64.isBase64(book.getImage());
  }

  /**
   * @see BookRepository#delete(Book);
   */
  @Override
  public void delete(final Book book) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(BookQueries.DELETE_BOOK)) {
      preparedStatement.setLong(1, book.getId());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      log.error(Messages.ERROR_DELETE_BOOK, e);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see BookRepository#getById(Long);
   */
  @Override
  public Book getById(final Long id) throws DaoException {
    Book book;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(BookQueries.GET_BOOK_BY_ID)) {
      preparedStatement.setLong(1, id);

      book = getBookFromResultSet(preparedStatement);
    } catch (SQLException e) {
      log.error(Messages.ERROR_SELECT_BOOK, e);
      throw new DaoException(e.getMessage());
    }
    return book;
  }

  /**
   * @see BookRepository#getByTitle(String);
   */
  @Override
  public Book getByTitle(final String bookTitle) throws DaoException {
    if (bookTitle.equals("") || bookTitle.equals(" ")) {
      log.error(Messages.ERROR_BOOK_EMPTY);
      throw new DaoException(Messages.ERROR_BOOK_EMPTY);
    }
    Book book;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(BookQueries.GET_BOOK_BY_NAME)) {
      preparedStatement.setString(1, bookTitle);
      book = getBookFromResultSet(preparedStatement);
    } catch (SQLException e) {
      log.error(Messages.ERROR_SELECT_BOOK, e);
      throw new DaoException(e.getMessage());
    }
    return book;
  }

  /**
   * @see BookRepository#getByAuthor(String);
   */
  @Override
  public List<Book> getByAuthor(final String authorLastName) throws DaoException {
    if (authorLastName.equals("") || authorLastName.equals(" ")) {
      log.error(Messages.ERROR_AUTHOR_EMPTY);
      throw new DaoException(Messages.ERROR_AUTHOR_EMPTY);
    }
    List<Book> books;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(BookQueries.GET_BOOK_BY_AUTHOR)) {
      preparedStatement.setString(1, authorLastName);

      books = getAllBooksFromResultSet(preparedStatement);
    } catch (SQLException e) {
      log.error(Messages.ERROR_SELECT_BOOKS_BY_AUTHOR, e);
      throw new DaoException(e.getMessage());
    }
    return books;
  }

  /**
   * @see BookRepository#getByGenre(String);
   */
  @Override
  public List<Book> getByGenre(final String genreName) throws DaoException {
    if (genreName.equals("") || genreName.equals(" ")) {
      log.error(Messages.ERROR_GENRE_EMPTY);
      throw new DaoException(Messages.ERROR_GENRE_EMPTY);
    }
    List<Book> books;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(BookQueries.GET_BOOK_BY_GENRE)) {
      preparedStatement.setString(1, genreName);

      books = getAllBooksFromResultSet(preparedStatement);
    } catch (SQLException e) {
      log.error(Messages.ERROR_SELECT_BOOKS_BY_GENRE, e);
      throw new DaoException(e.getMessage());
    }
    return books;
  }

  /**
   * @see BookRepository#getByPublishingHouse(String);
   */
  @Override
  public List<Book> getByPublishingHouse(final String publishingHouseName) throws DaoException {
    if (publishingHouseName.equals("") || publishingHouseName.equals(" ")) {
      log.error(Messages.ERROR_PUBL_HOUSE_EMPTY);
      throw new DaoException(Messages.ERROR_PUBL_HOUSE_EMPTY);
    }
    List<Book> books;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(BookQueries.GET_BOOK_BY_PUBLISHING_HOUSE)) {
      preparedStatement.setString(1, publishingHouseName);
      books = getAllBooksFromResultSet(preparedStatement);
    } catch (SQLException e) {
      log.error(Messages.ERROR_SELECT_BOOKS_BY_PUBL_HOUSE, e);
      throw new DaoException(e.getMessage());
    }
    return books;
  }

  /**
   * @see BookRepository#getAll();
   */
  @Transactional
  @Override
  public List<Book> getAll() throws DaoException {
    List<Book> books;
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(BookQueries.GET_ALL_BOOKS)) {
      books = getAllBooksFromResultSet(preparedStatement);
    } catch (SQLException e) {
      log.error(Messages.ERROR_SELECT_BOOKS, e);
      throw new DaoException(e.getMessage());
    }
    return books.stream()
        .sorted(Comparator.comparing(Book::getId))
        .collect(Collectors.toList());
  }

  /**
   * Service method to get Book from Db by PreparedStatement
   *
   * @param preparedStatement of current method
   * @return Book object
   * @throws DaoException if unable to select Book
   */
  private Book getBookFromResultSet(final PreparedStatement preparedStatement) throws DaoException {
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      return resultSet.next() ? configureBook(resultSet) : null;
    } catch (SQLException e) {
      log.error(Messages.ERROR_SELECT_BOOK, e);
      throw new DaoException(e.getMessage());
    }
  }


  /**
   * Service method to get Books from Db by PreparedStatement
   *
   * @param preparedStatement of current method
   * @return Book object
   * @throws DaoException if unable to select Books
   */
  private List<Book> getAllBooksFromResultSet(final PreparedStatement preparedStatement)
      throws DaoException {
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      return configureListBookFromResultSet(resultSet);
    } catch (SQLException e) {
      log.error(Messages.ERROR_SELECT_BOOKS, e);
      throw new DaoException(e.getMessage());
    }
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private byte[] getImageByteArrayFromResultSet(Optional<byte[]> imageFromDb) {
    return imageFromDb.map(bytes -> Base64.getEncoder().encode(bytes)).orElse(null);
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private String generateImageEncoded(Optional<byte[]> img) {
    String base64Encoded = img.map(bytes -> new String(bytes, StandardCharsets.UTF_8)).orElse(null);
    return base64Encoded != null ? "data:image/png;base64," + base64Encoded : null;
  }

  private List<Book> configureListBookFromResultSet(ResultSet resultSet) throws SQLException {
    List<Book> books = new ArrayList<>();
    while (resultSet.next()) {
      books.add(configureBook(resultSet));
    }
    return books;
  }

  private Book configureBook(ResultSet resultSet) throws SQLException {
    Author author = new Author();
    Genre genre = new Genre();
    Book book = new Book();
    genre.setId(resultSet.getLong(BookQueries.GENRE));
    genre.setName(resultSet.getString(GenreQueries.NAME));
    author.setId(resultSet.getLong(BookQueries.AUTHOR));
    author.setFirstName(resultSet.getString(AuthorQueries.FIRST_NAME));
    author.setLastName(resultSet.getString(AuthorQueries.LAST_NAME));
    book.setId(resultSet.getLong(BookQueries.ID));
    book.setTitle(resultSet.getString(BookQueries.NAME));
    book.setGenre(genre);
    book.setAuthor(author);
    book.setCountInStock(resultSet.getLong(BookQueries.COUNT_IN_STOCK));
    book.setPublishingHouse(resultSet.getString(BookQueries.PUBLISHING_HOUSE));
    book.setDateOfPublication(resultSet.getDate(BookQueries.DATE_OF_PUBLICATION));
    book.setImage(
        getImageByteArrayFromResultSet(Optional.ofNullable(resultSet.getBytes(BookQueries.IMAGE))));
    book.setEncodedImage(generateImageEncoded(Optional.ofNullable(book.getImage())));
    return book;
  }

  /**
   * Get Sorted Books ASC from database
   *
   * @return List of Books
   */
  public List<Book> getBooksSortedByTitleAsc(List<Book> books) {
    return books.stream().sorted(Comparator.comparing(Book::getTitle)).collect(Collectors.toList());
  }

  /**
   * Get Sorted Books DESC from database
   *
   * @return List of Books
   */
  public List<Book> getBooksSortedByTitleDesc(List<Book> books) {
    List<Book> collect = books.stream().sorted(Comparator.comparing(Book::getTitle))
        .collect(Collectors.toList());
    Collections.reverse(collect);
    return collect;
  }

  /**
   * Get Sorted Books by Author ASC from database
   *
   * @return List of Books
   */
  public List<Book> getBooksSortedByAuthorAsc(List<Book> books) {
    return books.stream().sorted(Comparator.comparing(x -> x.getAuthor().getFirstName()))
        .collect(Collectors.toList());
  }

  /**
   * Get Sorted Books by Author DESC from database
   *
   * @return List of Books
   */
  public List<Book> getBooksSortedByAuthorDesc(List<Book> books) {
    List<Book> collect = books.stream()
        .sorted(Comparator.comparing(x -> x.getAuthor().getFirstName()))
        .collect(Collectors.toList());
    Collections.reverse(collect);
    return collect;
  }

  /**
   * Get Sorted Books by Genre ASC from database
   *
   * @return List of Books
   */
  public List<Book> getBooksSortedByGenreAsc(List<Book> books) {
    return books.stream().sorted(Comparator.comparing(x -> x.getGenre().getName()))
        .collect(Collectors.toList());
  }

  /**
   * Get Sorted Books by Genre DESC from database
   *
   * @return List of Books
   */
  public List<Book> getBooksSortedByGenreDesc(List<Book> books) {
    List<Book> collect = books.stream()
        .sorted(Comparator.comparing(x -> x.getGenre().getName()))
        .collect(Collectors.toList());
    Collections.reverse(collect);
    return collect;
  }

  /**
   * Get Sorted Books by Publishing House ASC from database
   *
   * @return List of Books
   */
  public List<Book> getBooksSortedByPublishingHouseAsc(List<Book> books) {
    return books.stream().sorted(Comparator.comparing(Book::getPublishingHouse))
        .collect(Collectors.toList());
  }

  /**
   * Get Sorted Books by Publishing House DESC from database
   *
   * @return List of Books
   */
  public List<Book> getBooksSortedByPublishingHouseDesc(List<Book> books) {
    List<Book> collect = books.stream()
        .sorted(Comparator.comparing(Book::getPublishingHouse))
        .collect(Collectors.toList());
    Collections.reverse(collect);
    return collect;
  }

  /**
   * Get Sorted Books by Publication Date ASC from database
   *
   * @return List of Books
   */
  public List<Book> getBooksSortedByPublicationAsc(List<Book> books) {
    return books.stream().sorted(Comparator.comparing(Book::getDateOfPublication))
        .collect(Collectors.toList());
  }

  /**
   * Get Sorted Books by Publication Date DESC from database
   *
   * @return List of Books
   */
  public List<Book> getBooksSortedByPublicationDesc(List<Book> books) {
    List<Book> collect = books.stream()
        .sorted(Comparator.comparing(Book::getDateOfPublication))
        .collect(Collectors.toList());
    Collections.reverse(collect);
    return collect;
  }

  /**
   * Get Sorted Books by Count in Stock ASC from database
   *
   * @return List of Books
   */
  public List<Book> getBooksSortedByCountInStockAsc(List<Book> books) {
    return books.stream().sorted(Comparator.comparing(Book::getCountInStock))
        .collect(Collectors.toList());
  }

  /**
   * Get Sorted Books by Count in Stock DESC from database
   *
   * @return List of Books
   */
  public List<Book> getBooksSortedByCountInStockDesc(List<Book> books) {
    List<Book> collect = books.stream()
        .sorted(Comparator.comparing(Book::getCountInStock))
        .collect(Collectors.toList());
    Collections.reverse(collect);
    return collect;
  }

  /**
   * @see BookSortProcessor#sort(String, List)
   */
  public List<Book> sortBooksByType(String parameter, List<Book> books) {
    try {
      BookSortFactory bookSortFactory = new BookSortFactory(connection);
      Optional<BookSortProcessor> sortedBooks = bookSortFactory.getSortedBooks(parameter);
      if (sortedBooks.isPresent()) {
        return sortedBooks.get().sort(parameter, books);
      } else {
        throw new DaoException(Messages.ERROR_SORT_BOOKS);
      }
    } catch (DaoException e) {
      log.error(Messages.ERROR_SELECT_BOOKS, e);
    }
    return new ArrayList<>();
  }

  /**
   * @see BookRepository#searchBookByTitleOrAuthor(String)
   */
  @Transactional
  @Override
  public List<Book> searchBookByTitleOrAuthor(String search) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(BookQueries.SEARCH_BOOK_BY_TITLE_OR_AUTHOR)) {
      String searchWithAttributes = "%" + search + "%";
      preparedStatement.setString(1, searchWithAttributes);
      preparedStatement.setString(2, searchWithAttributes);
      preparedStatement.setString(3, searchWithAttributes);
      preparedStatement.setString(4, searchWithAttributes);
      preparedStatement.setString(5, searchWithAttributes);

      return getAllBooksFromResultSet(preparedStatement);
    } catch (SQLException | DaoException e) {
      log.error(Messages.ERROR_SELECT_BOOKS_BY_TITLE, e);
      throw new DaoException(e.getMessage());
    }
  }

  /**
   * @see BookRepository#getBooksByFilter(List, List)
   */
  @Transactional
  @Override
  public List<Book> getBooksByFilter(List<String> genres, List<String> authors)
      throws DaoException {
    String queryString = BookQueries.GET_BOOKS_FILTER;

    StringBuilder parameterBuilder = new StringBuilder();

    if (!genres.isEmpty()) {
      parameterBuilder.append(" g.name IN");
      parameterBuilder.append(generateQueriesByList(genres));
      if (!authors.isEmpty()) {
        parameterBuilder.append(" AND ");
      }
    }

    if (!authors.isEmpty()) {
      parameterBuilder.append(" a.last_name IN");
      parameterBuilder.append(generateQueriesByList(authors));
    }

    StringBuilder queries = new StringBuilder(queryString + parameterBuilder);
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(queries.toString())) {
      int count = 1;
      for (int i = 1; i < genres.size() + 1; i++) {
        preparedStatement.setString(count, genres.get(i - 1));
        count++;
      }
      for (int i = 1; i < authors.size() + 1; i++) {
        preparedStatement.setString(count, authors.get(i - 1));
        count++;
      }
      return getAllBooksFromResultSet(preparedStatement);
    } catch (SQLException e) {
      throw new DaoException(Messages.ERROR_GET_BOOKS_BY_FILTER);
    }
  }

  private StringBuilder generateQueriesByList(List<String> list) {
    StringBuilder parameterBuilder = new StringBuilder();
    if (!list.isEmpty()) {
      parameterBuilder.append(" (");
      for (int i = 0; i < list.size(); i++) {
        parameterBuilder.append("?");
        if (list.size() > i + 1) {
          parameterBuilder.append(",");
        }
      }
      parameterBuilder.append(")");
    }
    return parameterBuilder;
  }
}
