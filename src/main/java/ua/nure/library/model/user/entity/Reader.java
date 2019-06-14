package ua.nure.library.model.user.entity;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.role.entity.Role;

/**
 * @author Artem Kudria
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Reader extends User implements Serializable {

  private static final long serialVersionUID = 8572859990758119961L;
  private List<Order> orders;

  public Reader(Long id, String name, String login, String password, @NonNull String email,
      boolean isActive, Role role, List<Order> orders) {
    super(id, name, login, password, email, isActive, role);
    this.orders = orders;
  }

  public Reader(String name, String login, String password, @NonNull String email, boolean isActive,
      Role role,
      List<Order> orders) {
    super(name, login, password, email, isActive, role);
    this.orders = orders;
  }

  public Reader(Long id, String name, String login, String password,
      @NonNull String email, boolean isActive) {
    super(id, name, login, password, email, isActive);
  }
}
