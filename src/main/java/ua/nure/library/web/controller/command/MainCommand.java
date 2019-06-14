package ua.nure.library.web.controller.command;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.author.AuthorRepository;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.dao.genre.GenreRepository;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.util.Messages;
import ua.nure.library.util.PagesPath;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class MainCommand implements Command {


  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
        .getBookRepository();
    GenreRepository genreRepository = RepositoryFactoryProducer.repositoryFactory()
        .getGenreRepository();
    AuthorRepository authorRepository = RepositoryFactoryProducer.repositoryFactory()
        .getAuthorRepository();
    try {
      request.setAttribute("genres", genreRepository.getAll());
      request.setAttribute("authors", authorRepository.getAll());

      String booksAttr = "books";

      if (isServletContextHaveBooks(request, booksAttr)) {
        List<Book> books = bookRepository.getAll();
        request.getServletContext().setAttribute(booksAttr, books);
      }

      return PagesPath.MAIN_PAGE;
    } catch (SQLException | DaoException e) {
      log.error(Messages.ERROR_MAIN_SERVLET, e);
      return ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_MAIN_SERVLET;
    }
  }

  private boolean isServletContextHaveBooks(HttpServletRequest request, String booksAttr) {
    return request.getServletContext().getAttribute(booksAttr) == null;
  }
}
