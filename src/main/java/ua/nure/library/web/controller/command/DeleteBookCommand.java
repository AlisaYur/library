package ua.nure.library.web.controller.command;

import java.io.IOException;
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
public class DeleteBookCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
        .getBookRepository();

    String returnPage = Path.MAIN_MENU;
    try {
      request.setCharacterEncoding(ConsEncoding.UTF_8);
      String idParam = "id";
      final String bookIdParam = request.getParameter(idParam).trim();
      final Long bookId = Long.parseLong(bookIdParam);
      final Book bookToDelete = bookRepository.getById(bookId);
      bookRepository.delete(bookToDelete);

    } catch (IOException e) {
      log.error(Messages.ERROR, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR;
    } catch (DaoException e) {
      log.error(Messages.ERROR_DELETE_BOOK, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_DELETE_BOOK;
    } catch (NumberFormatException e) {
      log.error(Messages.NUMBER_FORMAT_EX, e);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.NUMBER_FORMAT_EX;
    }
    return returnPage;
  }
}
