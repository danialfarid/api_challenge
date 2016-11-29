package com.disney.studios;

import com.disney.studios.model.Dog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DogControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAll() {
        ResponseEntity<Dog[]> entity = restTemplate.getForEntity("/dogs", Dog[].class);
        assertThat(entity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(entity.getBody().length).isEqualTo(90);
        assertThat(entity.getBody()[0].getBreed()).isEqualTo("Labrador");
    }

    @Test
    public void testGetBreed() {
        ResponseEntity<Dog[]> entity = restTemplate.getForEntity("/dogs/Retriever", Dog[].class);
        assertThat(entity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(entity.getBody().length).isEqualTo(33);
        assertThat(entity.getBody()[0].getBreed()).isEqualTo("Retriever");
    }

    @Test
    public void testFavorite() {
        restTemplate.put("/dogs/10/favorites/1", null);
        restTemplate.put("/dogs/10/favorites/1", null);
        restTemplate.put("/dogs/10/favorites/1", null);
        restTemplate.put("/dogs/10/favorites/2", null);
        restTemplate.put("/dogs/10/favorites/3", null);
        restTemplate.put("/dogs/11/favorites/1", null);
        restTemplate.put("/dogs/11/favorites/5", null);

        Dog[] dogs = restTemplate.getForEntity("/dogs", Dog[].class).getBody();
        assertThat(dogs[0].getVotes()).isEqualTo(3);

        // make sure they are sorted by favorites
        assertThat(dogs[0].getId()).isEqualTo(10);
        assertThat(dogs[1].getId()).isEqualTo(11);

        //remove favorites
        restTemplate.delete("/dogs/10/favorites/1");
        restTemplate.delete("/dogs/10/favorites/1");
        restTemplate.delete("/dogs/10/favorites/2");

        dogs = restTemplate.getForEntity("/dogs", Dog[].class).getBody();
        assertThat(dogs[1].getVotes()).isEqualTo(1);

        // make sure they are sorted by favorites
        assertThat(dogs[0].getId()).isEqualTo(11);
        assertThat(dogs[1].getId()).isEqualTo(10);
    }
}