package ua.nure.library.web.listener.verifttoken;

import java.util.List;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.verifytoken.dao.TokenCheck;
import ua.nure.library.model.verifytoken.dao.VerifyTokenRepository;
import ua.nure.library.model.verifytoken.entity.VerifyToken;
import ua.nure.library.util.repository.RepositoryFactoryProducer;

/**
 * @author Artem Kudria
 */
@Log4j
public class CheckAllVerifyTokensOnExpiry implements Runnable {

  @Override
  public void run() {
    VerifyTokenRepository verifyTokenRepository =
        RepositoryFactoryProducer.repositoryFactory().getVerifyTokenRepository();
    try {
      List<VerifyToken> verifyTokens = verifyTokenRepository.getAllVerifyToken();
      TokenCheck.checkAllTokens(verifyTokens);
    } catch (DaoException e) {
      log.error(e.getMessage());
    }
  }
}
