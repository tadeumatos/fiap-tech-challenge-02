package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.domain.exception.ValidationFieldsException;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String email;
    private UserType userType;

    public User(UUID id, String name, String email, UserType userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userType = userType;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    private void validation()
    {
        if(this.id==null)
            throw new ValidationFieldsException("The id is required field");

        if(this.name==null || this.name.trim().isEmpty())
            throw new ValidationFieldsException("The name is required field");

        if(this.email==null || this.email.trim().isEmpty())
            throw new ValidationFieldsException("The email is required field");

        if(this.userType==null)
            throw new ValidationFieldsException("The user type is required field");
    }
}
