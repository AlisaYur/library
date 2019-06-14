package ua.nure.library.util.uservalidation;

import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.dao.user.UserRepository;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.repository.RepositoryFactoryProducer;

/**
 * @author Artem Kudria
 */
public final class UserValidation {

  private UserValidation() {
  }

  public static User getUserOnValidationByLogin(String login) throws DaoException {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();
    Reader reader = readerRepository.getByLogin(login);

    UserRepository userRepository = RepositoryFactoryProducer.repositoryFactory()
        .getUserRepository();
    User user = userRepository.getByLogin(login);

    if (null != reader) {
      return reader;
    } else if (null != user) {
      return user;
    } else {
      throw new DaoException("When user check validation on login, not found in db!");
    }
  }
}
