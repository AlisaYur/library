package ua.nure.library.model.user.dao.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.order.entity.OrderStatus;
import ua.nure.library.model.role.entity.Role;
import ua.nure.library.model.role.entity.RoleName;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.repository.RepositoryFactoryProducer;

@RunWith(JUnit4.class)
public class ReaderServiceTest {

  private static final ReaderRepository readerRepository =
      RepositoryFactoryProducer.repositoryFactory().getReaderTestRepository();

  @Test
  public void create() throws DaoException {
    final String userLogin = "testReader";

    Reader reader =
        new Reader("test", userLogin, "test",
            "test@gmail.ru", true, Role.builder().id(2L).name(RoleName.ADMIN_ROLE).build(),
            new ArrayList<>());

    readerRepository.create(reader);
    User foundUser = readerRepository.getByLogin(userLogin);
    assertEquals(foundUser.getLogin(), reader.getLogin());
  }

  @Test
  public void update() throws DaoException {
    final String userLogin = "testUpdateReader";
    final String newUserLogin = "testNewReader";
    Reader reader =
        new Reader("test", userLogin, "test",
            "testUpdate@gmail.ru", true, Role.builder().id(2L).name(RoleName.ADMIN_ROLE).build(),
            new ArrayList<>());

    readerRepository.create(reader);
    Reader findReader = readerRepository.getByLogin(userLogin);
    findReader.setLogin(newUserLogin);
    readerRepository.update(findReader.getId(), findReader);
    Reader findReaderAfterUpdate = readerRepository.getByLogin(newUserLogin);
    assertEquals(newUserLogin, findReaderAfterUpdate.getLogin());
  }

  @Test
  public void delete() throws DaoException {
    final String userLogin = "testDeleteReader";
    Reader reader =
        new Reader("test", userLogin, "test",
            "testDelete@gmail.ru", true, Role.builder().id(2L).name(RoleName.ADMIN_ROLE).build(),
            new ArrayList<>());

    readerRepository.create(reader);
    Reader findReader = readerRepository.getByLogin(userLogin);
    readerRepository.delete(findReader);
    assertNull(readerRepository.getByLogin(userLogin));
  }

  @Test
  public void getByLogin() throws DaoException {
    final String userLogin = "getByIdReader1";
    Reader reader =
        new Reader("test", userLogin, "test",
            "test" + userLogin + "@gmail.ru", true,
            Role.builder().id(2L).name(RoleName.ADMIN_ROLE).build(),
            new ArrayList<>());
    readerRepository.create(reader);
    assertNotNull(readerRepository.getByLogin(userLogin));
  }

  @Test
  public void getByName() throws DaoException {
    final String userName = "getByNameReader2";
    Reader reader =
        new Reader(userName, userName, "test",
            "test" + userName + "@gmail.ru", true,
            Role.builder().id(2L).name(RoleName.ADMIN_ROLE).build(),
            new ArrayList<>());
    readerRepository.create(reader);
    assertNotNull(readerRepository.getByName(userName));
  }

  @Test
  public void getAll() throws DaoException {
    assertFalse(readerRepository.getAll().isEmpty());
  }

  @Test
  public void getByEmail() throws DaoException {
    final String userEmail = "getByEmailReader3@gmail.com";
    Reader reader =
        new Reader("dafs", "dsafds", "test",
            userEmail, true, Role.builder().id(2L).name(RoleName.ADMIN_ROLE).build(),
            new ArrayList<>());
    readerRepository.create(reader);
    assertNotNull(readerRepository.getByEmail(userEmail));
  }

  @Test
  public void searchReadersByNameLoginOrEmail() throws DaoException {
    final String userLogin = "searchReadersByNameLoginOrEmail";
    Reader reader =
        new Reader("test", userLogin, "test",
            "test" + userLogin + "@gmail.ru", true,
            Role.builder().id(2L).name(RoleName.ADMIN_ROLE).build(),
            new ArrayList<>());
    readerRepository.create(reader);
    assertNotNull(readerRepository.searchReadersByNameLoginOrEmail(userLogin));
  }

  @Test
  public void getAllOrdersReader() throws DaoException {
    String userLogin = "getAllOrdersReader";
    ReaderService readerServiceTest = mock(ReaderService.class);
    when(readerServiceTest.getAllOrdersReader(userLogin))
        .thenReturn(new ArrayList<>(Collections.singletonList(
            Order.builder().bookId(Book.builder().build())
                .dateOfDelivery(LocalDate.now()).penalty(0L).startDate(LocalDate.now())
                .status(OrderStatus.APPROVED).build())));
    assertFalse(readerServiceTest.getAllOrdersReader(userLogin).isEmpty());
  }

  @Test
  public void getReaderOrderByBookId() throws DaoException {
    Long bookId = 1L;
    Reader reader = mock(Reader.class);
    ReaderService readerServiceTest = mock(ReaderService.class);
    when(readerServiceTest.getReaderOrderByBookId(bookId, reader))
        .thenReturn(Order.builder().bookId(Book.builder().build())
            .dateOfDelivery(LocalDate.now()).penalty(0L).startDate(LocalDate.now())
            .status(OrderStatus.APPROVED).build());
    assertNotNull(readerServiceTest.getReaderOrderByBookId(bookId, reader));
  }

  @Test
  public void searchOrderReader() throws DaoException {
    final String userLogin = "searchOrderReader";
    Reader reader =
        new Reader("test", userLogin, "test",
            "test" + userLogin + "@gmail.ru", true,
            Role.builder().id(2L).name(RoleName.ADMIN_ROLE).build(),
            new ArrayList<>());
    readerRepository.create(reader);
    List<Order> orders = readerRepository.searchOrderReader("T", reader);
    assertTrue(orders.isEmpty());
  }
}
