package ua.nure.library.model.book.entity;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Artem Kudria
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author implements Serializable {

  private static final long serialVersionUID = 2378446366668101601L;

  private Long id;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  private List<Book> books;

  public Author(final String firstName, final String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
