package ua.nure.library.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j;
import ua.nure.library.util.Path;

/**
 * @author Artem Kudria
 */
@Log4j
public class LogOutCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    HttpSession session = request.getSession();
    session.invalidate();
    return Path.MAIN_MENU;
  }
}
