package ua.nure.library.model.order.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.model.user.entity.User;

/**
 * @author Artem Kudria
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements Serializable {

  private static final long serialVersionUID = -68708156854546094L;
  private Long id;
  @NonNull
  private Book bookId;
  @NotNull
  private User userLogin;
  @NotNull
  private TypeIssue typeIssue;
  @NonNull
  private LocalDate startDate;
  @NonNull
  private LocalDate dateOfDelivery;
  private Long penalty;
  @NonNull
  private OrderStatus status;
  private String payId;

  public Order(@NonNull Book bookId,
      User userLogin,
      TypeIssue typeIssue,
      @NonNull LocalDate startDate,
      @NonNull LocalDate dateOfDelivery,
      @NonNull Long penalty,
      @NonNull OrderStatus status) {
    this.bookId = bookId;
    this.userLogin = userLogin;
    this.typeIssue = typeIssue;
    this.startDate = startDate;
    this.dateOfDelivery = dateOfDelivery;
    this.penalty = penalty;
    this.status = status;
  }
}
