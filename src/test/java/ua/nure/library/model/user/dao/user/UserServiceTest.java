package ua.nure.library.model.user.dao.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.role.entity.Role;
import ua.nure.library.model.role.entity.RoleName;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.repository.RepositoryFactoryProducer;

@RunWith(JUnit4.class)
public class UserServiceTest {

  private static UserRepository userRepository =
      RepositoryFactoryProducer.repositoryFactory().getUserTestRepository();

  @Test
  public void create() throws DaoException {
    final String userLogin = "test";

    User user = User.builder()
        .name("Test")
        .login(userLogin)
        .isActive(true)
        .password("pass")
        .email("lol@mail.ru")
        .role(Role.builder().id(1L).name(RoleName.ADMIN_ROLE).build())
        .build();

    userRepository.create(user);
    User foundUser = userRepository.getByLogin(userLogin);
    assertEquals(foundUser.getLogin(), user.getLogin());
  }

  @Test
  public void update() throws DaoException {
    final String userLogin = "update";
    final String newLogin = "newLogin";

    User user = User.builder()
        .name("Test")
        .login(userLogin)
        .isActive(true)
        .password("pass")
        .email("lol" + userLogin + "@mail.ru")
        .role(Role.builder().id(1L).name(RoleName.ADMIN_ROLE).build())
        .build();

    userRepository.create(user);
    User foundUser = userRepository.getByLogin(userLogin);
    foundUser.setLogin(newLogin);
    userRepository.update(foundUser.getId(), foundUser);
    User foundUserAfterUpdate = userRepository.getByLogin(newLogin);
    assertEquals(newLogin, foundUserAfterUpdate.getLogin());
  }

  @Test
  public void delete() throws DaoException {
    final String userLogin = "delete";

    User user = User.builder()
        .name("Test")
        .login(userLogin)
        .isActive(true)
        .password("pass")
        .email("lol" + userLogin + "@mail.ru")
        .role(Role.builder().id(1L).name(RoleName.ADMIN_ROLE).build())
        .build();

    userRepository.create(user);
    User foundUser = userRepository.getByLogin(userLogin);
    userRepository.delete(foundUser);
    User foundUserAfterDelete = userRepository.getByLogin(userLogin);
    assertNull(foundUserAfterDelete);
  }

  @Test
  public void getByLogin() throws DaoException {
    final String userLogin = "getByLogin";

    User user = User.builder()
        .name("Test")
        .login(userLogin)
        .isActive(true)
        .password("pass")
        .email("lol" + userLogin + "@mail.ru")
        .role(Role.builder().id(1L).name(RoleName.ADMIN_ROLE).build())
        .build();

    userRepository.create(user);
    User foundUser = userRepository.getByLogin(userLogin);
    assertEquals(foundUser.getLogin(), user.getLogin());
  }

  @Test
  public void getByName() throws DaoException {
    final String userLogin = "getByNameUserUniqueLogin";

    User user = User.builder()
        .name(userLogin)
        .login(userLogin)
        .isActive(true)
        .password("pass")
        .email("lol2" + userLogin + "@mail.ru")
        .role(Role.builder().id(1L).name(RoleName.ADMIN_ROLE).build())
        .build();

    userRepository.create(user);
    User foundUser = userRepository.getByName(userLogin);
    assertEquals(foundUser.getName(), user.getName());
  }

  @Test
  public void getByEmail() throws DaoException {
    final String userLogin = "getByEmail";
    final String userEmail = "getByEmail@mail.ru";

    User user = User.builder()
        .name(userLogin)
        .login(userLogin)
        .isActive(true)
        .password("pass")
        .email(userEmail)
        .role(Role.builder().id(1L).name(RoleName.ADMIN_ROLE).build())
        .build();

    userRepository.create(user);
    User foundUser = userRepository.getByEmail(userEmail);
    assertEquals(foundUser.getEmail(), user.getEmail());
  }

  @Test
  public void getAllManagers() throws DaoException {
    final String userLogin = "getByName";

    User user = User.builder()
        .name(userLogin)
        .login(userLogin)
        .isActive(true)
        .password("pass")
        .email("lol" + userLogin + "@mail.ru")
        .role(Role.builder().id(1L).name(RoleName.LIBRARIAN_ROLE).build())
        .build();

    userRepository.create(user);
    assertFalse(userRepository.getAllManagers().isEmpty());
  }
}
