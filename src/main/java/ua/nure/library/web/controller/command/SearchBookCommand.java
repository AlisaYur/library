package ua.nure.library.web.controller.command;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class SearchBookCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
        .getBookRepository();

    String returnPage = Path.MAIN_MENU;
    String searchBookParamName = "search_book";
    String searchParam = request.getParameter(searchBookParamName);
    String searchParamName = "searchParam";
    String booksAttr = "books";

    try {
      request.setCharacterEncoding(ConsEncoding.UTF_8);
      if (isParamNotEmpty(searchParam)) {
        List<Book> listSearchBook = bookRepository.searchBookByTitleOrAuthor(searchParam);
        request.getServletContext().setAttribute(booksAttr, listSearchBook);
      } else {
        request.getServletContext().setAttribute(booksAttr, null);
      }
      setSearchParam(request, searchParamName, searchParam);
    } catch (IOException | DaoException e) {
      log.error(Messages.UNABLE_PARSE_BOOKS, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.UNABLE_PARSE_BOOKS;
    }
    return returnPage;
  }

  private void setSearchParam(HttpServletRequest request, String searchParamName,
      String searchParam) {
    if (request.getSession().isNew()) {
      request.getSession(false).setAttribute(searchParamName, searchParam);
    } else {
      request.getSession().setAttribute(searchParamName, searchParam);
    }
  }

  private boolean isParamNotEmpty(String searchParam) {
    return null != searchParam && !Objects.requireNonNull(searchParam).equals("");
  }
}
