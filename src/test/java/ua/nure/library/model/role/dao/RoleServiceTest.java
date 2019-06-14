package ua.nure.library.model.role.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.role.entity.Role;
import ua.nure.library.model.role.entity.RoleName;
import ua.nure.library.util.repository.RepositoryFactoryProducer;

@RunWith(JUnit4.class)
public class RoleServiceTest {

  private static RoleRepository roleRepository =
      RepositoryFactoryProducer.repositoryFactory().getRoleTestRepository();

  @Test
  public void createNewRole_RoleExist() throws DaoException {
    Role findRole = roleRepository.getByName(RoleName.ADMIN_ROLE);
    assertNotNull(findRole);
  }

  @Test
  public void getByNameRole_RoleNotNull() throws DaoException {
    Role findRole = roleRepository.getByName(RoleName.READER_ROLE);
    assertNotNull(findRole);
  }
}
