package ua.nure.library.web.controller.command;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.author.AuthorRepository;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.dao.genre.GenreRepository;
import ua.nure.library.model.book.entity.Author;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.model.book.entity.Genre;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class FilterBooksCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    GenreRepository genreRepository = RepositoryFactoryProducer.repositoryFactory()
        .getGenreRepository();
    AuthorRepository authorRepository = RepositoryFactoryProducer.repositoryFactory()
        .getAuthorRepository();

    String returnPage = Path.MAIN_MENU;

    List<String> filterGenre;
    List<String> filterAuthors;

    String booksFilter = "booksFilter";
    try {
      filterGenre = genreRepository.getAll().stream()
          .filter(s -> request.getParameterValues(s.getName()) != null)
          .map(Genre::getName)
          .collect(Collectors.toList());

      filterAuthors = authorRepository.getAll().stream()
          .filter(x -> request.getParameterValues(x.getLastName()) != null)
          .map(Author::getLastName)
          .collect(Collectors.toList());

      String booksAttr = "books";
      if (filterAuthors.isEmpty() && filterGenre.isEmpty()) {
        return Path.SEARCH_BOOK;
      }
      BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
          .getBookRepository();
      List<Book> books = bookRepository.getAll();
      List<Book> filteringBooks = books.stream().filter(x ->
          filterGenre.contains(x.getGenre().getName()) && filterAuthors
              .contains(x.getAuthor().getLastName()) || filterGenre.contains(x.getGenre().getName())
              || filterAuthors.contains(x.getAuthor().getLastName()))
          .collect(Collectors.toList());
      request.getServletContext().setAttribute(booksAttr, filteringBooks);

    } catch (DaoException | SQLException e) {
      log.error(Messages.ERROR_FILTER);
      request.getSession(false).removeAttribute(booksFilter);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_FILTER;
    }
    return returnPage;
  }
}
