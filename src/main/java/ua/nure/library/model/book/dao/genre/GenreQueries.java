package ua.nure.library.model.book.dao.genre;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Artem Kudria
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GenreQueries {

  public static final String ID = "id";
  public static final String NAME = "name";

  static final String INSERT_GENRE = "INSERT INTO genres(name) VALUES(?)";
  static final String UPDATE_GENRE = "UPDATE genres SET name=? WHERE id=?";
  static final String DELETE_GENRE = "DELETE FROM genres WHERE id=?";
  static final String GET_GENRE_BY_ID = "SELECT * FROM genres WHERE id=?";
  static final String GET_GENRE_BY_NAME = "SELECT * FROM genres WHERE name=?";
  static final String GET_ALL_GENRES = "SELECT * FROM genres";
}
