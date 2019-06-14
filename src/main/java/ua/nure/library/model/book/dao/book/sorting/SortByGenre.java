package ua.nure.library.model.book.dao.book.sorting;

import java.sql.Connection;
import java.util.List;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.book.BookService;
import ua.nure.library.model.book.entity.Book;

/**
 * @author Artem Kudria
 */
public class SortByGenre implements BookSortProcessor {

  private BookService bookService;

  public SortByGenre(Connection connection) {
    this.bookService = new BookService(connection);
  }

  @Override
  public List<Book> sort(String param, List<Book> books) throws DaoException {
    return param.equalsIgnoreCase("genre_asc")
        ? bookService.getBooksSortedByGenreAsc(books) : bookService.getBooksSortedByGenreDesc(books);
  }
}
