package ua.nure.library.web.controller.command;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
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
public class UpdateBookCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    GenreRepository genreRepository = RepositoryFactoryProducer.repositoryFactory()
        .getGenreRepository();
    BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
        .getBookRepository();
    AuthorRepository authorRepository = RepositoryFactoryProducer.repositoryFactory()
        .getAuthorRepository();

    String returnPage = Path.MAIN_MENU;
    try {
      request.setCharacterEncoding(ConsEncoding.UTF_8);

      Long bookId = Long.parseLong(request.getParameter("id"));
      String bookTitle = request.getParameter("title");
      String bookPublishingHouse = request.getParameter("bookPublishingHouse");
      Author author = authorRepository.getById(Long.parseLong(request.getParameter("authorId")));
      Genre genre = genreRepository.getById(Long.parseLong(request.getParameter("genreId")));
      Date dateOfPublication = Date.valueOf(request.getParameter("dateOfPublication"));
      Long countInStock = Long.parseLong(request.getParameter("countInStock"));

      Part filePart = request.getPart("file");
      InputStream fileContent = filePart.getInputStream();
      if (countInStock < 0) {
        log.error(Messages.NEGATIVE_COUNT_IN_STOCK);
        returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.NEGATIVE_COUNT_IN_STOCK;
      } else {
        Book bookToUpdate = bookRepository.getById(bookId);
        bookToUpdate.setTitle(bookTitle);
        bookToUpdate.setAuthor(author);
        bookToUpdate.setGenre(genre);
        bookToUpdate.setPublishingHouse(bookPublishingHouse);
        bookToUpdate.setDateOfPublication(dateOfPublication);
        bookToUpdate.setCountInStock(countInStock);
        bookToUpdate.setImage(IOUtils.toByteArray(fileContent));

        bookRepository.update(bookId, bookToUpdate);
      }

      String booksAttr = "books";
      List<Book> books = bookRepository.getAll();
      request.getServletContext().setAttribute(booksAttr, books);
    } catch (IOException e) {
      log.info(Messages.ERROR, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR;
    } catch (NumberFormatException e) {
      log.error(Messages.NUMBER_FORMAT_EX, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.NUMBER_FORMAT_EX;
    } catch (DaoException | ServletException | SQLException e) {
      log.error(Messages.ERROR_UPDATE_BOOK, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_UPDATE_BOOK;
    }
    return returnPage;
  }
}
