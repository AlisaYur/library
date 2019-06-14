package ua.nure.library.web.controller.redirect;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import ua.nure.library.util.Path;

/**
 * @author Artem Kudria
 */
@Log4j
@NoArgsConstructor
public final class ErrorRedirect {

  public static final String ERROR_MAIN_MENU_URL = Path.MAIN_MENU + "?error=";
  public static final String ERROR_SIGN_UP_URL = "/mainMenu?error=";
  public static final String ERROR_AUTHORS_URL = "/mainMenu?error=";
  public static final String ERROR_GENRES_URL = "/mainMenu?error=";
  public static final String ERROR_READERS_URL = "/mainMenu?error=";
  public static final String ERROR_MANAGERS_URL = "/mainMenu?error=";

  public void sendRedirect(String url, HttpServletResponse response) {
    try {
      response.sendRedirect(url);
    } catch (IOException e) {
      log.debug("Error redirect after error into " + url + e.getMessage());
    }
  }
}
