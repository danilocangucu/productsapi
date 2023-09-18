package info.danilocangucu.models;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Digits;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import info.danilocangucu.views.CreatedProductView;
import info.danilocangucu.views.PublicProductView;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    @JsonView(CreatedProductView.class)
    private String id;
    @NotNull
    @NotBlank
    @Size(min=3, max=30, message="Name must be between 3 and 30 characters")
    @JsonView(PublicProductView.class)
    private String name;
    @NotNull
    @NotBlank
    @Size(min=10, max=50, message="Description must be between 10 and 50 characters")
    @JsonView(PublicProductView.class)
    private String description;
    @JsonView(PublicProductView.class)
    @NotNull
    @NotBlank
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid decimal number")    private Double price;
    @NotNull
    private String userId;

}
