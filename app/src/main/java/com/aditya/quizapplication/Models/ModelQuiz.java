package com.aditya.quizapplication.Models;

public class ModelQuiz {
    private String id, owner, name;

    public ModelQuiz() {
    }

    public ModelQuiz(String id, String owner, String name) {
        this.id = id;
        this.owner = owner;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
