package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.exceptions.ValidationFieldsException;

import java.util.UUID;

public class FoodType {
    private UUID id;
    private String name;

    public FoodType(UUID id,String name) {
        this.id =id;
        this.name = name;
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

    public static FoodType create(UUID id,String name) {
        return new FoodType(id,name);
    }

    void validate()
    {
        if(this.id==null)
            throw new ValidationFieldsException("The id is required field");

        if(this.name==null || this.name.trim().isEmpty())
            throw new ValidationFieldsException("The name is required field");
    }

}
