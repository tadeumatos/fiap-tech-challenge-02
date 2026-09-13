package br.com.fiap.tech.challenge._2.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tb_foodtypes")
public class FoodTypeJpaEntity {
    @Id
    private UUID id;
    private String name;

    protected FoodTypeJpaEntity() {
    }

    public FoodTypeJpaEntity( UUID id,String name)
    {
        this.id = id;
        this.name = name;
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


}
