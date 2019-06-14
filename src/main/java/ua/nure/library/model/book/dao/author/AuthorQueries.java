package ua.nure.library.model.book.dao.author;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Artem Kudria
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthorQueries {

  public static final String ID = "id";
  public static final String FIRST_NAME = "first_name";
  public static final String LAST_NAME = "last_name";

  static final String INSERT_AUTHOR = "INSERT INTO authors(first_name, last_name) VALUES(?, ?)";
  static final String UPDATE_AUTHOR = "UPDATE authors SET first_name =?, last_name =? WHERE id =?";
  static final String DELETE_AUTHOR = "DELETE FROM authors WHERE id=?";
  static final String GET_AUTHOR_BY_ID = "SELECT * FROM authors WHERE id=?";
  static final String GET_AUTHOR_BY_LAST_NAME = "SELECT * FROM authors WHERE last_name=? LIMIT 1";
  static final String GET_ALL_AUTHORS = "SELECT * FROM authors";
  static final String SEARCH_AUTHORS = "SELECT * FROM authors WHERE first_name LIKE ? OR last_name LIKE ?";
}
