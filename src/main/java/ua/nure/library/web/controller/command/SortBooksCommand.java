package ua.nure.library.web.controller.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.model.role.entity.Role;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Messages;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.book.ajaxrender.AjaxBooksData;

/**
 * @author Artem Kudria
 */
@Log4j
public class SortBooksCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
        .getBookRepository();

    String dateFormat = "yyyy-MM-dd";
    try {
      Gson gson = new GsonBuilder()
          .setDateFormat(dateFormat)
          .create();

      String sortParam = "sort";
      String ajaxFromRequest = request.getParameter(sortParam).trim();

      String booksAttr = "books";
      List<Book> books = (List<Book>) request.getServletContext().getAttribute(booksAttr);
      final List<Book> bookList = bookRepository.sortBooksByType(ajaxFromRequest, books);
      AjaxBooksData responseAjax = new AjaxBooksData();

      PrintWriter out = response.getWriter();
      response.setContentType("application/json");
      response.setCharacterEncoding(ConsEncoding.UTF_8);

      responseAjax.setBooks(bookList);

      String rolesParam = "roles";
      String anonymousRole = "Anonymous";
      if (null != request.getSession(false) && null != request.getSession(false)
          .getAttribute(rolesParam)) {
        Role role = (Role) request.getSession(false).getAttribute(rolesParam);
        responseAjax.setRole(role.getName().name());
        Object penalty = request.getSession(false).getAttribute("readerIsPenalty");
        if (null != penalty) {
          boolean readerIsInPenalty = Boolean.parseBoolean(penalty.toString());
          responseAjax.setReaderHavePenalty(readerIsInPenalty);
        }
      } else {
        responseAjax.setRole(anonymousRole);
      }

      out.write(gson.toJson(responseAjax));
      out.flush();
    } catch (IOException e) {
      log.error(Messages.ERROR_RESPONSE_BOOKS, e);
    }
    return "json";
  }
}
