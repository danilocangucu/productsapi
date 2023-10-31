package info.danilocangucu.models;

import info.danilocangucu.views.PrivateProductView;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Digits;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import info.danilocangucu.views.PublicProductView;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    @JsonView(PrivateProductView.class)
    private String id;
    @NotNull(message = "Product name cannot be null")
    @NotBlank(message = "Product name cannot be blank")
    @Size(min=3, max=30, message="Name must be between 3 and 30 characters")
    @JsonView(PublicProductView.class)
    private String name;
    @NotNull(message = "Product description cannot be null")
    @NotBlank(message = "Product description cannot be blank")
    @Size(min=10, max=50, message="Description must be between 10 and 50 characters")
    @JsonView(PublicProductView.class)
    private String description;
    @JsonView(PublicProductView.class)
    @NotNull(message = "Product price cannot be null")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid decimal number")
    private Double price;
    private String userId;

}
