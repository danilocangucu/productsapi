package info.danilocangucu.services;

import info.danilocangucu.configs.JwtService;
import info.danilocangucu.models.Product;
import info.danilocangucu.models.User;
import info.danilocangucu.repositories.ProductRepository;
import info.danilocangucu.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
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

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    public String getUserIdFromHeader(String authHeader) {
        String userEmail = jwtService.extractSubject(authHeader.substring(7));
        Optional<User> user = userRepository.findByEmail(userEmail);
        String userId = user.map(User::getId).orElse(null);
        return userId;
    }
}
