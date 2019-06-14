package ua.nure.library.model.book.entity;

import java.io.Serializable;
import java.sql.Date;
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
public class Book implements Serializable {

  private static final long serialVersionUID = -530568080618750027L;

  private Long id;
  @NotNull
  private String title;
  @NotNull
  private Author author;
  @NotNull
  private Genre genre;
  private String publishingHouse;
  @NotNull
  private Date dateOfPublication;
  private Long countInStock;
  private byte[] image;
  private String encodedImage;
}
