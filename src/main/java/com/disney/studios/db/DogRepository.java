package com.disney.studios.db;

import com.disney.studios.model.Dog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DogRepository extends PagingAndSortingRepository<Dog, Long> {
    List<Dog> findByBreedOrderByVotesDesc(String breed);
}
