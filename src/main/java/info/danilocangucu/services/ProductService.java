package info.danilocangucu.services;

import org.springframework.stereotype.Service;

import info.danilocangucu.configs.JwtService;
import info.danilocangucu.models.Product;
import info.danilocangucu.models.User;
import info.danilocangucu.repositories.ProductRepository;
import info.danilocangucu.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    
    public Product save(Product request) {
        var product = Product.builder()
            .name(request.getName())
            .price(request.getPrice())
            .description(request.getDescription())
            .userId(request.getUserId())
            .build();

        return productRepository.save(product);
    }
}
