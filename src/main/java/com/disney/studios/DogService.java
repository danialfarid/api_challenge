package com.disney.studios;

import com.disney.studios.db.DogRepository;
import com.disney.studios.model.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DogService {

    @Autowired
    DogRepository dogRepo;

    public Iterable<Dog> getDogs() {
        return dogRepo.findAll(
                new Sort(new Sort.Order(Sort.Direction.ASC, "breed"),
                        new Sort.Order(Sort.Direction.DESC, "votes")));
    }

    public List<Dog> getDogs(String breed) {
        return dogRepo.findByBreedOrderByVotesDesc(breed);
    }

    public boolean addFavorite(Long dogId, Long userId) {
        Dog dog = dogRepo.findOne(dogId);
        if (dog == null) return false;
        if (dog.getFavorites().add(userId)) {
            dog.setVotes(dog.getVotes() + 1);
        }
        dogRepo.save(dog);
        return true;
    }

    public boolean removeFavorite(Long dogId, Long userId) {
        Dog dog = dogRepo.findOne(dogId);
        if (dog == null) return false;
        if (dog.getFavorites().remove(userId)) {
            dog.setVotes(dog.getVotes() - 1);
        }
        dogRepo.save(dog);
        return true;
    }
}
