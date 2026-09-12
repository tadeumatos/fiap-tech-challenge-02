package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.domain.exception.ValidationFieldsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Entity Tests")
class UserTest {

    @Test
    @DisplayName("Should create user when all fields are valid")
    void shouldCreateUserWhenAllFieldsAreValid() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@email.com";
        UserType userType = new UserType(UUID.randomUUID(),"Owner only",true);
        //Action
        var user = new User(id, name, email, userType);
        //Assert
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getUserType()).isEqualTo(userType);
    }

    @Test
    @DisplayName("Should throw exception when id is null")
    void shouldThrowExceptionWhenIdIsNull() {
        //Arrange
        UUID id = null;
        String name = "John Doe";
        String email = "john.doe@email.com";
        UserType userType = new UserType(UUID.randomUUID(),"Owner only",true);

        //Action and Assert
        assertThatThrownBy(() -> new User(id, name, email, userType))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The id is required field");
    }

    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = null;
        String email = "john.doe@email.com";
        UserType userType = new UserType(UUID.randomUUID(),"Owner only",true);

        //Action and Assert
        assertThatThrownBy(() -> new User(id, name, email, userType))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should throw exception when name is empty")
    void shouldThrowExceptionWhenNameIsEmpty() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "";
        String email = "john.doe@email.com";
        UserType userType = new UserType(UUID.randomUUID(),"Owner only",true);
        //Action and Assert
        assertThatThrownBy(() -> new User(id, name, email, userType))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should throw exception when name contains only spaces")
    void shouldThrowExceptionWhenNameContainsOnlySpaces() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "  ";
        String email = "john.doe@email.com";
        UserType userType = new UserType(UUID.randomUUID(),"Owner only",true);
        //Action and Assert
        assertThatThrownBy(() -> new User(id, name, email, userType))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should throw exception when email is null")
    void shouldThrowExceptionWhenEmailIsNull() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = null;
        UserType userType = new UserType(UUID.randomUUID(),"Owner only",true);
        //Action and Assert
        assertThatThrownBy(() -> new User(id, name, email, userType))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The email is required field");
    }

    @Test
    @DisplayName("Should throw exception when email is empty")
    void shouldThrowExceptionWhenEmailIsEmpty() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "";
        UserType userType = new UserType(UUID.randomUUID(),"Owner only",true);
        //Action and Assert
        assertThatThrownBy(() -> new User(id, name, email, userType))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The email is required field");
    }

    @Test
    @DisplayName("Should throw exception when email contains only spaces")
    void shouldThrowExceptionWhenEmailContainsOnlySpaces() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "  ";
        UserType userType = new UserType(UUID.randomUUID(),"Owner only",true);
        //Action and Assert
        assertThatThrownBy(() -> new User(id, name, email, userType))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The email is required field");
    }

    @Test
    @DisplayName("Should throw exception when user type is null")
    void shouldThrowExceptionWhenUserTypeIsNull() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@email.com";
        UserType userType = null;
        //Action and Assert
        assertThatThrownBy(() -> new User(id, name, email, userType))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The user type is required field");
    }

    @Test
    @DisplayName("Should update id using setter")
    void shouldUpdateIdUsingSetter() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@email.com";
        UserType userType = new UserType(UUID.randomUUID(),"Owner only",true);
        //Action
        var user = new User(id, name, email, userType);
        var newId = UUID.randomUUID();

        user.setId(newId);
        //Assert
        assertThat(user.getId()).isEqualTo(newId);
    }

    @Test
    @DisplayName("Should update name using setter")
    void shouldUpdateNameUsingSetter() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@email.com";
        UserType userType = new UserType(UUID.randomUUID(),"Owner only",true);
        //Action
        var user = new User(id, name, email, userType);

        user.setName("Jane Doe");
        //Assert
        assertThat(user.getName()).isEqualTo("Jane Doe");
    }

    @Test
    @DisplayName("Should update email using setter")
    void shouldUpdateEmailUsingSetter() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@email.com";
        UserType userType = new UserType(UUID.randomUUID(),"Owner only",true);
        //Action
        var user = new User(id, name, email, userType);

        user.setEmail("jane.doe@email.com");
        //Assert
        assertThat(user.getEmail()).isEqualTo("jane.doe@email.com");
    }

    @Test
    @DisplayName("Should update user type using setter")
    void shouldUpdateUserTypeUsingSetter() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@email.com";
        UserType userType = new UserType(UUID.randomUUID(),"Owner only",true);
        UserType userTypeTest = new UserType(UUID.randomUUID(),"user only",false);
        //Action
        var user = new User(id, name, email, userType);

        user.setUserType(userTypeTest);
        //Assert
        assertThat(userTypeTest.equals(user.getUserType()));
    }

}