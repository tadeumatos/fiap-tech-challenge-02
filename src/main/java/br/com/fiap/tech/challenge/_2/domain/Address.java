package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.exceptions.ValidationFieldsException;

import java.util.UUID;

public class Address {
    private UUID id;
    private String name;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public Address(UUID id, String name, String neighborhood, String city, String state, String postalCode, String country) {
        this.id = id;
        this.name = name;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;

        this.validate();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
        this.validate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.validate();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        this.validate();
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        this.validate();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        this.validate();
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        this.validate();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        this.validate();
    }

    public static Address create(UUID id, String name, String neighborhood, String city, String state, String postalCode, String country) {
        return new Address(id,name,neighborhood,city,state,postalCode,country);
    }

    void validate()
    {
        if(this.id==null)
            throw new ValidationFieldsException("The id is required field");

        if(this.name==null || this.name.trim().isEmpty())
            throw new ValidationFieldsException("The name is required field");

        if(this.neighborhood==null || this.neighborhood.trim().isEmpty())
            throw new ValidationFieldsException("The neighborhood is required field");

        if(this.city==null || this.city.trim().isEmpty())
            throw new ValidationFieldsException("The city is required field");

        if(this.state==null || this.state.trim().isEmpty())
            throw new ValidationFieldsException("The state is required field");

        if(this.postalCode==null || this.postalCode.trim().isEmpty())
            throw new ValidationFieldsException("The postal code is required field");

        if(this.country==null || this.country.trim().isEmpty())
            throw new ValidationFieldsException("The country is required field");

    }
}
