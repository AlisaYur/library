package ua.nure.library.model.book.dao.genre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.entity.Genre;
import ua.nure.library.util.repository.RepositoryFactoryProducer;

@RunWith(JUnit4.class)
public class GenreServiceTest {

  private static GenreRepository genreRepository = RepositoryFactoryProducer.repositoryFactory()
      .getGenreTestRepository();

  @Test
  public void create() throws DaoException {
    Genre genre = Genre.builder().name("Fantastics").build();
    genreRepository.create(genre);
    Genre genreFromDb = genreRepository.getByName(genre.getName());
    assertEquals(genre.getName(), genreFromDb.getName());
  }

  @Test
  public void update() throws DaoException {
    Genre genre = Genre.builder().name("Historic").build();
    genreRepository.create(genre);
    Genre genreToUpdate = genreRepository.getByName(genre.getName());
    genreToUpdate.setName("History");
    genreRepository.update(genreToUpdate.getId(), genreToUpdate);
    assertEquals(genreToUpdate.getName(), genreRepository.getById(genreToUpdate.getId()).getName());
  }

  @Test
  public void delete() throws DaoException {
    Genre genre = Genre.builder().name("ToDelete").build();
    genreRepository.create(genre);
    Genre genreToDelete = genreRepository.getByName(genre.getName());
    genreRepository.delete(genreToDelete);
    assertNull(genreRepository.getByName(genre.getName()).getName());

  }

  @Test
  public void getById() throws DaoException {
    Genre genre = Genre.builder().id(10L).name("Genre").build();
    genreRepository.create(genre);
    Genre genreFromDb = genreRepository.getByName(genre.getName());
    assertEquals(genre.getName(), genreRepository.getById(genreFromDb.getId()).getName());
  }

  @Test
  public void getByName() throws DaoException {
    Genre genre = Genre.builder().name("Hot").build();
    genreRepository.create(genre);
    Genre byName = genreRepository.getByName(genre.getName());
    assertEquals(genre.getName(), byName.getName());
  }

  @Test
  public void getAll() throws DaoException {
    Genre genre = Genre.builder().name("Great").build();
    genreRepository.create(genre);
    final List<Genre> allGenres = genreRepository.getAll();
    assertFalse(allGenres.isEmpty());
  }
}