 package br.com.fiap.tech.challenge._2.integration;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.AddressEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.FoodTypeEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.RestaurantEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserTypeEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.RestaurantRepository;
import br.com.fiap.tech.challenge._2.presentation.controller.request.RestaurantRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestaurantRepository restaurantRepository;


    @Test
    @DisplayName("Should create restaurant successfully")
    void shouldCreateRestaurantSuccessfully() throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UserEntity user =
                userRepository.save(
                        new UserEntity(
                                "Joao da Silva",
                                "joao@email.com",
                                userType
                        )
                );

        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity(
                                "Brazilian"
                        )
                );

        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua Principal",
                                "Centro",
                                "Fortaleza",
                                "CE",
                                "60000000",
                                "Brazil"
                        )
                );

        LocalTime startTime =
                LocalTime.now().plusMinutes(10);

        LocalTime endTime =
                LocalTime.now().plusMinutes(60);

        RestaurantRequest request =
                new RestaurantRequest(
                        "Restaurant Joao",
                        "Brazilian food restaurant",
                        address.getId().toString(),
                        "100",
                        "Near the main square",
                        user.getId().toString(),
                        foodType.getId().toString(),
                        startTime,
                        endTime
                );

        // Act
        mockMvc.perform(
                        post("/restaurants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated());

        // Assert
        assertThat(restaurantRepository.findAll())
                .hasSize(1);

        RestaurantEntity restaurant =
                restaurantRepository.findAll().get(0);

        assertThat(restaurant.getName())
                .isEqualTo("Restaurant Joao");

        assertThat(restaurant.getDescription())
                .isEqualTo("Brazilian food restaurant");

        assertThat(restaurant.getAddressEntity().getId())
                .isEqualTo(address.getId());

        assertThat(restaurant.getAddressNumber())
                .isEqualTo("100");

        assertThat(restaurant.getAddressComplement())
                .isEqualTo("Near the main square");

        assertThat(restaurant.getUserEntity().getId())
                .isEqualTo(user.getId());

        assertThat(restaurant.getFoodTypeEntity().getId())
                .isEqualTo(foodType.getId());
    }


    @Test
    @DisplayName("Should return 404 when creating restaurant with invalid user")
    void shouldReturn404WhenCreateRestaurantWithInvalidUser()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity(
                                "Brazilian"
                        )
                );

        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua Principal",
                                "Centro",
                                "Fortaleza",
                                "CE",
                                "60000000",
                                "Brazil"
                        )
                );

        UUID invalidUserId =
                UUID.randomUUID();

        RestaurantRequest request =
                new RestaurantRequest(
                        "Restaurant Joao",
                        "Brazilian food restaurant",
                        address.getId().toString(),
                        "100",
                        null,
                        invalidUserId.toString(),
                        foodType.getId().toString(),
                        LocalTime.now().plusMinutes(10),
                        LocalTime.now().plusMinutes(60)
                );

        // Act
        mockMvc.perform(
                        post("/restaurants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound());

        // Assert
        assertThat(restaurantRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 404 when creating restaurant with invalid food type")
    void shouldReturn404WhenCreateRestaurantWithInvalidFoodType()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UserEntity user =
                userRepository.save(
                        new UserEntity(
                                "Joao da Silva",
                                "joao@email.com",
                                userType
                        )
                );

        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua Principal",
                                "Centro",
                                "Fortaleza",
                                "CE",
                                "60000000",
                                "Brazil"
                        )
                );

        UUID invalidFoodTypeId =
                UUID.randomUUID();

        RestaurantRequest request =
                new RestaurantRequest(
                        "Restaurant Joao",
                        "Brazilian food restaurant",
                        address.getId().toString(),
                        "100",
                        null,
                        user.getId().toString(),
                        invalidFoodTypeId.toString(),
                        LocalTime.now().plusMinutes(10),
                        LocalTime.now().plusMinutes(60)
                );

        // Act
        mockMvc.perform(
                        post("/restaurants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound());

        // Assert
        assertThat(restaurantRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should find restaurant by id successfully")
    void shouldFindRestaurantByIdSuccessfully()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UserEntity user =
                userRepository.save(
                        new UserEntity(
                                "Joao da Silva",
                                "joao@email.com",
                                userType
                        )
                );

        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity(
                                "Brazilian"
                        )
                );

        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua Principal",
                                "Centro",
                                "Fortaleza",
                                "CE",
                                "60000000",
                                "Brazil"
                        )
                );

        RestaurantEntity restaurant =
                restaurantRepository.save(
                        new RestaurantEntity(
                                "Restaurant Joao",
                                "Brazilian food restaurant",
                                address,
                                "100",
                                null,
                                foodType,
                                user,
                                LocalTime.of(10, 0),
                                LocalTime.of(22, 0)
                        )
                );

        // Act
        mockMvc.perform(
                        get("/restaurants/{id}", restaurant.getId())
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(restaurant.getId().toString())
                )
                .andExpect(
                        jsonPath("$.name")
                                .value("Restaurant Joao")
                )
                .andExpect(
                        jsonPath("$.description")
                                .value("Brazilian food restaurant")
                );

        // Assert
        assertThat(
                restaurantRepository.findById(restaurant.getId())
        ).isPresent();
    }


    @Test
    @DisplayName("Should return 404 when restaurant does not exist")
    void shouldReturn404WhenRestaurantDoesNotExist()
            throws Exception {

        // Arrange
        UUID restaurantId =
                UUID.randomUUID();

        // Act
        mockMvc.perform(
                        get("/restaurants/{id}", restaurantId)
                )
                .andExpect(status().isNotFound());

        // Assert
        assertThat(
                restaurantRepository.findById(restaurantId)
        ).isEmpty();
    }


    @Test
    @DisplayName("Should return all restaurants successfully")
    void shouldReturnAllRestaurantsSuccessfully()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UserEntity user =
                userRepository.save(
                        new UserEntity(
                                "Joao da Silva",
                                "joao@email.com",
                                userType
                        )
                );

        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity(
                                "Brazilian"
                        )
                );

        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua Principal",
                                "Centro",
                                "Fortaleza",
                                "CE",
                                "60000000",
                                "Brazil"
                        )
                );

        restaurantRepository.save(
                new RestaurantEntity(
                        "Restaurant Joao",
                        "Brazilian food",
                        address,
                        "100",
                        null,
                        foodType,
                        user,
                        LocalTime.of(10, 0),
                        LocalTime.of(22, 0)
                )
        );

        restaurantRepository.save(
                new RestaurantEntity(
                        "Restaurant Maria",
                        "Italian food",
                        address,
                        "200",
                        null,
                        foodType,
                        user,
                        LocalTime.of(11, 0),
                        LocalTime.of(23, 0)
                )
        );

        // Act
        mockMvc.perform(
                        get("/restaurants")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(2)
                );

        // Assert
        assertThat(restaurantRepository.findAll())
                .hasSize(2);
    }


    @Test
    @DisplayName("Should return empty list when there are no restaurants")
    void shouldReturnEmptyListWhenThereAreNoRestaurants()
            throws Exception {

        // Arrange

        // Act
        mockMvc.perform(
                        get("/restaurants")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(0)
                );

        // Assert
        assertThat(restaurantRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should update restaurant successfully")
    void shouldUpdateRestaurantSuccessfully()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UserEntity user =
                userRepository.save(
                        new UserEntity(
                                "Joao da Silva",
                                "joao@email.com",
                                userType
                        )
                );

        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity(
                                "Brazilian"
                        )
                );

        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua Principal",
                                "Centro",
                                "Fortaleza",
                                "CE",
                                "60000000",
                                "Brazil"
                        )
                );

        RestaurantEntity restaurant =
                restaurantRepository.save(
                        new RestaurantEntity(
                                "Restaurant Joao",
                                "Brazilian food",
                                address,
                                "100",
                                null,
                                foodType,
                                user,
                                LocalTime.of(10, 0),
                                LocalTime.of(22, 0)
                        )
                );

        RestaurantRequest request =
                new RestaurantRequest(
                        "Restaurant Updated",
                        "Updated description",
                        address.getId().toString(),
                        "200",
                        "Updated complement",
                        user.getId().toString(),
                        foodType.getId().toString(),
                        LocalTime.now().plusMinutes(10),
                        LocalTime.now().plusMinutes(60)
                );

        // Act
        mockMvc.perform(
                        put("/restaurants/{id}", restaurant.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(restaurant.getId().toString())
                )
                .andExpect(
                        jsonPath("$.name")
                                .value("Restaurant Updated")
                )
                .andExpect(
                        jsonPath("$.description")
                                .value("Updated description")
                );

        // Assert
        RestaurantEntity updated =
                restaurantRepository.findById(restaurant.getId())
                        .orElseThrow();

        assertThat(updated.getName())
                .isEqualTo("Restaurant Updated");

        assertThat(updated.getDescription())
                .isEqualTo("Updated description");

        assertThat(updated.getAddressNumber())
                .isEqualTo("200");

        assertThat(updated.getAddressComplement())
                .isEqualTo("Updated complement");
    }


    @Test
    @DisplayName("Should return 404 when updating restaurant with invalid user")
    void shouldReturn404WhenUpdateRestaurantWithInvalidUser()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UserEntity user =
                userRepository.save(
                        new UserEntity(
                                "Joao da Silva",
                                "joao@email.com",
                                userType
                        )
                );

        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity(
                                "Brazilian"
                        )
                );

        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua Principal",
                                "Centro",
                                "Fortaleza",
                                "CE",
                                "60000000",
                                "Brazil"
                        )
                );

        RestaurantEntity restaurant =
                restaurantRepository.save(
                        new RestaurantEntity(
                                "Restaurant Joao",
                                "Brazilian food",
                                address,
                                "100",
                                null,
                                foodType,
                                user,
                                LocalTime.of(10, 0),
                                LocalTime.of(22, 0)
                        )
                );

        RestaurantRequest request =
                new RestaurantRequest(
                        "Restaurant Updated",
                        "Updated description",
                        address.getId().toString(),
                        "200",
                        null,
                        UUID.randomUUID().toString(),
                        foodType.getId().toString(),
                        LocalTime.now().plusMinutes(10),
                        LocalTime.now().plusMinutes(60)
                );

        // Act
        mockMvc.perform(
                        put("/restaurants/{id}", restaurant.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound());

        // Assert
        RestaurantEntity unchanged =
                restaurantRepository.findById(restaurant.getId())
                        .orElseThrow();

        assertThat(unchanged.getName())
                .isEqualTo("Restaurant Joao");
    }


    @Test
    @DisplayName("Should return 404 when updating restaurant with invalid food type")
    void shouldReturn404WhenUpdateRestaurantWithInvalidFoodType()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UserEntity user =
                userRepository.save(
                        new UserEntity(
                                "Joao da Silva",
                                "joao@email.com",
                                userType
                        )
                );

        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity(
                                "Brazilian"
                        )
                );

        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua Principal",
                                "Centro",
                                "Fortaleza",
                                "CE",
                                "60000000",
                                "Brazil"
                        )
                );

        RestaurantEntity restaurant =
                restaurantRepository.save(
                        new RestaurantEntity(
                                "Restaurant Joao",
                                "Brazilian food",
                                address,
                                "100",
                                null,
                                foodType,
                                user,
                                LocalTime.of(10, 0),
                                LocalTime.of(22, 0)
                        )
                );

        RestaurantRequest request =
                new RestaurantRequest(
                        "Restaurant Updated",
                        "Updated description",
                        address.getId().toString(),
                        "200",
                        null,
                        user.getId().toString(),
                        UUID.randomUUID().toString(),
                        LocalTime.now().plusMinutes(10),
                        LocalTime.now().plusMinutes(60)
                );

        // Act
        mockMvc.perform(
                        put("/restaurants/{id}", restaurant.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound());

        // Assert
        RestaurantEntity unchanged =
                restaurantRepository.findById(restaurant.getId())
                        .orElseThrow();

        assertThat(unchanged.getName())
                .isEqualTo("Restaurant Joao");
    }


    @Test
    @DisplayName("Should return 404 when updating nonexistent restaurant")
    void shouldReturn404WhenUpdateNonexistentRestaurant()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UserEntity user =
                userRepository.save(
                        new UserEntity(
                                "Joao da Silva",
                                "joao@email.com",
                                userType
                        )
                );

        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity(
                                "Brazilian"
                        )
                );

        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua Principal",
                                "Centro",
                                "Fortaleza",
                                "CE",
                                "60000000",
                                "Brazil"
                        )
                );

        UUID restaurantId =
                UUID.randomUUID();

        RestaurantRequest request =
                new RestaurantRequest(
                        "Restaurant Updated",
                        "Updated description",
                        address.getId().toString(),
                        "200",
                        null,
                        user.getId().toString(),
                        foodType.getId().toString(),
                        LocalTime.now().plusMinutes(10),
                        LocalTime.now().plusMinutes(60)
                );

        // Act
        mockMvc.perform(
                        put("/restaurants/{id}", restaurantId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound());

        // Assert
        assertThat(
                restaurantRepository.findById(restaurantId)
        ).isEmpty();
    }


    @Test
    @DisplayName("Should delete restaurant successfully")
    void shouldDeleteRestaurantSuccessfully()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UserEntity user =
                userRepository.save(
                        new UserEntity(
                                "Joao da Silva",
                                "joao@email.com",
                                userType
                        )
                );

        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity(
                                "Brazilian"
                        )
                );

        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua Principal",
                                "Centro",
                                "Fortaleza",
                                "CE",
                                "60000000",
                                "Brazil"
                        )
                );

        RestaurantEntity restaurant =
                restaurantRepository.save(
                        new RestaurantEntity(
                                "Restaurant Joao",
                                "Brazilian food",
                                address,
                                "100",
                                null,
                                foodType,
                                user,
                                LocalTime.of(10, 0),
                                LocalTime.of(22, 0)
                        )
                );

        UUID restaurantId =
                restaurant.getId();

        // Act
        mockMvc.perform(
                        delete("/restaurants/{id}", restaurantId)
                )
                .andExpect(status().isNoContent());

        // Assert
        assertThat(
                restaurantRepository.findById(restaurantId)
        ).isEmpty();
    }


    @Test
    @DisplayName("Should return 404 when deleting nonexistent restaurant")
    void shouldReturn404WhenDeleteNonexistentRestaurant()
            throws Exception {

        // Arrange
        UUID restaurantId =
                UUID.randomUUID();

        // Act
        mockMvc.perform(
                        delete("/restaurants/{id}", restaurantId)
                )
                .andExpect(status().isNotFound());

        // Assert
        assertThat(
                restaurantRepository.findById(restaurantId)
        ).isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when creating restaurant with invalid fields")
    void shouldReturn422WhenCreateRestaurantWithInvalidFields()
            throws Exception {

        // Arrange
        RestaurantRequest request =
                new RestaurantRequest(
                        "abc",
                        "Brazilian food",
                        UUID.randomUUID().toString(),
                        "",
                        null,
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        LocalTime.now().plusMinutes(10),
                        LocalTime.now().plusMinutes(60)
                );

        // Act
        mockMvc.perform(
                        post("/restaurants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isUnprocessableEntity());

        // Assert
        assertThat(restaurantRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when creating restaurant with null required fields")
    void shouldReturn422WhenCreateRestaurantWithNullRequiredFields()
            throws Exception {

        // Arrange
        RestaurantRequest request =
                new RestaurantRequest(
                        null,
                        "Brazilian food",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );

        // Act
        mockMvc.perform(
                        post("/restaurants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isUnprocessableEntity());

        // Assert
        assertThat(restaurantRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when updating restaurant with invalid fields")
    void shouldReturn422WhenUpdateRestaurantWithInvalidFields()
            throws Exception {

        // Arrange
        UUID restaurantId =
                UUID.randomUUID();

        RestaurantRequest request =
                new RestaurantRequest(
                        "abc",
                        "Updated description",
                        UUID.randomUUID().toString(),
                        "",
                        null,
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        null,
                        null
                );

        // Act
        mockMvc.perform(
                        put("/restaurants/{id}", restaurantId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isUnprocessableEntity());

        // Assert
        assertThat(
                restaurantRepository.findById(restaurantId)
        ).isEmpty();
    }
}

