package ua.nure.library.model.role.entity;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.nure.library.model.user.entity.User;

/**
 * @author Artem Kudria
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "userSet")
@Builder
public class Role implements Serializable {

  private static final long serialVersionUID = -68706543210846100L;

  private Long id;
  @NotNull
  private RoleName name;
  private Set<User> userSet;
}
