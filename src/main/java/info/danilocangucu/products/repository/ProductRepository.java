package info.danilocangucu.products.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import info.danilocangucu.products.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
