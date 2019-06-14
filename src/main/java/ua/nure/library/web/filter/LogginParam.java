package ua.nure.library.web.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Artem Kudria
 */
@Data
@AllArgsConstructor
final class LogginParam {

  private boolean loggedIn;
  private boolean isGuard;
  private boolean access;
  private boolean loginRequest;
  private boolean singUpRequest;
}
