package info.danilocangucu.shop.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import info.danilocangucu.shop.models.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsByIdAndUserId(String id, String userId);
}
