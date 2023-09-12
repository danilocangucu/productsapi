package info.danilocangucu.shop.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import info.danilocangucu.shop.models.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
