package br.com.fiap.tech.challenge._2.integration;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.UserTypeEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserRepository;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.UserTypeRepository;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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

class UserControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UserRequest request =
                new UserRequest(
                        "Joao da Silva",
                        "joao@email.com",
                        userType.getId().toString()
                );

        // Act / Assert
        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name")
                        .value("Joao da Silva"))
                .andExpect(jsonPath("$.email")
                        .value("joao@email.com"))
                .andExpect(jsonPath("$.userTypeResponse.id")
                        .value(userType.getId().toString()));

        // Assert
        assertThat(userRepository.findAll())
                .hasSize(1);

        UserEntity user =
                userRepository.findAll().get(0);

        assertThat(user.getId())
                .isNotNull();

        assertThat(user.getName())
                .isEqualTo("Joao da Silva");

        assertThat(user.getEmail())
                .isEqualTo("joao@email.com");

        assertThat(user.getUserTypeEntity())
                .isNotNull();

        assertThat(user.getUserTypeEntity().getId())
                .isEqualTo(userType.getId());
    }


    @Test
    @DisplayName("Should return 409 when creating user with existing email")
    void shouldReturn409WhenCreateUserWithExistingEmail()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        userRepository.save(
                new UserEntity(
                        "Joao da Silva",
                        "joao@email.com",
                        userType
                )
        );

        UserRequest request =
                new UserRequest(
                        "Maria da Silva",
                        "joao@email.com",
                        userType.getId().toString()
                );

        // Act / Assert
        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isConflict())
                .andExpect(
                        jsonPath("$.title")
                                .value("Error rule business")
                );

        // Assert
        assertThat(userRepository.findAll())
                .hasSize(1);
    }


    @Test
    @DisplayName("Should return 404 when creating user with non-existing user type")
    void shouldReturn404WhenCreateUserWithInvalidUserType()
            throws Exception {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserRequest request =
                new UserRequest(
                        "Joao da Silva",
                        "joao@email.com",
                        userTypeId.toString()
                );

        // Act / Assert
        mockMvc.perform(
                        post("/users")
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
        assertThat(userRepository.findAll())
                .isEmpty();
    }



    @Test
    @DisplayName("Should find user by ID successfully")
    void shouldFindUserByIdSuccessfully()
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

        UUID id = user.getId();

        // Act
        mockMvc.perform(
                        get("/users/{id}", id)
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(id.toString())
                )
                .andExpect(
                        jsonPath("$.name")
                                .value("Joao da Silva")
                )
                .andExpect(
                        jsonPath("$.email")
                                .value("joao@email.com")
                )
                .andExpect(
                        jsonPath("$.userTypeResponse.id")
                                .value(userType.getId().toString())
                )
                .andExpect(
                        jsonPath("$.userTypeResponse.name")
                                .value("Administrator")
                )
                .andExpect(
                        jsonPath("$.userTypeResponse.owner")
                                .value(true)
                );

        // Assert
        UserEntity found =
                userRepository.findById(id)
                        .orElseThrow();

        assertThat(found.getName())
                .isEqualTo("Joao da Silva");

        assertThat(found.getEmail())
                .isEqualTo("joao@email.com");

        assertThat(found.getUserTypeEntity().getId())
                .isEqualTo(userType.getId());
    }








    @Test
    @DisplayName("Should return 404 when user does not exist")
    void shouldReturn404WhenUserDoesNotExist()
            throws Exception {

        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        mockMvc.perform(
                        get("/users/{id}", id)
                )
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath("$.title")
                                .value("Resource not found")
                )
                .andExpect(
                        jsonPath("$.detail")
                                .value("Register not found")
                );

        // Assert
        assertThat(userRepository.findById(id))
                .isEmpty();
    }


    @Test
    @DisplayName("Should return all users successfully")
    void shouldReturnAllUsersSuccessfully()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        userRepository.save(
                new UserEntity(
                        "Joao da Silva",
                        "joao@email.com",
                        userType
                )
        );

        userRepository.save(
                new UserEntity(
                        "Maria da Silva",
                        "maria@email.com",
                        userType
                )
        );

        // Act / Assert
        mockMvc.perform(
                        get("/users")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(2)
                )
                .andExpect(
                        jsonPath("$[0].name")
                                .value("Joao da Silva")
                )
                .andExpect(
                        jsonPath("$[0].email")
                                .value("joao@email.com")
                )
                .andExpect(
                        jsonPath("$[1].name")
                                .value("Maria da Silva")
                )
                .andExpect(
                        jsonPath("$[1].email")
                                .value("maria@email.com")
                );
    }


    @Test
    @DisplayName("Should return an empty list when no users exist")
    void shouldReturnEmptyListWhenNoUsersExist()
            throws Exception {

        // Arrange
        // Database is cleaned before each test.

        // Act / Assert
        mockMvc.perform(
                        get("/users")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(0)
                );
    }


    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserSuccessfully()
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

        UUID id = user.getId();

        UserRequest request =
                new UserRequest(
                        "Joao da Silva Updated",
                        "joao.updated@email.com",
                        userType.getId().toString()
                );

        // Act / Assert
        mockMvc.perform(
                        put("/users/{id}", id)
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
                                .value("Joao da Silva Updated")
                )
                .andExpect(
                        jsonPath("$.email")
                                .value("joao.updated@email.com")
                );

        // Assert
        UserEntity updated =
                userRepository.findById(id)
                        .orElseThrow();

        assertThat(updated.getName())
                .isEqualTo("Joao da Silva Updated");

        assertThat(updated.getEmail())
                .isEqualTo("joao.updated@email.com");

        assertThat(updated.getUserTypeEntity().getId())
                .isEqualTo(userType.getId());
    }


    @Test
    @DisplayName("Should allow updating user keeping its own email")
    void shouldAllowUpdateWithOwnEmail()
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

        UUID id = user.getId();

        UserRequest request =
                new UserRequest(
                        "Joao da Silva Updated",
                        "joao@email.com",
                        userType.getId().toString()
                );

        // Act / Assert
        mockMvc.perform(
                        put("/users/{id}", id)
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
                                .value("Joao da Silva Updated")
                )
                .andExpect(
                        jsonPath("$.email")
                                .value("joao@email.com")
                );

        // Assert
        UserEntity updated =
                userRepository.findById(id)
                        .orElseThrow();

        assertThat(updated.getName())
                .isEqualTo("Joao da Silva Updated");

        assertThat(updated.getEmail())
                .isEqualTo("joao@email.com");
    }


    @Test
    @DisplayName("Should return 409 when updating user with another user's email")
    void shouldReturn409WhenUpdateWithEmailFromAnotherUser()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UserEntity user1 =
                userRepository.save(
                        new UserEntity(
                                "Joao da Silva",
                                "joao@email.com",
                                userType
                        )
                );

        userRepository.save(
                new UserEntity(
                        "Maria da Silva",
                        "maria@email.com",
                        userType
                )
        );

        UUID id = user1.getId();

        UserRequest request =
                new UserRequest(
                        "Joao Updated",
                        "maria@email.com",
                        userType.getId().toString()
                );

        // Act / Assert
        mockMvc.perform(
                        put("/users/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isConflict())
                .andExpect(
                        jsonPath("$.title")
                                .value("Error rule business")
                );

        // Assert
        UserEntity unchanged =
                userRepository.findById(id)
                        .orElseThrow();

        assertThat(unchanged.getName())
                .isEqualTo("Joao da Silva");

        assertThat(unchanged.getEmail())
                .isEqualTo("joao@email.com");
    }


    @Test
    @DisplayName("Should return 404 when updating user with non-existing user type")
    void shouldReturn404WhenUpdateWithInvalidUserType()
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

        UUID id = user.getId();

        UUID invalidUserTypeId =
                UUID.randomUUID();

        UserRequest request =
                new UserRequest(
                        "Joao Updated",
                        "joao.novo@email.com",
                        invalidUserTypeId.toString()
                );

        // Act / Assert
        mockMvc.perform(
                        put("/users/{id}", id)
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
        UserEntity unchanged =
                userRepository.findById(id)
                        .orElseThrow();

        assertThat(unchanged.getName())
                .isEqualTo("Joao da Silva");

        assertThat(unchanged.getEmail())
                .isEqualTo("joao@email.com");

        assertThat(
                unchanged.getUserTypeEntity().getId()
        ).isEqualTo(userType.getId());
    }


    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUserSuccessfully()
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

        UUID id = user.getId();

        // Act / Assert
        mockMvc.perform(
                        delete("/users/{id}", id)
                )
                .andExpect(status().isNoContent());

        // Assert
        assertThat(
                userRepository.findById(id)
        ).isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when creating user with invalid fields")
    void shouldReturn422WhenCreatingUserWithInvalidFields()
            throws Exception {

        // Arrange
        UserTypeEntity userType =
                userTypeRepository.save(
                        new UserTypeEntity(
                                "Administrator",
                                true
                        )
                );

        UserRequest request =
                new UserRequest(
                        "",
                        "",
                        userType.getId().toString()
                );

        // Act / Assert
        mockMvc.perform(
                        post("/users")
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
        assertThat(userRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when creating user with null fields")
    void shouldReturn422WhenCreatingUserWithNullFields()
            throws Exception {

        // Arrange
        String request = """
                {
                    "name": null,
                    "email": null,
                    "userTypeId": null
                }
                """;

        // Act / Assert
        mockMvc.perform(
                        post("/users")
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
        assertThat(userRepository.findAll())
                .isEmpty();
    }


    @Test
    @DisplayName("Should return 422 when updating user with invalid fields")
    void shouldReturn422WhenUpdatingUserWithInvalidFields()
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

        UUID id = user.getId();

        UserRequest request =
                new UserRequest(
                        "",
                        "",
                        userType.getId().toString()
                );

        // Act / Assert
        mockMvc.perform(
                        put("/users/{id}", id)
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
        UserEntity unchanged =
                userRepository.findById(id)
                        .orElseThrow();

        assertThat(unchanged.getName())
                .isEqualTo("Joao da Silva");

        assertThat(unchanged.getEmail())
                .isEqualTo("joao@email.com");
    }
}

