package info.danilocangucu.shop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import info.danilocangucu.shop.models.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
