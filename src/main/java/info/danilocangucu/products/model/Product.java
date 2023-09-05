package info.danilocangucu.products.model;
import info.danilocangucu.products.Views;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

@Document(collection = "products")
public class Product {
    @Id
    private String id;
    @JsonView(Views.Api.class)
    private String name;
    @JsonView(Views.Api.class)
    private String description;
    @JsonView(Views.Api.class)
    private Double price;
    
    private String userId;

    public String getId() {
        return this.id;
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
