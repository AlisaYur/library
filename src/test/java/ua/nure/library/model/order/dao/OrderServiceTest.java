package ua.nure.library.model.order.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.order.entity.OrderStatus;
import ua.nure.library.model.order.entity.TypeIssue;
import ua.nure.library.model.role.entity.Role;
import ua.nure.library.model.role.entity.RoleName;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.repository.RepositoryFactoryProducer;

@RunWith(JUnit4.class)
public class OrderServiceTest {

  private static OrderRepository orderRepository =
      RepositoryFactoryProducer.repositoryFactory().getOrderTestRepository();

  @Test
  public void create() throws DaoException {
    String newPayId = UUID
        .randomUUID().toString();
    BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
        .getBookTestRepository();

    String userLogin = "TestUSERBOOk";
    User user = User.builder()
        .name("Test")
        .login(userLogin)
        .isActive(true)
        .password("pass")
        .email("lol" + userLogin + "@mail.ru")
        .role(Role.builder().id(1L).name(RoleName.ADMIN_ROLE).build())
        .build();

    Order order =
        Order.builder().status(OrderStatus.RETURNS)
            .userLogin(user)
            .typeIssue(TypeIssue.READING_ROOM)
            .bookId(bookRepository.getByTitle("Thinking In Java"))
            .dateOfDelivery(LocalDate.now())
            .startDate(LocalDate.now())
            .penalty(0L).payId(newPayId)
            .build();
    orderRepository.create(order);
    Order findOrder = orderRepository.getOrderByPayId(newPayId);
    assertNotNull(findOrder);
  }

  @Test
  public void getAll() throws DaoException {
    assertFalse(orderRepository.getAll().isEmpty());
  }
}
