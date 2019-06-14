package ua.nure.library.model.book.dao.book;

import java.sql.SQLException;
import java.util.List;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.util.repository.Repository;

/**
 * @author Artem Kudria
 */
public interface BookRepository extends Repository<Book> {

  /**
   * Save new Book in database
   *
   * @param book object to save
   * @throws DaoException if unable to save Book
   */
  @Override
  void create(Book book) throws DaoException;

  /**
   * Update Book in database
   *
   * @param id of Book to update
   * @param book with new values
   * @throws DaoException if unable to update Book
   */
  @Override
  void update(Long id, Book book) throws DaoException;

  /**
   * Delete Book from database
   *
   * @param book to delete
   * @throws DaoException if unable to delete Book
   */
  @Override
  void delete(Book book) throws DaoException;

  /**
   * Get Book by id from database
   *
   * @param id of Book to get
   * @throws DaoException if unable to select Book
   */
  @Override
  Book getById(Long id) throws DaoException;

  /**
   * Get all Books from database
   *
   * @return List of Books
   * @throws SQLException if have database errors
   * @throws DaoException if unable to select Books
   */
  List<Book> getAll() throws SQLException, DaoException;

  /**
   * Get Book by book name
   *
   * @param bookName to select Book
   * @return Book object
   * @throws DaoException if unable to select Book
   */
  Book getByTitle(final String bookName) throws DaoException;

  /**
   * Get Books by book Author
   *
   * @param authorLastName to select Book
   * @return List of Books
   * @throws DaoException if unable to select Book
   */
  List<Book> getByAuthor(final String authorLastName) throws DaoException;

  /**
   * Get Books by book genre
   *
   * @param genreName to select
   * @return List of Books
   * @throws DaoException if unable to select Book
   */
  List<Book> getByGenre(final String genreName) throws DaoException;

  /**
   * Get Books by book Publishing Houser
   *
   * @param publishingHouseName to select Book
   * @return List of Books
   * @throws DaoException if unable to select Book
   */
  List<Book> getByPublishingHouse(final String publishingHouseName) throws DaoException;

  /**
   * Sort books by paramether
   *
   * @param parameter to sort
   * @param books List Books from front
   * @return List of sorted Books
   */
  List<Book> sortBooksByType(final String parameter, List<Book> books);

  /**
   * Search book by title name or author first name or last name
   *
   * @param search String
   * @return List books
   */
  List<Book> searchBookByTitleOrAuthor(String search) throws DaoException;

  /**
   * Get books by filter values
   *
   * @param genres Genre list
   * @param authors Author list
   * @return List books
   */
  List<Book> getBooksByFilter(List<String> genres, List<String> authors) throws DaoException;
}
