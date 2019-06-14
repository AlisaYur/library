package ua.nure.library.web.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import ua.nure.library.util.Path;

/**
 * @author Artem Kudria
 */
final class GuardPath {

  //All
  static final Set<String> GUARDS_ALL = Collections.unmodifiableSet(new HashSet<>(
      Arrays.asList(Path.MAIN_MENU, Path.SEARCH_BOOK, Path.LOG_OUT,
          Path.ERROR_PAGE, Path.CONTROL_FILTER, Path.CHANGE_EMAIL_READER,
          Path.CHECK_READER_LOGIN_EMAIL, Path.CHECK_USER_LOGIN,
          Path.CHECK_READER_LOGIN_EMAIL, Path.CHECK_AUTHOR_LAST_NAME,
          Path.GET_USERNAME_EMAIL_USER, Path.GET_COUNT_ORDERS_READER)));

  //Reader
  static final Set<String> GUARDS_PATHS_READER = Collections.unmodifiableSet(new HashSet<>(
      Arrays.asList(Path.READER_CABINET, Path.PAY_OF_FINE,
          Path.CREATE_ORDER, Path.CHANGE_PASS_READER,
          Path.CHANGE_LOGIN_READER,
          Path.CHANGE_ORDER_STATUS, Path.HISTORY_READER_ORDERS)));

  //Admin
  static final Set<String> GUARDS_ADMIN = Collections.unmodifiableSet(new HashSet<>(
      Arrays.asList(Path.ADMIN_MANAGERS_LIST,
          Path.LIST_READERS, Path.LIST_GENRE, Path.LIST_AUTHORS,
          Path.UPDATE_GENRE, Path.DELETE_GENRE, Path.CREATE_GENRE,
          Path.DELETE_AUTHORS, Path.AUTHORS_UPDATE, Path.DELETE_BOOKS,
          Path.UPDATE_BOOK, Path.DELETE_MANAGER,
          Path.CHECK_BOOK_TITLE, Path.DELETE_BOOKS, Path.DELETE_READER, Path.AUTHORS_CREATE,
          Path.CREATE_BOOKS, Path.MANAGER_CREATE)));

  //Librarian
  static final Set<String> GUARDS_LIBRARIAN = Collections.unmodifiableSet(new HashSet<>(
      Arrays.asList(Path.MANAGER_CABINET,
          Path.DELETE_ORDER, Path.UPDATE_ORDERS, Path.CHANGE_EMAIL_MANAGER,
          Path.CHANGE_LOGIN_MANAGER, Path.CHANGE_PASS_MANAGER, Path.CHANGE_ORDER_STATUS,
          Path.HISTORY_ORDERS_CAB)));

  private GuardPath() {
  }
}
