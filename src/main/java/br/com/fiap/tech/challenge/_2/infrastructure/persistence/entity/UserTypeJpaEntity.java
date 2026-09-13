package br.com.fiap.tech.challenge._2.infrastructure.persistence.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tb_usertypes")
public class UserTypeJpaEntity {
    @Id
    private UUID id;
    private String name;
    private boolean owner;

    protected UserTypeJpaEntity() {
    }

    public UserTypeJpaEntity( UUID id,String name,boolean owner)
    {
        this.id = id;
        this.name = name;
        this.owner = owner;
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
}
