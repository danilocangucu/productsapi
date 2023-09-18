package info.danilocangucu.models;

import lombok.*;

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
    @JsonView(PublicProductView.class)
    private String name;
    @JsonView(PublicProductView.class)
    private String description;
    @JsonView(PublicProductView.class)
    private Double price;
    private String userId;

}
