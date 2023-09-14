package info.danilocangucu.shop.models;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

import info.danilocangucu.shop.views.CreatedProductView;
import info.danilocangucu.shop.views.PublicProductView;

@Data
@Builder
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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
