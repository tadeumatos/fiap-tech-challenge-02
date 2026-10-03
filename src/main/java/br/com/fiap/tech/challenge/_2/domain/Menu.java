package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.exceptions.ValidationFieldsException;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

public class Menu {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean onlyLocal;
    private String foodPhoto;
    private Restaurant restaurant;
    private boolean active;

    public Menu(UUID id, String name, String description, BigDecimal price, boolean onlyLocal,String foodPhoto, Restaurant restaurant, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.onlyLocal=onlyLocal;
        this.foodPhoto=foodPhoto;
        this.restaurant = restaurant;
        this.active = active;
        this.validation();
    }
    public static Menu create(UUID id, String name, String description, BigDecimal price,boolean onlyLocal,String foodPhoto,Restaurant restaurant, boolean active) {

        return new Menu(id,name,description,price,onlyLocal,foodPhoto,restaurant,active);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
        this.validation();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.validation();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.validation();
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.validation();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        this.validation();
    }

    public boolean isOnlyLocal() {
        return onlyLocal;
    }

    public void setOnlyLocal(boolean onlyLocal) {
        this.onlyLocal = onlyLocal;
    }

    public String getFoodPhoto() {
        return foodPhoto;
    }

    public void setFoodPhoto(String foodPhoto) {
        this.foodPhoto = foodPhoto;
        this.validation();
    }

    private void validation()
    {
        if(this.id==null)
            throw new ValidationFieldsException("The id is required field");

        if(this.name==null || this.name.trim().isEmpty())
            throw new ValidationFieldsException("The name is required field");

        if(this.description==null || this.description.trim().isEmpty())
            throw new ValidationFieldsException("The description is required field");

        if(this.price==null)
            throw new ValidationFieldsException("The price is required field");

        if(this.price.compareTo(BigDecimal.ZERO)<=0)
            throw new ValidationFieldsException("The price not be < 0");

        if(this.foodPhoto==null || this.foodPhoto.trim().isEmpty())
            throw new ValidationFieldsException("The food photo is required field");

        if(this.restaurant==null)
            throw new ValidationFieldsException("The restaurant is required field");
    }
}
