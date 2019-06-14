package ua.nure.library.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.sendgrid.EmailSender;

/**
 * @author Artem Kudria
 */
@Log4j
public class SendEmailCommand implements Command {


  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String returnPage = Path.MANAGER_CABINET;
    String email = request.getParameter("email");
    String title = request.getParameter("title");
    String message = request.getParameter("message");

    if (email.equalsIgnoreCase(" ")
        || title.equalsIgnoreCase(" ")
        || message.equalsIgnoreCase(" ")) {
      return Path.MANAGER_CABINET + Messages.EMPTY_INPUT;

    }

    EmailSender emailSender = new EmailSender();
    emailSender.send(email, title, message);

    return returnPage;
  }
}
