package ua.nure.library.model.book.dao.book;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Optional;
import lombok.extern.log4j.Log4j;
import ua.nure.library.model.book.dao.book.sorting.BookSortProcessor;
import ua.nure.library.model.book.dao.book.sorting.SortByAuthor;
import ua.nure.library.model.book.dao.book.sorting.SortByCount;
import ua.nure.library.model.book.dao.book.sorting.SortByDatePublication;
import ua.nure.library.model.book.dao.book.sorting.SortByGenre;
import ua.nure.library.model.book.dao.book.sorting.SortByHouse;
import ua.nure.library.model.book.dao.book.sorting.SortByTitle;

/**
 * @author Artem Kudria
 */
@Log4j
class BookSortFactory {

  private static final HashMap<String, BookSortProcessor> sortOperations = new HashMap<>();

  BookSortFactory(Connection connection) {
    log.debug("Initialization of sort commands hashmap");
    sortOperations.put("title_asc", new SortByTitle(connection));
    sortOperations.put("title_dsc", new SortByTitle(connection));
    sortOperations.put("author_asc", new SortByAuthor(connection));
    sortOperations.put("author_dsc", new SortByAuthor(connection));
    sortOperations.put("genre_asc", new SortByGenre(connection));
    sortOperations.put("genre_dsc", new SortByGenre(connection));
    sortOperations.put("house_asc", new SortByHouse(connection));
    sortOperations.put("house_dsc", new SortByHouse(connection));
    sortOperations.put("publ_date_asc", new SortByDatePublication(connection));
    sortOperations.put("publ_date_dsc", new SortByDatePublication(connection));
    sortOperations.put("count_asc", new SortByCount(connection));
    sortOperations.put("count_dsc", new SortByCount(connection));
  }

  synchronized Optional<BookSortProcessor> getSortedBooks(final String operation) {
    return Optional.ofNullable(sortOperations.get(operation));
  }
}
