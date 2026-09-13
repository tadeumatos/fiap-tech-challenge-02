package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.domain.exception.ValidationFieldsException;

import java.util.UUID;

public class Menu {
    private UUID id;
    private String name;
    private String description;
    private Restaurant restaurant;
    private boolean active;

    public Menu(UUID id, String name, String description, Restaurant restaurant, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.restaurant = restaurant;
        this.active = active;
        this.validation();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    private void validation()
    {
        if(this.id==null)
            throw new ValidationFieldsException("The id is required field");

        if(this.name==null || this.name.trim().isEmpty())
            throw new ValidationFieldsException("The name is required field");

        if(this.description==null || this.description.trim().isEmpty())
            throw new ValidationFieldsException("The description is required field");

        if(this.restaurant==null)
            throw new ValidationFieldsException("The restaurant is required field");
    }
}
