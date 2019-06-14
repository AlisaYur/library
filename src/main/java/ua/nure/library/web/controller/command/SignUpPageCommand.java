package ua.nure.library.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.nure.library.util.PagesPath;

/**
 * @author Artem Kudria
 */

public class SignUpPageCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    return PagesPath.SIGN_UP;
  }
}
