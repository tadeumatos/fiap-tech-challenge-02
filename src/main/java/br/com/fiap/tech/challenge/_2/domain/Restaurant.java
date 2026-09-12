package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.domain.exception.ValidationFieldsException;

import java.time.LocalTime;
import java.util.UUID;

public class Restaurant {
    private UUID id;
    private String name;
    private FoodType foodType;
    private LocalTime startTime;
    private LocalTime endTime;
    private User user;
    private String description;

    public Restaurant(UUID id, String name, FoodType foodType, LocalTime startTime, LocalTime endTime, User user, String description) {
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
        this.description = description;
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

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    void validation()
    {
        if(this.id==null)
            throw new ValidationFieldsException("The id is required field");

        if(this.name==null || this.name.trim().isEmpty())
            throw new ValidationFieldsException("The name is required field");

        if(this.foodType==null)
            throw new ValidationFieldsException("The food type is required field");

        if(this.startTime==null)
            throw new ValidationFieldsException("The start time type is required field");

        if(this.endTime==null)
            throw new ValidationFieldsException("The end time type is required field");

        if(this.user==null)
            throw new ValidationFieldsException("The user is required field");

        if (this.startTime.isAfter(this.endTime))
            throw new ValidationFieldsException(
                    "The start time cannot be after the end time"
            );

        if(!this.user.getUserType().isOwner())
            throw new ValidationFieldsException("The user have be owner");
    }

}
