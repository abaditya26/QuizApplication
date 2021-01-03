package com.aditya.quizapplication.Models;

public class ModelOptions {
    private String title, description, icon, role;

    public ModelOptions(String title, String description, String icon, String role) {
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
