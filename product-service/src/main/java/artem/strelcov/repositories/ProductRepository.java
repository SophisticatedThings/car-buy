package artem.strelcov.repositories;

import artem.strelcov.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product,String> {
    @Query("{'model': ?0}")
    Optional<Product> findByModel(String model);
    @Query("{'brand': ?0}")
    Optional<Product> findByBrand(String brand);
    Optional<Product> getProductById(String id);
}
