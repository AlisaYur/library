package ua.nure.library.web.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.util.Messages;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class ChangeReaderStateCommand implements Command {


  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();

    String returnPage = "json";
    try {
      Long readerId = Long.parseLong(request.getParameter("id"));
      boolean isReaderActive = Boolean.parseBoolean(request.getParameter("active"));

      Reader readerToUpdate;
      readerToUpdate = readerRepository.getById(readerId);
      readerToUpdate.setActive(isReaderActive);
      readerRepository.update(readerId, readerToUpdate);
    } catch (NumberFormatException e) {
      log.error(Messages.UNABLE_PARSE_READER, e);
    } catch (DaoException e) {
      log.error(Messages.ERROR_DELETE_READER);
      returnPage = ErrorRedirect.ERROR_READERS_URL + Messages.ERROR_DELETE_READER;
    }
    return returnPage;
  }
}
