package ua.nure.library.util.passwordUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import ua.nure.library.model.user.entity.User;

/**
 * @author Artem Kudria
 */
public final class PasswordEncoder {

  private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

  private PasswordEncoder() {
  }

  public static String hash(String str) throws NoSuchAlgorithmException,
      UnsupportedEncodingException {
    MessageDigest digest;
    final String encode = "UTF-8";
    StringBuilder hexString = new StringBuilder();
    digest = MessageDigest.getInstance("SHA-512");
    digest.update(str.getBytes(encode));
    for (byte d : digest.digest()) {
      hexString.append(getFirstHexDigit(d)).append(getSecondHexDigit(d));
    }
    return hexString.toString();
  }

  public static boolean matches(String inputPassword, String encodedPassword)
      throws UnsupportedEncodingException,
      NoSuchAlgorithmException {
    return PasswordEncoder.hash(inputPassword).equalsIgnoreCase(encodedPassword);
  }

  public static boolean checkOldPassword(HttpServletRequest request, String oldPassword)
      throws UnsupportedEncodingException, NoSuchAlgorithmException {
    if (null != request.getSession(false) && null != request.getSession(false)
        .getAttribute("user")) {
      User user = (User) request.getSession(false).getAttribute("user");

      return PasswordEncoder.matches(oldPassword, user.getPassword());
    }

    return false;
  }

  private static char getFirstHexDigit(byte x) {
    return HEX_DIGITS[(0xFF & x) / 16];
  }

  private static char getSecondHexDigit(byte x) {
    return HEX_DIGITS[(0xFF & x) % 16];
  }
}
