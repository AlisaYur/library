package ua.nure.library.model.book.dao.book.sorting;

import java.sql.Connection;
import java.util.List;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.book.BookService;
import ua.nure.library.model.book.entity.Book;

/**
 * @author Artem Kudria
 */
public class SortByAuthor implements BookSortProcessor {

  private BookService bookService;

  public SortByAuthor(Connection connection) {
    this.bookService = new BookService(connection);
  }

  @Override
  public List<Book> sort(String param, List<Book> books) {
    return param.equalsIgnoreCase("author_asc")
        ? bookService.getBooksSortedByAuthorAsc(books) : bookService.getBooksSortedByAuthorDesc(books);
  }
}
