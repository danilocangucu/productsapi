package info.danilocangucu.controllers;

import java.util.List;
import java.util.Optional;

import java.net.URI;

import info.danilocangucu.services.UserService;
import info.danilocangucu.views.PrivateProductView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import info.danilocangucu.models.Product;
import info.danilocangucu.repositories.ProductRepository;
import info.danilocangucu.services.ProductService;
import info.danilocangucu.views.PublicProductView;
import lombok.RequiredArgsConstructor;

import static info.danilocangucu.controllers.AuthenticationController.createAndSendErrorResponse;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;
    private final UserService userService;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/public/products")
    @JsonView(PublicProductView.class)
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/private/products")
    @JsonView(PrivateProductView.class)
    public ResponseEntity<List<Product>> findAllFromUser(
            @RequestHeader("Authorization") String authHeader
    ) {
        String userId = userService.getUserIdFromHeader(authHeader);
        return ResponseEntity.ok(productService.findAllFromUser(userId));
    }

    @JsonView(PrivateProductView.class)
    @PostMapping("/private/products")
    public ResponseEntity<Void> createProduct(
            @Valid @RequestBody Product request,
            BindingResult bindingResult,
            @RequestHeader("Authorization") String authHeader,
            UriComponentsBuilder ucb
            ) {
            if (bindingResult.hasErrors()) { createAndSendErrorResponse(bindingResult); }

            request.setUserId(
                    userService.getUserIdFromHeader(authHeader)
            );
            Product createdProduct = productService.save(request);
            URI locationOfNewProduct = ucb
                .path("private/products/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewProduct).build();
    }

    @JsonView(PublicProductView.class)
    @GetMapping("/private/products/{requestedId}")
    public ResponseEntity<Product> findById(
            @PathVariable String requestedId,
            @RequestHeader("Authorization") String authHeader) {
        String userId = userService.getUserIdFromHeader(authHeader);
        Product product = findProduct(requestedId, userId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/private/products/{requestedId}")
    private ResponseEntity<Void> putProduct(
        @PathVariable String requestedId,
        @Valid @RequestBody Product productUpdate,
        BindingResult bindingResult,
        @RequestHeader("Authorization") String authHeader) {
        if (bindingResult.hasErrors()) { createAndSendErrorResponse(bindingResult); }

        String userId = userService.getUserIdFromHeader(authHeader);
            Product product = findProduct(requestedId, userId);
            if (product != null) {
                Product updatedProduct = new Product(
                    requestedId,
                    productUpdate.getName(),
                    productUpdate.getDescription(),
                    productUpdate.getPrice(),
                    userId);

                productRepository.save(updatedProduct);
                return ResponseEntity.noContent().build();
            }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/private/products/{id}")
    private ResponseEntity<Void> deleteProduct(
        @PathVariable String id,
        @RequestHeader("Authorization") String authHeader) {
            String userId = userService.getUserIdFromHeader(authHeader);
            if (productRepository.existsByIdAndUserId(id, userId)) {
                productRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            }
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private Product findProduct(String requestedId, String userId) {
        Optional<Product> searchedProduct = productRepository.findById(requestedId);
        if (searchedProduct.isEmpty()) {
            return null;
        }

        Product foundProduct = searchedProduct.get();
        if (!foundProduct.getUserId().equals(userId)) { return null; }
        return foundProduct;
    }
}
