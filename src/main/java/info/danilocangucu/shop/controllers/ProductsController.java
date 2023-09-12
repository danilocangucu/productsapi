package info.danilocangucu.shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.data.domain.Pageable;

import info.danilocangucu.shop.Views;
import info.danilocangucu.shop.controllers.services.ProductService;
import info.danilocangucu.shop.models.Product;
import info.danilocangucu.shop.repository.ProductRepository;
import info.danilocangucu.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/public/products")
    @JsonView(Views.Products.class)
    public ResponseEntity<List<Product>> findAll(Pageable pageable) {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping("/private/newproduct")
    public ResponseEntity<Product> create(
        @RequestBody Product request,
        @RequestHeader("Authorization") String authHeader
    ) {
        request.setUserId(
            productService.getUserIdFromHeader(authHeader)
        );
        return ResponseEntity.ok(productService.save(request));
    }


}
