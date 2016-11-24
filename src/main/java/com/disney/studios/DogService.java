package com.disney.studios;

import com.disney.studios.db.DataStore;
import com.disney.studios.model.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DogService {
    @Autowired
    DataStore dataStore;

    public Dog getDog(Long id) {
        List<Dog> dogs = dataStore.getDogs(String.format("SELECT * FROM DOGS WHERE id = %d", id));
        return dogs.isEmpty() ? null : dogs.get(0);
    }

    public List<Dog> getDogs() {
        return dataStore.getDogs("SELECT * FROM DOGS ORDER BY breed, vote desc");
    }

    public List<Dog> getDogs(String breed) {
        return dataStore.getDogs(String.format(
                "SELECT * FROM DOGS WHERE breed = '%s' ORDER BY vote desc", breed));
    }

    public boolean addFavorite(Long dogId, Long userId) {
        return dataStore.addFavorite(dogId, userId);
    }

    public boolean removeFavorite(Long dogId, Long userId) {
        return dataStore.removeFavorite(dogId, userId);
    }
}
