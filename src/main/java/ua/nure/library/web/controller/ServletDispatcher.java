package ua.nure.library.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.web.controller.command.Command;
import ua.nure.library.web.controller.command.MainCommand;

/**
 * @author Artem Kudria
 */
@WebServlet(name = "ServletDispatcher", urlPatterns = "/mainMenu")
@MultipartConfig
@Log4j
public class ServletDispatcher extends HttpServlet {

  @Override
  protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
    try {
      response.setCharacterEncoding(ConsEncoding.UTF_8);
      processRequest(request, response);
    } catch (ServletException e) {
      log.error("[GET] Error");
    } catch (IOException e) {
      log.error("[GET] IO Exception");
    }
  }

  @Override
  protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
    try {
      response.setCharacterEncoding(ConsEncoding.UTF_8);
      processRequest(request, response);
    } catch (ServletException e) {
      log.error("[POST] Error");
    } catch (IOException e) {
      log.error("[POST] IO Exception");
    }
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Command command = getCommand(request);
    String page = command.execute(request, response);
    checkPageContains(request, response, page);
  }

  private void checkPageContains(HttpServletRequest request, HttpServletResponse response,
      String page) throws ServletException, IOException {
    if (page.contains(".jsp")) {
      request.getRequestDispatcher(page).forward(request, response);
    } else if (page.contains("json")) {
      request.setCharacterEncoding("UTF-8");
    } else {
      response.sendRedirect(page);
    }
  }

  private Command getCommand(HttpServletRequest request) {
    String action = request.getParameter("action");
    try {
      if (action != null) {
        Class type = Class.forName(String.format("ua.nure.library.web.controller.command.%sCommand",
            action));
        return (Command) type.asSubclass(Command.class).newInstance();
      }
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      log.error("Unknown command action: " + action);
    }
    return new MainCommand();
  }
}
