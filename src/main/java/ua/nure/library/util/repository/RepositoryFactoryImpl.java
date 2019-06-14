package ua.nure.library.util.repository;

import java.lang.reflect.Proxy;
import ua.nure.library.model.book.dao.author.AuthorRepository;
import ua.nure.library.model.book.dao.author.AuthorService;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.dao.book.BookService;
import ua.nure.library.model.book.dao.genre.GenreRepository;
import ua.nure.library.model.book.dao.genre.GenreService;
import ua.nure.library.model.order.dao.OrderRepository;
import ua.nure.library.model.order.dao.OrderService;
import ua.nure.library.model.role.dao.RoleRepository;
import ua.nure.library.model.role.dao.RoleService;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.dao.reader.ReaderService;
import ua.nure.library.model.user.dao.user.UserRepository;
import ua.nure.library.model.user.dao.user.UserService;
import ua.nure.library.model.verifytoken.dao.VerifyTokenRepository;
import ua.nure.library.model.verifytoken.dao.VerifyTokenService;
import ua.nure.library.util.connection.DBWorker;
import ua.nure.library.util.connection.TestDbWorker;
import ua.nure.library.util.transaction.TransactionHandler;

/**
 * @author Artem Kudria
 */
public class RepositoryFactoryImpl implements RepositoryFactory {

  @Override
  public ReaderRepository getReaderRepository() {
    return (ReaderRepository) getProxy(ReaderRepository.class,
        new ReaderService(DBWorker.getDbWorker().getConnection()));
  }

  @Override
  public ReaderRepository getReaderTestRepository() {
    return new ReaderService(TestDbWorker.getH2DbWorker().getConnection());
  }

  @Override
  public AuthorRepository getAuthorRepository() {
    return (AuthorRepository) getProxy(AuthorRepository.class,
        new AuthorService(DBWorker.getDbWorker().getConnection()));
  }

  @Override
  public AuthorRepository getAuthorTestRepository() {
    return new AuthorService(TestDbWorker.getH2DbWorker().getConnection());
  }

  @Override
  public GenreRepository getGenreRepository() {
    return (GenreRepository) getProxy(GenreRepository.class,
        new GenreService(DBWorker.getDbWorker().getConnection()));
  }

  @Override
  public GenreRepository getGenreTestRepository() {
    return new GenreService(TestDbWorker.getH2DbWorker().getConnection());
  }

  @Override
  public BookRepository getBookRepository() {
    return (BookRepository) getProxy(BookRepository.class,
        new BookService(DBWorker.getDbWorker().getConnection()));
  }

  @Override
  public BookRepository getBookTestRepository() {
    return new BookService(TestDbWorker.getH2DbWorker().getConnection());
  }

  @Override
  public RoleRepository getRoleRepository() {
    return (RoleRepository) getProxy(RoleRepository.class,
        new RoleService(DBWorker.getDbWorker().getConnection()));
  }

  @Override
  public RoleRepository getRoleTestRepository() {
    return new RoleService(TestDbWorker.getH2DbWorker().getConnection());
  }

  @Override
  public OrderRepository getOrderRepository() {
    return (OrderRepository) getProxy(OrderRepository.class,
        new OrderService(DBWorker.getDbWorker().getConnection()));
  }

  @Override
  public OrderRepository getOrderTestRepository() {
    return new OrderService(TestDbWorker.getH2DbWorker().getConnection());
  }

  @Override
  public UserRepository getUserRepository() {
    return (UserRepository) getProxy(UserRepository.class,
        new UserService(DBWorker.getDbWorker().getConnection()));
  }

  @Override
  public UserRepository getUserTestRepository() {
    return new UserService(TestDbWorker.getH2DbWorker().getConnection());
  }

  @Override
  public VerifyTokenRepository getVerifyTokenRepository() {
    return (VerifyTokenRepository) getProxy(VerifyTokenRepository.class,
        new VerifyTokenService(DBWorker.getDbWorker().getConnection()));
  }

  @Override
  public VerifyTokenRepository getVerifyTokenTestRepository() {
    return new VerifyTokenService(TestDbWorker.getH2DbWorker().getConnection());
  }

  private Object getProxy(Class clazz, Object object) {
    return Proxy.newProxyInstance(
        clazz.getClassLoader(),
        new Class[]{clazz},
        new TransactionHandler(object));
  }
}