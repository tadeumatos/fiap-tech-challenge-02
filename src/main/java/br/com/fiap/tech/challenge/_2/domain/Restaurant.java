package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.exceptions.BusinessException;
import br.com.fiap.tech.challenge._2.exceptions.ValidationFieldsException;

import java.time.LocalTime;
import java.util.UUID;

public class Restaurant {
    private UUID id;
    private String name;
    private String description;
    private Address address;
    private String addressNumber;
    private String addressComplement;
    private User user;
    private FoodType foodType;
    private LocalTime startTime;
    private LocalTime endTime;

    public Restaurant(UUID id, String name, String description,Address address, String addressNumber,String addressComplement,User user,FoodType foodType, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address=address;
        this.addressNumber=addressNumber;
        this.addressComplement=addressComplement;
        this.user = user;
        this.foodType = foodType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.validation();
    }

    public static Restaurant create(UUID id, String name, String description,Address address, String addressNumber,String addressComplement,User user,FoodType foodType, LocalTime startTime, LocalTime endTime) {

        return new Restaurant(id,name,description,address,addressNumber,addressComplement,user,foodType,startTime,endTime);
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
        this.validation();
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {

        this.foodType = foodType;
        this.validation();
    }


    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {

        this.startTime = startTime;
        this.validation();
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {

        this.endTime = endTime;
        this.validation();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {

        this.user = user;
        this.validation();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        this.validation();
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
        this.validation();
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public void setAddressComplement(String addressComplement) {

        this.addressComplement = addressComplement;
    }

    void validation()
    {
        if(this.id==null)
            throw new ValidationFieldsException("The id is required field");

        if(this.name==null || this.name.trim().isEmpty())
            throw new ValidationFieldsException("The name is required field");

        if(this.user==null)
            throw new ValidationFieldsException("The user is required field");

        if(this.address==null)
            throw new ValidationFieldsException("The address is required field");

        if(this.addressNumber==null || this.addressNumber.trim().isEmpty())
            throw new ValidationFieldsException("The address complement is required field");

        if(this.foodType==null)
            throw new ValidationFieldsException("The food type is required field");

        if(this.startTime==null)
            throw new ValidationFieldsException("The start time type is required field");

        if(this.endTime==null)
            throw new ValidationFieldsException("The end time type is required field");

        if (this.startTime.isAfter(this.endTime))
            throw new BusinessException(
                    "The start time cannot be after the end time"
            );

        if(!this.user.getUserType().isOwner())
            throw new BusinessException("The user have be owner");
    }

}
