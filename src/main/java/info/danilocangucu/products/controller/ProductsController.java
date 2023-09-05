package info.danilocangucu.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.data.domain.Pageable;

import info.danilocangucu.products.Views;
import info.danilocangucu.products.model.Product;
import info.danilocangucu.products.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping
    @JsonView(Views.Api.class)
    public ResponseEntity<List<Product>> findAll(Pageable pageable) {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }
}
