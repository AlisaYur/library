package ua.nure.library.web.controller.command;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.ajax.book.validation.AjaxBookTitleData;

/**
 * @author Artem Kudria
 */
@Log4j
public class CheckBookTitleCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    try {
      Gson gson = new Gson();
      String ajaxParam = "requestData";
      String ajaxFromRequest = request.getParameter(ajaxParam);
      AjaxBookTitleData ajaxBookTitleData = gson.fromJson(ajaxFromRequest, AjaxBookTitleData.class);

      AjaxBookTitleData responseAjax = new AjaxBookTitleData();

      PrintWriter out = response.getWriter();
      response.setContentType("application/json");
      response.setCharacterEncoding(ConsEncoding.UTF_8);

      final String inputInvalid = "invalid input!";
      final String bookFoundMessage = "book found!";

      Optional<String> title = Optional.ofNullable(ajaxBookTitleData.getTitle());

      if (!title.isPresent() || title.get().equalsIgnoreCase("")) {
        responseAjax.setTitle(inputInvalid);
        out.print(responseAjax);
        out.flush();
        return "json";
      }

      Book book = getBookOnValidationByTitle(ajaxBookTitleData.getTitle());
      if (book != null && book.getTitle() != null) {
        responseAjax.setTitle(bookFoundMessage);
      }

      out.print(gson.toJson(responseAjax));
      out.flush();
    } catch (IOException e) {
      String errorMsq = "Error check book title, server return error" + e;
      log.error(errorMsq);
    } catch (JsonSyntaxException e) {
      String errorMsq = "Json syntax Error " + e;
      log.error(errorMsq);
    }
    return "json";
  }

  private Book getBookOnValidationByTitle(String bookTitle) {
    BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
        .getBookRepository();
    try {
      Optional<Book> book = Optional.ofNullable(bookRepository.getByTitle(bookTitle));

      if (book.isPresent()) {
        return book.get();
      }

    } catch (DaoException e) {
      String messageError = "Error get book from db" + e;
      log.error(messageError);
    }
    return null;
  }
}
