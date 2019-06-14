package ua.nure.library.util.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Artem Kudria
 */
public final class ValidateFields {

  private ValidateFields() {
  }

  public static boolean validateEmail(String email) {
    String emailReqExp = "^[a-zA-Z0-9_+&*-]+(?:\\." +
        "[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
        "A-Z]{2,7}$";
    Pattern regexp = Pattern.compile(emailReqExp);
    Matcher m = regexp.matcher(email);
    return !email.equalsIgnoreCase("") && m.matches();
  }

  public static boolean validateLogin(String login) {
    String loginRegExp = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d.-]{0,19}$";
    Pattern regexp = Pattern.compile(loginRegExp);
    Matcher m = regexp.matcher(login);
    return !login.equalsIgnoreCase("") && m.matches();
  }

  public static boolean validateName(String name) {
    String nameRegExp = "^\\s*(?:(?:\\b[a-zA-Z\\u0400-\\u04FF]+\\b)\\s*){1,2}$";
    Pattern regexp = Pattern.compile(nameRegExp);
    Matcher m = regexp.matcher(name);
    return !name.equalsIgnoreCase("") && m.matches();
  }
}
