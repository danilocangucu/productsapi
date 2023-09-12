package info.danilocangucu.shop.controllers.services;

import org.springframework.stereotype.Service;

import info.danilocangucu.shop.configs.JwtService;
import info.danilocangucu.shop.models.Product;
import info.danilocangucu.shop.models.User;
import info.danilocangucu.shop.repository.ProductRepository;
import info.danilocangucu.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper productMapper = new ModelMapper();
    private final JwtService jwtService;
    
    public Product save(Product request) {
        var product = Product.builder()
            .name(request.getName())
            .price(request.getPrice())
            .description(request.getDescription())
            .userId(request.getUserId())
            .build();

        Product savedProduct = productRepository.save(product);
        return productMapper.map(savedProduct, Product.class);
    }

    public String getUserIdFromHeader(String authHeader) {
        String userEmail = jwtService.extractSubject(authHeader.substring(7));
        Optional<User> user = userRepository.findByEmail(userEmail);
        String userId = user.map(User::getId).orElse(null);
        return userId;
    }
}
