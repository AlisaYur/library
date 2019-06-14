package ua.nure.library.model.book.dao.book;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Artem Kudria
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookQueries {

  public static final String ID = "id";
  public static final String NAME = "title";
  static final String AUTHOR = "author_id";
  static final String PUBLISHING_HOUSE = "publishing_house";
  static final String GENRE = "genre_id";
  static final String DATE_OF_PUBLICATION = "date_of_publication";
  static final String COUNT_IN_STOCK = "count_in_stock";
  static final String IMAGE = "image";
  static final String INSERT_BOOK = "INSERT INTO books(title, author_id, genre_id, " +
      "date_of_publication, count_in_stock, publishing_house, image) VALUES(?, ?, ?, ?, ?, ?, ?)";
  static final String UPDATE_BOOK = "UPDATE books SET title=?, author_id=?, genre_id=?, " +
      "date_of_publication=?, count_in_stock=?, publishing_house=?, image = ? WHERE id=?";
  static final String DELETE_BOOK = "DELETE FROM books WHERE id=?";
  static final String GET_BOOK_BY_NAME =
      "SELECT * FROM books JOIN authors ON books.author_id = authors.id  " +
          "JOIN genres ON books.genre_id = genres.id where books.title = ?";
  static final String GET_ALL_BOOKS = "SELECT * FROM books " +
      "JOIN authors ON books.author_id = authors.id " +
      "JOIN genres ON books.genre_id = genres.id";
  static final String SEARCH_BOOK_BY_TITLE_OR_AUTHOR = "SELECT * FROM books b" +
      " JOIN authors a on b.author_id = a.id" +
      " JOIN genres g on b.genre_id = g.id" +
      " WHERE upper(b.title) LIKE upper(?) OR upper(a.first_name) LIKE upper(?) OR upper(a.last_name) LIKE upper(?) OR upper(a.first_name)"
      +
      " LIKE upper(split_part(?, ' ', 1)) AND upper(a.last_name) LIKE upper(split_part(?, ' ', 2))";
  static final String GET_BOOKS_FILTER =
      "SELECT * FROM books b " +
          "JOIN authors a on b.author_id = a.id " +
          "JOIN genres g on b.genre_id = g.id " +
          "WHERE";
  private static final String SELECT_QUERY_PART = "SELECT * FROM books JOIN authors " +
      "ON books.author_id = authors.id JOIN genres " +
      "ON books.genre_id = genres.id ";
  static final String GET_BOOK_BY_ID = SELECT_QUERY_PART + "where books.id = ?";
  static final String GET_BOOK_BY_AUTHOR = SELECT_QUERY_PART + "where authors.last_name = ?";
  static final String GET_BOOK_BY_GENRE = SELECT_QUERY_PART + "where genres.name = ?";
  static final String GET_BOOK_BY_PUBLISHING_HOUSE =
      SELECT_QUERY_PART + "where books.publishing_house = ?";
}
