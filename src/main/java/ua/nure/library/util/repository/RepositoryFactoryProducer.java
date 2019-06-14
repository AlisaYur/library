package ua.nure.library.util.repository;

/**
 * @author Artem Kudria
 */
public final class RepositoryFactoryProducer {

  private RepositoryFactoryProducer() {
  }

  public static RepositoryFactoryImpl repositoryFactory() {
    return new RepositoryFactoryImpl();
  }
}
