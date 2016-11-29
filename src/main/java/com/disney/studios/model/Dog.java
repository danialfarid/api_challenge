package com.disney.studios.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;

    private String breed;

    private int votes;

    @ElementCollection
    private Set<Long> favorites = new HashSet<>();

    public Dog() {
    }

    public Dog(String url, String breed, int votes) {
        this.url = url;
        this.breed = breed;
        this.votes = votes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Set<Long> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Long> favorites) {
        this.favorites = favorites;
    }
}
