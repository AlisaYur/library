package ua.nure.library.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.util.PagesPath;

/**
 * @author Artem Kudria
 */
@Log4j
public class AuthPageCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    return PagesPath.SIGN_IN;
  }
}
