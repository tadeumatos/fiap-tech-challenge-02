package br.com.fiap.tech.challenge._2.integration;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserTypeEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserTypeRepository;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserTypeRequest;
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

class UserTypeControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Test
    @DisplayName("Should create user type successfully")
    void shouldCreateUserTypeSuccessfully() throws Exception {

        // Arrange
        UserTypeRequest request =
                new UserTypeRequest(
                        "Administrator",
                        true
                );

        // Act / Assert
        mockMvc.perform(
                        post("/usertypes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name")
                        .value("Administrator"))
                .andExpect(jsonPath("$.owner")
                        .value(true));

        // Assert
        assertThat(userTypeRepository.findAll())
                .hasSize(1);

        UserTypeEntity userType =
                userTypeRepository.findAll().get(0);

        assertThat(userType.getId())
                .isNotNull();

        assertThat(userType.getName())
                .isEqualTo("Administrator");

        assertThat(userType.isOwner())
                .isTrue();
    }


    @Test
    @DisplayName("Should find user type by ID successfully")
    void shouldFindUserTypeByIdSuccessfully() throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UUID id = userType.getId();

        // Act / Assert
        mockMvc.perform(
                        get("/usertypes/{id}", id)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(id.toString()))
                .andExpect(jsonPath("$.name")
                        .value("Administrator"))
                .andExpect(jsonPath("$.owner")
                        .value(true));
    }


    @Test
    @DisplayName("Should return 404 when user type does not exist")
    void shouldReturn404WhenUserTypeDoesNotExist()
            throws Exception {

        // Arrange
        UUID id = UUID.randomUUID();

        // Act / Assert
        mockMvc.perform(
                        get("/usertypes/{id}", id)
                )
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath("$.title")
                                .value("Resource not found")
                )
                .andExpect(
                        jsonPath("$.detail")
                                .value("User Type not found")
                );
    }


    @Test
    @DisplayName("Should return all user types successfully")
    void shouldReturnAllUserTypesSuccessfully()
            throws Exception {

        // Arrange
        userTypeRepository.save(
                new UserTypeEntity(
                        "Administrator",
                        true
                )
        );

        userTypeRepository.save(
                new UserTypeEntity(
                        "Customer",
                        false
                )
        );

        // Act / Assert
        mockMvc.perform(
                        get("/usertypes")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(2))
                .andExpect(jsonPath("$[0].name")
                        .value("Administrator"))
                .andExpect(jsonPath("$[0].owner")
                        .value(true))
                .andExpect(jsonPath("$[1].name")
                        .value("Customer"))
                .andExpect(jsonPath("$[1].owner")
                        .value(false));
    }


    @Test
    @DisplayName("Should return an empty list when no user types exist")
    void shouldReturnEmptyListWhenNoUserTypesExist()
            throws Exception {

        // Arrange
        // Database is cleaned before each test.

        // Act / Assert
        mockMvc.perform(
                        get("/usertypes")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(0));
    }


    @Test
    @DisplayName("Should update user type successfully")
    void shouldUpdateUserTypeSuccessfully()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UUID id = userType.getId();

        UserTypeRequest request =
                new UserTypeRequest(
                        "Manager",
                        false
                );

        // Act / Assert
        mockMvc.perform(
                        put("/usertypes/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(id.toString()))
                .andExpect(jsonPath("$.name")
                        .value("Manager"))
                .andExpect(jsonPath("$.owner")
                        .value(false));

        // Assert
        UserTypeEntity updated =
                userTypeRepository.findById(id)
                        .orElseThrow();

        assertThat(updated.getId())
                .isEqualTo(id);

        assertThat(updated.getName())
                .isEqualTo("Manager");

        assertThat(updated.isOwner())
                .isFalse();
    }


    @Test
    @DisplayName("Should return 404 when updating a non-existing user type")
    void shouldReturn404WhenUpdatingNonExistingUserType()
            throws Exception {

        // Arrange
        UUID id = UUID.randomUUID();

        UserTypeRequest request =
                new UserTypeRequest(
                        "Manager",
                        false
                );

        // Act / Assert
        mockMvc.perform(
                        put("/usertypes/{id}", id)
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
        assertThat(userTypeRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should delete user type successfully")
    void shouldDeleteUserTypeSuccessfully()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UUID id = userType.getId();

        // Act / Assert
        mockMvc.perform(
                        delete("/usertypes/{id}", id)
                )
                .andExpect(status().isNoContent());

        // Assert
        assertThat(
                userTypeRepository.findById(id)
        ).isEmpty();
    }


    @Test
    @DisplayName("Should return 404 when deleting a non-existing user type")
    void shouldReturn404WhenDeletingNonExistingUserType()
            throws Exception {

        // Arrange
        UUID id = UUID.randomUUID();

        // Act / Assert
        mockMvc.perform(
                        delete("/usertypes/{id}", id)
                )
                .andExpect(status().isNotFound());

        // Assert
        assertThat(userTypeRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when creating user type with blank name")
    void shouldReturn422WhenCreatingUserTypeWithBlankName()
            throws Exception {

        // Arrange
        UserTypeRequest request =
                new UserTypeRequest(
                        "",
                        true
                );

        // Act / Assert
        mockMvc.perform(
                        post("/usertypes")
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
        assertThat(userTypeRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when creating user type with null name")
    void shouldReturn422WhenCreatingUserTypeWithNullName()
            throws Exception {

        // Arrange
        String request = """
                {
                    "name": null,
                    "owner": true
                }
                """;

        // Act / Assert
        mockMvc.perform(
                        post("/usertypes")
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
        assertThat(userTypeRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when updating user type with blank name")
    void shouldReturn422WhenUpdatingUserTypeWithBlankName()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UUID id = userType.getId();

        UserTypeRequest request =
                new UserTypeRequest(
                        "",
                        false
                );

        // Act / Assert
        mockMvc.perform(
                        put("/usertypes/{id}", id)
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
        UserTypeEntity unchanged =
                userTypeRepository.findById(id)
                        .orElseThrow();

        assertThat(unchanged.getName())
                .isEqualTo("Administrator");

        assertThat(unchanged.isOwner())
                .isTrue();
    }
}

