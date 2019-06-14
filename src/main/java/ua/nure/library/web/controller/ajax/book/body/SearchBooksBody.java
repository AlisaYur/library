package ua.nure.library.web.controller.ajax.book.body;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.library.model.book.entity.Book;

/**
 * @author Artem Kudria
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBooksBody implements Serializable {

  private static final long serialVersionUID = 5869801601548247535L;

  private String search;
  private List<Book> bookList;
}
