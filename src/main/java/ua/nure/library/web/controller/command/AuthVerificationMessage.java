package ua.nure.library.web.controller.command;

/**
 * @author Artem Kudria
 */
final class AuthVerificationMessage {

  private AuthVerificationMessage() {
  }

  static String generateValidateMessage(String token) {
    return "<form method=\"POST\" action=\"http://localhost:8080/mainMenu?action=VerifyToken\">" +
        "<input type=\"hidden\" value=\"" + token + "\" name=\"token\" hidden/>" +
        "<input type=\"submit\" value=\"Confirm\" name=\"token_button\"/></form>";
  }
}
