package br.com.fiap.tech.challenge._2.infrastructure.persistence.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="tb_restaurants")
@EntityListeners(AuditingEntityListener.class)
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "foodtype_id", nullable = false)
    private FoodTypeEntity foodTypeEntity;
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity addressEntity;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity userEntity;
    @OneToMany(mappedBy = "restaurantEntity")
    private List<MenuEntity> menus;

    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private String addressNumber;
    private String addressComplement;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public RestaurantEntity(String name, String description,AddressEntity addressEntity,String addressNumber,String addressComplement,
                             FoodTypeEntity foodTypeEntity, UserEntity userEntity, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.description = description;
        this.addressEntity=addressEntity;
        this.addressNumber=addressNumber;
        this.addressComplement=addressComplement;
        this.foodTypeEntity = foodTypeEntity;
        this.userEntity = userEntity;
        this.startTime = startTime;
        this.endTime = endTime;

    }

    public RestaurantEntity() { }

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

    public FoodTypeEntity getFoodTypeEntity() {
        return foodTypeEntity;
    }

    public void setFoodTypeEntity(FoodTypeEntity foodTypeEntity) {
        this.foodTypeEntity = foodTypeEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    public List<MenuEntity> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuEntity> menus) {
        this.menus = menus;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public void setAddressComplement(String addressComplement) {
        this.addressComplement = addressComplement;
    }


}
