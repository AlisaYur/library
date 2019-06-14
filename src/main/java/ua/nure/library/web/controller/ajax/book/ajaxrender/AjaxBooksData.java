package ua.nure.library.web.controller.ajax.book.ajaxrender;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.library.model.book.entity.Book;

/**
 * @author Artem Kudria
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AjaxBooksData implements Serializable {

  private static final long serialVersionUID = -4040874165786650886L;
  private List<Book> books;
  private String role;
  private boolean readerHavePenalty;
}
