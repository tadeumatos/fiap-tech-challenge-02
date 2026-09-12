package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.domain.exception.ValidationFieldsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("User Type Entity Tests")
class UserTypeTest {

    @Test
    @DisplayName("Should create a UserType successfully with a valid name")
    void shouldCreateUserTypeSuccessfully() {
        //Arrange
        String name = "Administrator";
        UUID id = UUID.randomUUID();
        boolean owner = true;
        //Action
        UserType userType = new UserType(id,name, owner);
        //Assert
        assertThat(userType).isNotNull();
        assertThat(userType.getName()).isEqualTo(name);
        assertThat(userType.isOwner()).isTrue();
    }
    @Test
    @DisplayName("Should create a UserType with owner set to false")
    void shouldCreateUserTypeWithOwnerFalse() {
        //Arrange
        String name="User";
        boolean owner=false;
        //Action
        UserType userType = new UserType(UUID.randomUUID(),name, owner);
        //Assert
        assertThat(userType.getName()).isEqualTo("User");
        assertThat(userType.isOwner()).isFalse();
    }
    @Test
    @DisplayName("Should throw ValidationFieldsException when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name=null;
        boolean owner=false;
        //Action and Assert
        assertThatThrownBy(() -> new UserType(id,name,owner))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should throw ValidationFieldsException when name is empty")
    void shouldThrowExceptionWhenNameIsEmpty() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name ="";
        boolean owner=false;
        //Action and Assert
        assertThatThrownBy(() -> new UserType(id,name, owner))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should throw ValidationFieldsException when name contains only spaces")
    void shouldThrowExceptionWhenNameContainsOnlySpaces() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name ="   ";
        boolean owner=false;

        //Action and Assert
        assertThatThrownBy(() -> new UserType(id,name, owner))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should set and get the UserType name")
    void shouldSetAndGetName() {
        //Arrange
        UserType userType = new UserType(UUID.randomUUID(),"Administrator", true);
        //Action
        userType.setName("Manager");
        //Assert
        assertThat(userType.getName()).isEqualTo("Manager");
    }


    @Test
    @DisplayName("Should set and get the owner property")
    void shouldSetAndGetOwner() {
        //Arrange
        UserType userType = new UserType(UUID.randomUUID(),"Administrator", true);
        //Action
        userType.setOwner(false);
        //Assert
        assertThat(userType.isOwner()).isFalse();
    }
    @Test
    @DisplayName("Should set and get id successfully")
    void shouldSetAndGetId() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name ="Administrator";
        boolean owner=true;
        //Action
        UserType userType = new UserType(id, name,owner);
        UUID newId = UUID.randomUUID();
        userType.setId(newId);
        //Assert
        assertThat(userType.getId()).isEqualTo(newId);
    }

    @Test
    @DisplayName("Should throw ValidationFieldsException when id is null")
    void shouldThrowExceptionWhenIdIsNull() {
        //Arrange
        UUID id = null;
        String name=null;
        boolean owner=false;
        //Action and Assert
        assertThatThrownBy(() -> new UserType(id,name,owner))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The id is required field");
    }
}