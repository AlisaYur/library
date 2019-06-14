package ua.nure.library.web.controller.command;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.IOUtils;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.author.AuthorRepository;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.dao.genre.GenreRepository;
import ua.nure.library.model.book.entity.Author;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.model.book.entity.Genre;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
@MultipartConfig
public class SaveBookCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
        .getBookRepository();
    GenreRepository genreRepository = RepositoryFactoryProducer.repositoryFactory()
        .getGenreRepository();
    AuthorRepository authorRepository = RepositoryFactoryProducer.repositoryFactory()
        .getAuthorRepository();

    String returnPage = Path.MAIN_MENU;
    try {
      request.setCharacterEncoding(ConsEncoding.UTF_8);

      String titleParamName = "title";
      String bookTitle = request.getParameter(titleParamName);
      Genre genre = genreRepository.getById(Long.parseLong(request.getParameter("genreId")));
      Author author = authorRepository.getById(Long.parseLong(request.getParameter("authorId")));
      Date dateOfPublication = Date.valueOf(request.getParameter("dateOfPublication"));
      long countInStock = Long.parseLong(request.getParameter("countInStock"));
      String bookPublishingHouse = request.getParameter("bookPublishingHouse");

      Part filePart = request.getPart("file");
      InputStream fileContent = filePart.getInputStream();

      if (countInStock < 0) {
        returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.INVALID_COUNT_IN_STOCK;
      } else {
        Book book = Book.builder().author(author).title(bookTitle).countInStock(countInStock)
            .dateOfPublication(dateOfPublication).genre(genre).publishingHouse(bookPublishingHouse)
            .image(IOUtils.toByteArray(fileContent)).build();
        bookRepository.create(book);
      }
      String booksAttr = "books";
      List<Book> books = bookRepository.getAll();
      request.getServletContext().setAttribute(booksAttr, books);
    } catch (IOException e) {
      String errorMessage = "Error! ";
      log.error(errorMessage, e);
    } catch (DaoException e) {
      log.error(Messages.UNABLE_SELECT_GENRE, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.UNABLE_SELECT_GENRE;
    } catch (NumberFormatException e) {
      log.error(Messages.NUMBER_FORMAT_EX, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.NUMBER_FORMAT_EX;
    } catch (Exception e) {
      log.error(Messages.ERROR_SAVE_IMAGE, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_SAVE_IMAGE;
    }
    return returnPage;
  }
}
