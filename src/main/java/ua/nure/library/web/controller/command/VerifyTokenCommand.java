package ua.nure.library.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.model.verifytoken.dao.TokenCheck;
import ua.nure.library.model.verifytoken.dao.VerifyTokenRepository;
import ua.nure.library.model.verifytoken.entity.VerifyToken;
import ua.nure.library.util.Messages;
import ua.nure.library.util.PagesPath;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class VerifyTokenCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String returnPage = PagesPath.SIGN_IN;
    try {
      String token = request.getParameter("token");
      VerifyTokenRepository verifyTokenRepository =
          RepositoryFactoryProducer.repositoryFactory().getVerifyTokenRepository();
      TokenCheck.checkAllTokens(
          verifyTokenRepository.getAllVerifyToken()); //Check all tokens before validation
      VerifyToken verifyToken = verifyTokenRepository.getByToken(token);
      if (null != verifyToken.getUser()) {
        changeReaderStatus(verifyToken);
        verifyTokenRepository.delete(verifyToken);
      }
      return returnPage;
    } catch (DaoException e) {
      log.error(Messages.ERROR_VALIDATE_TOKEN + e);
      return ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_VALIDATE_TOKEN;
    }
  }

  private void changeReaderStatus(VerifyToken verifyToken) throws DaoException {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();
    Reader reader = readerRepository.getByLogin(verifyToken.getUser().getLogin());
    reader.setActive(true);
    readerRepository.update(reader.getId(), reader);
  }
}
