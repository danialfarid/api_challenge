package com.disney.studios;

import com.disney.studios.model.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin(origins = "*")
public class DogController {

    @Autowired
    DogService dogService;

    @RequestMapping(method = GET, path = "/dogs")
    public List<Dog> getDogs() throws IOException {
        return dogService.getDogs();
    }

    @RequestMapping(method = GET, path = "/dogs/{breed}")
    public List<Dog> getDogs(@PathVariable String breed) throws IOException {
        return dogService.getDogs(breed);
    }

    @RequestMapping(method = PUT, path = "/dogs/{id}/favorites/{userId}")
    public void upVote(@PathVariable Long id, @PathVariable Long userId) throws IOException {
        dogService.addFavorite(id, userId);
    }

    @RequestMapping(method = DELETE, path = "/dogs/{id}/favorites/{userId}")
    public void downVote(@PathVariable Long id, @PathVariable Long userId) throws IOException {
        dogService.removeFavorite(id, userId);
    }
}
