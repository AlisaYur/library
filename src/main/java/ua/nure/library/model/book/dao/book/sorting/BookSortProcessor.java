package ua.nure.library.model.book.dao.book.sorting;

import java.util.List;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.entity.Book;

/**
 * @author Artem Kudria
 */
@FunctionalInterface
public interface BookSortProcessor {

  List<Book> sort(String param, List<Book> books) throws DaoException;
}
