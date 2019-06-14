package ua.nure.library.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Custom Dispatcher for forward request in your command
 *
 * @author Artem Kudria
 */

public interface Command {

  /**
   * Check request, and transition into command handler
   *
   * @param request HttpServletRequest
   * @param response HttpServletRequest
   * @return String page or json response
   */
  String execute(HttpServletRequest request, HttpServletResponse response);
}
