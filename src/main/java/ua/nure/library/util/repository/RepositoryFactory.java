package ua.nure.library.util.repository;

import ua.nure.library.model.book.dao.author.AuthorRepository;
import ua.nure.library.model.book.dao.book.BookRepository;
import ua.nure.library.model.book.dao.genre.GenreRepository;
import ua.nure.library.model.order.dao.OrderRepository;
import ua.nure.library.model.role.dao.RoleRepository;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.dao.user.UserRepository;
import ua.nure.library.model.verifytoken.dao.VerifyTokenRepository;

/**
 * Factory repository for creating dao service
 *
 * @author Artem Kudria
 */
public interface RepositoryFactory {

  ReaderRepository getReaderRepository();

  ReaderRepository getReaderTestRepository();

  AuthorRepository getAuthorRepository();

  AuthorRepository getAuthorTestRepository();

  GenreRepository getGenreRepository();

  GenreRepository getGenreTestRepository();

  BookRepository getBookRepository();

  BookRepository getBookTestRepository();

  RoleRepository getRoleRepository();

  RoleRepository getRoleTestRepository();

  OrderRepository getOrderRepository();

  OrderRepository getOrderTestRepository();

  UserRepository getUserRepository();

  UserRepository getUserTestRepository();

  VerifyTokenRepository getVerifyTokenRepository();

  VerifyTokenRepository getVerifyTokenTestRepository();
}
