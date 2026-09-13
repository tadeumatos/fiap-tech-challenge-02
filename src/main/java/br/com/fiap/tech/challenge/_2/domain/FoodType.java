package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.domain.exception.ValidationFieldsException;

import java.util.UUID;

public class FoodType {
    UUID id;
    String name;

    public FoodType(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.validate();
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

    public static FoodType create(String name) {
        return new FoodType(
                UUID.randomUUID(),
                name
        );
    }

    void validate()
    {
        if(this.id==null)
            throw new ValidationFieldsException("The id is required field");

        if(this.name==null || this.name.trim().isEmpty())
            throw new ValidationFieldsException("The name is required field");
    }

}
