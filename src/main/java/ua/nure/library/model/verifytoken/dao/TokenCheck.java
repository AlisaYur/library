package ua.nure.library.model.verifytoken.dao;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.verifytoken.entity.VerifyToken;
import ua.nure.library.util.repository.RepositoryFactoryProducer;

/**
 * @author Artem Kudria
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenCheck {

  public static void checkAllTokens(List<VerifyToken> verifyTokenList) throws DaoException {
    VerifyTokenRepository verifyTokenRepository =
        RepositoryFactoryProducer.repositoryFactory().getVerifyTokenRepository();
    ReaderRepository readerRepository =
        RepositoryFactoryProducer.repositoryFactory().getReaderRepository();

    LocalDate currentDate = LocalDate.now();
    for (VerifyToken verifyToken : verifyTokenList) {
      if (currentDate.isAfter(verifyToken.getExpiryDate())) {
        long daysBetween = Math.abs(DAYS.between(currentDate, verifyToken.getExpiryDate()));
        if (daysBetween >= 1) {
          readerRepository.delete(verifyToken.getUser());
          verifyTokenRepository.delete(verifyToken);
        }
      }
    }
  }
}
