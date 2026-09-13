package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.domain.exception.ValidationFieldsException;

import java.util.UUID;

public class UserType {
    private UUID id;
    private String name;
    private boolean owner;


    public UserType(UUID id,String name, boolean owner) {
        this.name = name;
        this.owner = owner;
        this.id = id;
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

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public static UserType create(String name,boolean owner) {
        return new UserType(
                UUID.randomUUID(),
                name,
                owner
        );
    }

    private void validation()
    {
      if(this.id==null)
         throw new ValidationFieldsException("The id is required field");

      if(this.name==null || this.name.trim().isEmpty())
         throw new ValidationFieldsException("The name is required field");
    }
}
