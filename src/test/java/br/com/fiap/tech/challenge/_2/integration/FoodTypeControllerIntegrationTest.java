package br.com.fiap.tech.challenge._2.integration;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.FoodTypeEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.FoodTypeRepository;
import br.com.fiap.tech.challenge._2.presentation.controller.request.FoodTypeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class FoodTypeControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FoodTypeRepository foodTypeRepository;

    @Test
    @DisplayName("Should create food type successfully")
    void shouldCreateFoodTypeSuccessfully() throws Exception {

        // Arrange
        FoodTypeRequest request = new FoodTypeRequest(
                "Brazilian"
        );

        // Act / Assert
        mockMvc.perform(
                        post("/foodtypes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Brazilian"));

        // Assert
        assertThat(foodTypeRepository.findAll())
                .hasSize(1);

        FoodTypeEntity foodType =
                foodTypeRepository.findAll().get(0);

        assertThat(foodType.getName())
                .isEqualTo("Brazilian");

        assertThat(foodType.getId())
                .isNotNull();
    }


    @Test
    @DisplayName("Should find food type by ID successfully")
    void shouldFindFoodTypeByIdSuccessfully() throws Exception {

        // Arrange
        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity("Brazilian")
                );

        UUID id = foodType.getId();

        // Act / Assert
        mockMvc.perform(
                        get("/foodtypes/{id}", id)
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(id.toString())
                )
                .andExpect(
                        jsonPath("$.name")
                                .value("Brazilian")
                );
    }


    @Test
    @DisplayName("Should return 404 when food type does not exist")
    void shouldReturn404WhenFoodTypeDoesNotExist()
            throws Exception {

        // Arrange
        UUID id = UUID.randomUUID();

        // Act / Assert
        mockMvc.perform(
                        get("/foodtypes/{id}", id)
                )
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath("$.title")
                                .value("Resource not found")
                )
                .andExpect(
                        jsonPath("$.detail")
                                .value("Food Type not found")
                );
    }


    @Test
    @DisplayName("Should return all food types successfully")
    void shouldReturnAllFoodTypesSuccessfully()
            throws Exception {

        // Arrange
        foodTypeRepository.save(
                new FoodTypeEntity("Brazilian")
        );

        foodTypeRepository.save(
                new FoodTypeEntity("Italian")
        );

        // Act / Assert
        mockMvc.perform(
                        get("/foodtypes")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(2)
                )
                .andExpect(
                        jsonPath("$[0].name")
                                .value("Brazilian")
                )
                .andExpect(
                        jsonPath("$[1].name")
                                .value("Italian")
                );
    }


    @Test
    @DisplayName("Should return an empty list when no food types exist")
    void shouldReturnEmptyListWhenNoFoodTypesExist()
            throws Exception {

        // Arrange
        // Database is cleaned before each test.

        // Act / Assert
        mockMvc.perform(
                        get("/foodtypes")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(0)
                );
    }


    @Test
    @DisplayName("Should update food type successfully")
    void shouldUpdateFoodTypeSuccessfully()
            throws Exception {

        // Arrange
        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity("Brazilian")
                );

        UUID id = foodType.getId();

        FoodTypeRequest request =
                new FoodTypeRequest(
                        "Japanese"
                );

        // Act / Assert
        mockMvc.perform(
                        put("/foodtypes/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(id.toString())
                )
                .andExpect(
                        jsonPath("$.name")
                                .value("Japanese")
                );

        // Assert
        FoodTypeEntity updated =
                foodTypeRepository.findById(id)
                        .orElseThrow();

        assertThat(updated.getName())
                .isEqualTo("Japanese");

        assertThat(updated.getId())
                .isEqualTo(id);
    }


    @Test
    @DisplayName("Should return 404 when updating a non-existing food type")
    void shouldReturn404WhenUpdatingNonExistingFoodType()
            throws Exception {

        // Arrange
        UUID id = UUID.randomUUID();

        FoodTypeRequest request =
                new FoodTypeRequest(
                        "Japanese"
                );

        // Act / Assert
        mockMvc.perform(
                        put("/foodtypes/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath("$.title")
                                .value("Resource not found")
                );

        // Assert
        assertThat(foodTypeRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should delete food type successfully")
    void shouldDeleteFoodTypeSuccessfully()
            throws Exception {

        // Arrange
        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity("Brazilian")
                );

        UUID id = foodType.getId();

        // Act / Assert
        mockMvc.perform(
                        delete("/foodtypes/{id}", id)
                )
                .andExpect(status().isNoContent());

        // Assert
        assertThat(
                foodTypeRepository.findById(id)
        ).isEmpty();
    }


    @Test
    @DisplayName("Should return 404 when deleting a non-existing food type")
    void shouldReturn404WhenDeletingNonExistingFoodType()
            throws Exception {

        // Arrange
        UUID id = UUID.randomUUID();

        // Act / Assert
        mockMvc.perform(
                        delete("/foodtypes/{id}", id)
                )
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath("$.title")
                                .value("Resource not found")
                );

        // Assert
        assertThat(foodTypeRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when creating food type with blank name")
    void shouldReturn422WhenCreatingFoodTypeWithBlankName()
            throws Exception {

        // Arrange
        FoodTypeRequest request =
                new FoodTypeRequest(
                        ""
                );

        // Act / Assert
        mockMvc.perform(
                        post("/foodtypes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(
                        jsonPath("$.title")
                                .value("Validation")
                )
                .andExpect(
                        jsonPath("$.errors")
                                .exists()
                );

        // Assert
        assertThat(foodTypeRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when creating food type with null name")
    void shouldReturn422WhenCreatingFoodTypeWithNullName()
            throws Exception {

        // Arrange
        String request = """
                {
                    "name": null
                }
                """;

        // Act / Assert
        mockMvc.perform(
                        post("/foodtypes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(
                        jsonPath("$.title")
                                .value("Validation")
                )
                .andExpect(
                        jsonPath("$.errors")
                                .exists()
                );

        // Assert
        assertThat(foodTypeRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when updating food type with blank name")
    void shouldReturn422WhenUpdatingFoodTypeWithBlankName()
            throws Exception {

        // Arrange
        FoodTypeEntity foodType =
                foodTypeRepository.save(
                        new FoodTypeEntity("Brazilian")
                );

        UUID id = foodType.getId();

        FoodTypeRequest request =
                new FoodTypeRequest(
                        ""
                );

        // Act / Assert
        mockMvc.perform(
                        put("/foodtypes/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(
                        jsonPath("$.title")
                                .value("Validation")
                )
                .andExpect(
                        jsonPath("$.errors")
                                .exists()
                );

        // Assert
        FoodTypeEntity unchanged =
                foodTypeRepository.findById(id)
                        .orElseThrow();

        assertThat(unchanged.getName())
                .isEqualTo("Brazilian");
    }
}

