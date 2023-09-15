package info.danilocangucu.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import info.danilocangucu.models.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsByIdAndUserId(String id, String userId);
}
