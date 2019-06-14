package ua.nure.library.model.book.dao.book.sorting;

import java.sql.Connection;
import java.util.List;
import ua.nure.library.model.book.dao.book.BookService;
import ua.nure.library.model.book.entity.Book;

/**
 * @author Artem Kudria
 */
public class SortByHouse implements BookSortProcessor {

  private BookService bookService;

  public SortByHouse(Connection connection) {
    this.bookService = new BookService(connection);
  }

  @Override
  public List<Book> sort(String param, List<Book> books) {
    return param.equalsIgnoreCase("house_asc")
        ? bookService.getBooksSortedByPublishingHouseAsc(books)
        : bookService.getBooksSortedByPublishingHouseDesc(books);
  }
}
