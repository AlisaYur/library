package ua.nure.library.model.user.exception;

/**
 * @author Artem Kudria
 */

public class UserLoginNotUniqueException extends Exception {

  public UserLoginNotUniqueException() {
    super("Login already in use");
  }
}
