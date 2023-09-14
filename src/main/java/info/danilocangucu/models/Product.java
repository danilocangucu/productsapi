package info.danilocangucu.shop.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import info.danilocangucu.shop.views.CreatedProductView;
import info.danilocangucu.shop.views.PublicProductView;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    @JsonView(CreatedProductView.class)
    private String id;
    @JsonView(PublicProductView.class)
    private String name;
    @JsonView(PublicProductView.class)
    private String description;
    @JsonView(PublicProductView.class)
    private Double price;
    
    private String userId;

    public Product(String id, String name, String description, Double price, String userId) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.userId = userId;
    }

}
