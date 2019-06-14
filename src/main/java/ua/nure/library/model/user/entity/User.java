package ua.nure.library.model.user.entity;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ua.nure.library.model.role.entity.Role;

/**
 * @author Artem Kudria
 */
@Data
@Builder
@NoArgsConstructor
public class User implements Serializable {

  private static final long serialVersionUID = -5862271743819787545L;
  private Long id;
  @NotNull
  private String name;
  @NotNull
  private String login;
  @NotNull
  private String password;
  @NonNull
  @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\." +
      "[a-zA-Z0-9_+&*-]+)*@" +
      "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
      "A-Z]{2,7}$")
  private String email;
  @NotNull
  private boolean isActive;
  private Role role;

  public User(Long id, String name, String login, String password, @NonNull String email,
      boolean isActive, Role role) {
    this.id = id;
    this.name = name;
    this.login = login;
    this.password = password;
    this.email = email;
    this.isActive = isActive;
    this.role = role;
  }

  public User(String name, String login, String password, @NonNull String email, boolean isActive,
      Role role) {
    this.name = name;
    this.login = login;
    this.password = password;
    this.email = email;
    this.isActive = isActive;
    this.role = role;
  }

  public User(String name, String login, String password, @NonNull String email, boolean isActive) {
    this.name = name;
    this.login = login;
    this.password = password;
    this.email = email;
    this.isActive = isActive;
  }

  public User(Long id, String name, String login, String password, @NonNull String email,
      boolean isActive) {
    this.id = id;
    this.name = name;
    this.login = login;
    this.password = password;
    this.email = email;
    this.isActive = isActive;
  }
}
