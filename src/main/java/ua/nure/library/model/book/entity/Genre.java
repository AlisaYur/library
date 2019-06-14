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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genre implements Serializable {

  private static final long serialVersionUID = 8105358524771694401L;

  private Long id;
  @NotNull
  private String name;
  private List<Book> books;
}
