package ua.nure.library.util.sendgrid;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

/**
 * @author Artem Kudria
 */
@AllArgsConstructor
@Log4j
public class EmailSender {

  private static final String FROM_EMAIL = "drcsystemsoftware@gmail.com";

  public void sendHTML(final String to, final String message, final String title) {
    Content content = new Content("text/html", message);
    sendEmail(to, title, content);
  }

  public void send(final String to, final String message, final String title) {
    Content content = new Content("text/plain", message);
    sendEmail(to, title, content);
  }

  private void sendEmail(final String to, final String title, Content content) {
    Email from = new Email(FROM_EMAIL);
    Email toEmail = new Email(to);
    Mail mailSend = new Mail(from, title, toEmail, content);
    Request request = new Request();

    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mailSend.build());
      SendGrid sendGrid = new SendGrid(ApiKeyConfig.API_KEY);
      sendGrid.api(request);
    } catch (IOException ex) {
      log.error("Error send! " + ex.getMessage());
    }
  }
}
