package info.danilo.products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import info.danilocangucu.products.ProductsApplication;
import net.minidev.json.JSONArray;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProductsApplication.class)
@AutoConfigureMockMvc
class ProductsApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnAllProductsWithoutUserIdWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("http://localhost:8080/products", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray userIds = documentContext.read("$..userId");
		assertThat(userIds.size()).isEqualTo(0);
    }
}
