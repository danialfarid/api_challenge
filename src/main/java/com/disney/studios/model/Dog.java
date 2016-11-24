package com.disney.studios.model;

public class Dog {
    Long id;
    String url;
    String breed;
    int vote;

    public Dog() {
    }

    public Dog(Long id, String url, String breed, int vote) {
        this.id = id;
        this.url = url;
        this.breed = breed;
        this.vote = vote;
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

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
