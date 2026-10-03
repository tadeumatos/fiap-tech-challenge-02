package br.com.fiap.tech.challenge._2.integration;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.AddressEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.FoodTypeEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.MenuEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.RestaurantEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserTypeEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.MenuRepository;
import br.com.fiap.tech.challenge._2.presentation.controller.request.MenuRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MenuRepository menuRepository;


    @Test
    @DisplayName("Should create menu successfully")
    void shouldCreateMenuSuccessfully() throws Exception {

        // Arrange
        RestaurantEntity restaurant =
                createRestaurant();

        MenuRequest request =
                new MenuRequest(
                        "Feijoada",
                        "Traditional Brazilian feijoada",
                        new BigDecimal("39.90"),
                        true,
                        "https://image.com/feijoada.jpg",
                        restaurant.getId().toString(),
                        true
                );

        // Act
        mockMvc.perform(
                        post("/menus")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath("$.id")
                                .exists()
                )
                .andExpect(
                        jsonPath("$.name")
                                .value("Feijoada")
                )
                .andExpect(
                        jsonPath("$.description")
                                .value("Traditional Brazilian feijoada")
                )
                .andExpect(
                        jsonPath("$.price")
                                .value(39.90)
                )
                .andExpect(
                        jsonPath("$.onlyLocal")
                                .value(true)
                )
                .andExpect(
                        jsonPath("$.foodPhoto")
                                .value("https://image.com/feijoada.jpg")
                )
                .andExpect(
                        jsonPath("$.active")
                                .value(true)
                );

        // Assert
        assertThat(menuRepository.findAll())
                .hasSize(1);

        MenuEntity menu =
                menuRepository.findAll().get(0);

        assertThat(menu.getName())
                .isEqualTo("Feijoada");

        assertThat(menu.getDescription())
                .isEqualTo("Traditional Brazilian feijoada");

        assertThat(menu.getPrice())
                .isEqualByComparingTo("39.90");

        assertThat(menu.isOnlyLocal())
                .isTrue();

        assertThat(menu.getFoodPhoto())
                .isEqualTo("https://image.com/feijoada.jpg");

        assertThat(menu.getRestaurantEntity().getId())
                .isEqualTo(restaurant.getId());

        assertThat(menu.isActive())
                .isTrue();
    }


    @Test
    @DisplayName("Should return 404 when creating menu with invalid restaurant")
    void shouldReturn404WhenCreateMenuWithInvalidRestaurant()
            throws Exception {

        // Arrange
        UUID restaurantId =
                UUID.randomUUID();

        MenuRequest request =
                new MenuRequest(
                        "Feijoada",
                        "Traditional Brazilian feijoada",
                        new BigDecimal("39.90"),
                        true,
                        "https://image.com/feijoada.jpg",
                        restaurantId.toString(),
                        true
                );

        // Act
        mockMvc.perform(
                        post("/menus")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound());

        // Assert
        assertThat(menuRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should find menu by id successfully")
    void shouldFindMenuByIdSuccessfully()
            throws Exception {

        // Arrange
        RestaurantEntity restaurant =
                createRestaurant();

        MenuEntity menu =
                menuRepository.save(
                        new MenuEntity(
                                "Feijoada",
                                "Traditional Brazilian feijoada",
                                new BigDecimal("39.90"),
                                true,
                                "https://image.com/feijoada.jpg",
                                restaurant,
                                true
                        )
                );

        // Act
        mockMvc.perform(
                        get("/menus/{id}", menu.getId())
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(menu.getId().toString())
                )
                .andExpect(
                        jsonPath("$.name")
                                .value("Feijoada")
                )
                .andExpect(
                        jsonPath("$.description")
                                .value("Traditional Brazilian feijoada")
                )
                .andExpect(
                        jsonPath("$.price")
                                .value(39.90)
                )
                .andExpect(
                        jsonPath("$.onlyLocal")
                                .value(true)
                )
                .andExpect(
                        jsonPath("$.foodPhoto")
                                .value("https://image.com/feijoada.jpg")
                )
                .andExpect(
                        jsonPath("$.active")
                                .value(true)
                );

        // Assert
        assertThat(
                menuRepository.findById(menu.getId())
        ).isPresent();
    }


    @Test
    @DisplayName("Should return 404 when menu does not exist")
    void shouldReturn404WhenMenuDoesNotExist()
            throws Exception {

        // Arrange
        UUID menuId =
                UUID.randomUUID();

        // Act
        mockMvc.perform(
                        get("/menus/{id}", menuId)
                )
                .andExpect(status().isNotFound());

        // Assert
        assertThat(
                menuRepository.findById(menuId)
        ).isEmpty();
    }


    @Test
    @DisplayName("Should return all menus successfully")
    void shouldReturnAllMenusSuccessfully()
            throws Exception {

        // Arrange
        RestaurantEntity restaurant =
                createRestaurant();

        menuRepository.save(
                new MenuEntity(
                        "Feijoada",
                        "Traditional Brazilian feijoada",
                        new BigDecimal("39.90"),
                        true,
                        "https://image.com/feijoada.jpg",
                        restaurant,
                        true
                )
        );

        menuRepository.save(
                new MenuEntity(
                        "Picanha",
                        "Brazilian grilled beef",
                        new BigDecimal("59.90"),
                        false,
                        "https://image.com/picanha.jpg",
                        restaurant,
                        true
                )
        );

        // Act
        mockMvc.perform(
                        get("/menus")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(2)
                );

        // Assert
        assertThat(menuRepository.findAll())
                .hasSize(2);
    }


    @Test
    @DisplayName("Should return empty list when there are no menus")
    void shouldReturnEmptyListWhenThereAreNoMenus()
            throws Exception {

        // Arrange

        // Act
        mockMvc.perform(
                        get("/menus")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(0)
                );

        // Assert
        assertThat(menuRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should update menu successfully")
    void shouldUpdateMenuSuccessfully()
            throws Exception {

        // Arrange
        RestaurantEntity restaurant =
                createRestaurant();

        MenuEntity menu =
                menuRepository.save(
                        new MenuEntity(
                                "Feijoada",
                                "Traditional Brazilian feijoada",
                                new BigDecimal("39.90"),
                                true,
                                "https://image.com/feijoada.jpg",
                                restaurant,
                                true
                        )
                );

        MenuRequest request =
                new MenuRequest(
                        "Feijoada Updated",
                        "Updated Brazilian food",
                        new BigDecimal("49.90"),
                        false,
                        "https://image.com/updated.jpg",
                        restaurant.getId().toString(),
                        false
                );

        // Act
        mockMvc.perform(
                        put("/menus/{id}", menu.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(menu.getId().toString())
                )
                .andExpect(
                        jsonPath("$.name")
                                .value("Feijoada Updated")
                )
                .andExpect(
                        jsonPath("$.description")
                                .value("Updated Brazilian food")
                )
                .andExpect(
                        jsonPath("$.price")
                                .value(49.90)
                )
                .andExpect(
                        jsonPath("$.onlyLocal")
                                .value(false)
                )
                .andExpect(
                        jsonPath("$.foodPhoto")
                                .value("https://image.com/updated.jpg")
                )
                .andExpect(
                        jsonPath("$.active")
                                .value(false)
                );

        // Assert
        MenuEntity updated =
                menuRepository.findById(menu.getId())
                        .orElseThrow();

        assertThat(updated.getName())
                .isEqualTo("Feijoada Updated");

        assertThat(updated.getDescription())
                .isEqualTo("Updated Brazilian food");

        assertThat(updated.getPrice())
                .isEqualByComparingTo("49.90");

        assertThat(updated.isOnlyLocal())
                .isFalse();

        assertThat(updated.getFoodPhoto())
                .isEqualTo("https://image.com/updated.jpg");

        assertThat(updated.isActive())
                .isFalse();

        assertThat(updated.getRestaurantEntity().getId())
                .isEqualTo(restaurant.getId());
    }


    @Test
    @DisplayName("Should return 404 when updating menu with invalid restaurant")
    void shouldReturn404WhenUpdateMenuWithInvalidRestaurant()
            throws Exception {

        // Arrange
        RestaurantEntity restaurant =
                createRestaurant();

        MenuEntity menu =
                menuRepository.save(
                        new MenuEntity(
                                "Feijoada",
                                "Traditional Brazilian feijoada",
                                new BigDecimal("39.90"),
                                true,
                                "https://image.com/feijoada.jpg",
                                restaurant,
                                true
                        )
                );

        UUID invalidRestaurantId =
                UUID.randomUUID();

        MenuRequest request =
                new MenuRequest(
                        "Feijoada Updated",
                        "Updated Brazilian food",
                        new BigDecimal("49.90"),
                        false,
                        "https://image.com/updated.jpg",
                        invalidRestaurantId.toString(),
                        true
                );

        // Act
        mockMvc.perform(
                        put("/menus/{id}", menu.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound());

        // Assert
        MenuEntity unchanged =
                menuRepository.findById(menu.getId())
                        .orElseThrow();

        assertThat(unchanged.getName())
                .isEqualTo("Feijoada");

        assertThat(unchanged.getPrice())
                .isEqualByComparingTo("39.90");
    }


    @Test
    @DisplayName("Should return 404 when updating nonexistent menu")
    void shouldReturn404WhenUpdateNonexistentMenu()
            throws Exception {

        // Arrange
        RestaurantEntity restaurant =
                createRestaurant();

        UUID menuId =
                UUID.randomUUID();

        MenuRequest request =
                new MenuRequest(
                        "Feijoada Updated",
                        "Updated Brazilian food",
                        new BigDecimal("49.90"),
                        false,
                        "https://image.com/updated.jpg",
                        restaurant.getId().toString(),
                        true
                );

        // Act
        mockMvc.perform(
                        put("/menus/{id}", menuId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound());

        // Assert
        assertThat(
                menuRepository.findById(menuId)
        ).isEmpty();
    }


    @Test
    @DisplayName("Should delete menu successfully")
    void shouldDeleteMenuSuccessfully()
            throws Exception {

        // Arrange
        RestaurantEntity restaurant =
                createRestaurant();

        MenuEntity menu =
                menuRepository.save(
                        new MenuEntity(
                                "Feijoada",
                                "Traditional Brazilian feijoada",
                                new BigDecimal("39.90"),
                                true,
                                "https://image.com/feijoada.jpg",
                                restaurant,
                                true
                        )
                );

        UUID menuId =
                menu.getId();

        // Act
        mockMvc.perform(
                        delete("/menus/{id}", menuId)
                )
                .andExpect(status().isNoContent());

        // Assert
        assertThat(
                menuRepository.findById(menuId)
        ).isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when creating menu with invalid fields")
    void shouldReturn422WhenCreateMenuWithInvalidFields()
            throws Exception {

        // Arrange
        MenuRequest request =
                new MenuRequest(
                        "abc",
                        "",
                        BigDecimal.ZERO,
                        false,
                        "",
                        "",
                        false
                );

        // Act
        mockMvc.perform(
                        post("/menus")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isUnprocessableEntity());

        // Assert
        assertThat(menuRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when creating menu with null required fields")
    void shouldReturn422WhenCreateMenuWithNullRequiredFields()
            throws Exception {

        // Arrange
        MenuRequest request =
                new MenuRequest(
                        null,
                        null,
                        null,
                        false,
                        null,
                        null,
                        false
                );

        // Act
        mockMvc.perform(
                        post("/menus")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isUnprocessableEntity());

        // Assert
        assertThat(menuRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when updating menu with invalid fields")
    void shouldReturn422WhenUpdateMenuWithInvalidFields()
            throws Exception {

        // Arrange
        UUID menuId =
                UUID.randomUUID();

        MenuRequest request =
                new MenuRequest(
                        "abc",
                        "",
                        BigDecimal.ZERO,
                        false,
                        "",
                        "",
                        false
                );

        // Act
        mockMvc.perform(
                        put("/menus/{id}", menuId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isUnprocessableEntity());

        // Assert
        assertThat(
                menuRepository.findById(menuId)
        ).isEmpty();
    }


    private RestaurantEntity createRestaurant() {

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

        return restaurantRepository.save(
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
    }
}

