package com.aditya.quizapplication.Models;

public class ModelQuiz {
    private String id, name, ownerId, owner;

    public ModelQuiz() {
    }

    public ModelQuiz(String id, String name, String ownerId, String owner) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}