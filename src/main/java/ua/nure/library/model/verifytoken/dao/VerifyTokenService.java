package ua.nure.library.model.verifytoken.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.verifytoken.entity.VerifyToken;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.util.transaction.Transactional;

/**
 * @author Artem Kudria
 */
@Log4j
public class VerifyTokenService implements VerifyTokenRepository {

  private Connection connection;

  public VerifyTokenService(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void create(VerifyToken object) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(VerifyTokenQueries.INSERT)) {
      createOrUpdateVerifyToken(preparedStatement, object, null);
    } catch (SQLException e) {
      String errorMessage = "Error create or update VerifyToken!";
      log.error(errorMessage + e.getMessage());
      throw new DaoException(errorMessage);
    }
  }

  @Override
  public void update(Long id, VerifyToken object) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(VerifyTokenQueries.UPDATE)) {
      createOrUpdateVerifyToken(preparedStatement, object, id);
    } catch (SQLException e) {
      String errorMessage = "Error create or update VerifyToken!";
      log.error(errorMessage + e.getMessage());
      throw new DaoException(errorMessage);
    }
  }

  private void createOrUpdateVerifyToken(PreparedStatement preparedStatement, VerifyToken object,
      Long id) throws SQLException {
    preparedStatement.setString(1, object.getToken());
    preparedStatement.setString(2, object.getUser().getLogin());
    preparedStatement.setObject(3, object.getExpiryDate());

    Optional<Long> idObject = Optional.ofNullable(id);
    if (idObject.isPresent()) {
      preparedStatement.setLong(4, id);
    }
    preparedStatement.executeUpdate();
  }

  @Override
  public void delete(VerifyToken object) throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(VerifyTokenQueries.DELETE)) {
      preparedStatement.setLong(1, object.getId());
      if (preparedStatement.executeUpdate() > 0) {
        log.debug("verify token success delete!");
      }
    } catch (SQLException e) {
      String errorMessage = "Error delete verify token";
      log.error(errorMessage + e.getMessage());
      throw new DaoException(errorMessage);
    }
  }

  @Override
  public VerifyToken getById(Long id) throws DaoException {
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(VerifyTokenQueries.GET_BY_ID)) {
      preparedStatement.setLong(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return configureVerifyTokenFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      String error = "Error verify token from db!";
      log.error(error + e.getMessage());
      throw new DaoException(error);
    }
  }

  @Override
  public VerifyToken getByToken(String token) throws DaoException {
    try (PreparedStatement preparedStatement =
        connection.prepareStatement(VerifyTokenQueries.GET_BY_TOKEN)) {
      preparedStatement.setString(1, token);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return configureVerifyTokenFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      String error = "Error verify token from db!";
      log.error(error + e.getMessage());
      throw new DaoException(error);
    }
  }

  @Transactional
  @Override
  public List<VerifyToken> getAllVerifyToken() throws DaoException {
    try (PreparedStatement preparedStatement = connection
        .prepareStatement(VerifyTokenQueries.GET_ALL)) {
      List<VerifyToken> verifyTokens = new ArrayList<>();
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          VerifyToken verifyToken = new VerifyToken();
          configureVerifyToken(verifyToken, resultSet);
          verifyTokens.add(verifyToken);
        }
        return verifyTokens;
      }
    } catch (SQLException e) {
      String error = "Error get all tokens from db!";
      log.error(error + e.getMessage());
      throw new DaoException(error);
    }
  }

  private VerifyToken configureVerifyTokenFromResultSet(ResultSet resultSet)
      throws SQLException, DaoException {
    VerifyToken verifyToken = new VerifyToken();
    while (resultSet.next()) {
      configureVerifyToken(verifyToken, resultSet);
    }
    return verifyToken;
  }

  private void configureVerifyToken(VerifyToken verifyToken, ResultSet resultSet)
      throws SQLException, DaoException {
    verifyToken.setId(resultSet.getLong(VerifyTokenQueries.ID));
    verifyToken.setToken(resultSet.getString(VerifyTokenQueries.TOKEN));
    verifyToken.setExpiryDate(resultSet.getObject(VerifyTokenQueries.EXPIRY_DATE, LocalDate.class));

    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();
    verifyToken.setUser(readerRepository.getByLogin(resultSet.getString(VerifyTokenQueries.USER)));
  }
}
