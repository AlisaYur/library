package ua.nure.library.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.nure.library.util.PagesPath;

/**
 * @author Artem Kudria
 */
public class NotFoundCommand implements Command {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    return PagesPath.NOT_FOUND;
  }
}
