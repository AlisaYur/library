package ua.nure.library.util;

/**
 * @author Artem Kudria
 */
public final class Path {

  //Error
  public static final String ERROR_PAGE = "/mainMenu?error";
  public static final String NOT_FOUND = "/404";

  //MainCommand Menu
  public static final String MAIN_MENU = "/mainMenu";
  public static final String MAIN_MENU_ACTION = "/mainMenu?action=";

  //Cabs
  public static final String READER_CABINET = MAIN_MENU_ACTION + "ReaderCabinet";
  public static final String MANAGER_CABINET = MAIN_MENU_ACTION + "ManagerCabinet";

  //Validation
  public static final String CHECK_READER_LOGIN_EMAIL =
      MAIN_MENU_ACTION + "CheckReaderLoginAndEmail";
  public static final String CHECK_USER_LOGIN = MAIN_MENU_ACTION + "CheckUserLogin";
  public static final String CHECK_AUTHOR_LAST_NAME = MAIN_MENU_ACTION + "CheckAuthorLastName";
  public static final String GET_USERNAME_EMAIL_USER = MAIN_MENU_ACTION + "GetUsername";
  public static final String CHECK_BOOK_TITLE = MAIN_MENU_ACTION + "CheckBookTitle";

  //LogOut and Sign In and Sign Up
  public static final String LOG_OUT = MAIN_MENU_ACTION + "LogoUt";
  public static final String SIGN_IN = MAIN_MENU_ACTION + "SignIn";
  public static final String SIGN_IN_PAGE = MAIN_MENU_ACTION + "AuthPage";
  public static final String SIGN_UP = MAIN_MENU_ACTION + "SignUp";
  public static final String CONFIRM_SIGN_UP = MAIN_MENU_ACTION + "ConfirmReg";

  //Authors
  public static final String DELETE_AUTHORS = MAIN_MENU_ACTION + "DeleteAuthor";
  public static final String LIST_AUTHORS = MAIN_MENU_ACTION + "Authors";
  public static final String AUTHORS_CREATE = MAIN_MENU_ACTION + "SaveAuthor";
  public static final String AUTHORS_UPDATE = MAIN_MENU_ACTION + "UpdateAuthor";

  //MainCommand Filter
  public static final String CONTROL_FILTER = MAIN_MENU_ACTION + "FilterBooks";

  //Books
  public static final String DELETE_BOOKS = MAIN_MENU_ACTION + "DeleteBook";
  public static final String CREATE_BOOKS = MAIN_MENU_ACTION + "SaveBook";
  public static final String SEARCH_BOOK = MAIN_MENU_ACTION + "SearchBook";
  public static final String UPDATE_BOOK = MAIN_MENU_ACTION + "UpdateBook";

  //Genre
  public static final String DELETE_GENRE = MAIN_MENU_ACTION + "DeleteGenre";
  public static final String LIST_GENRE = MAIN_MENU_ACTION + "Genres";
  public static final String CREATE_GENRE = MAIN_MENU_ACTION + "SaveGenre";
  public static final String UPDATE_GENRE = MAIN_MENU_ACTION + "UpdateGenre";

  //Manager
  public static final String CHANGE_EMAIL_MANAGER = MAIN_MENU_ACTION + "ChangeManagerEmail";
  public static final String CHANGE_LOGIN_MANAGER = MAIN_MENU_ACTION + "ChangeManagerLogin";
  public static final String CHANGE_PASS_MANAGER = MAIN_MENU_ACTION + "ChangeManagerPass";
  public static final String DELETE_MANAGER = MAIN_MENU_ACTION + "DeleteManager";
  public static final String ADMIN_MANAGERS_LIST = MAIN_MENU_ACTION + "Managers";
  public static final String MANAGER_CREATE = MAIN_MENU_ACTION + "CreateManager";
  public static final String HISTORY_ORDERS_CAB = MAIN_MENU_ACTION + "HistoryManagerOrders";

  //Order
  public static final String CHANGE_ORDER_STATUS = MAIN_MENU_ACTION + "ChangeOrderStatus";
  public static final String CREATE_ORDER = MAIN_MENU_ACTION + "CreateOrder";
  public static final String DELETE_ORDER = MAIN_MENU_ACTION + "DeleteOrder";
  public static final String LIST_ORDERS = MAIN_MENU_ACTION + "Orders";
  public static final String UPDATE_ORDERS = MAIN_MENU_ACTION + "UpdateOrder";

  //Pay
  public static final String PAY_OF_FINE = MAIN_MENU_ACTION + "PayFineReader";

  //Reader
  public static final String CHANGE_EMAIL_READER = MAIN_MENU_ACTION + "ChangeEmailReader";
  public static final String CHANGE_LOGIN_READER = MAIN_MENU_ACTION + "ChangeLoginReader";
  public static final String CHANGE_PASS_READER = MAIN_MENU_ACTION + "ChangePassReader";
  public static final String DELETE_READER = MAIN_MENU_ACTION + "DeleteReader";
  public static final String LIST_READERS = MAIN_MENU_ACTION + "Readers";
  public static final String GET_COUNT_ORDERS_READER = MAIN_MENU_ACTION + "GetCountAllOrders";
  public static final String HISTORY_READER_ORDERS = MAIN_MENU_ACTION + "HistoryReaderOrder";

  private Path() {
  }
}
