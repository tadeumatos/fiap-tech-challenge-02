package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.exceptions.ValidationFieldsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class UserTest {

    @Test
    @DisplayName("Should create User successfully with valid data")
    void shouldCreateUserSuccessfullyWithValidData() {
        // Arrange
        UUID id = UUID.randomUUID();
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );

        // Action
        User user = User.create( id, "John Silva", "john@email.com", userType );

        // Assert
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals("John Silva", user.getName());
        assertEquals("john@email.com", user.getEmail());
        assertEquals(userType, user.getUserType());
    }

    @Test
    @DisplayName("Should throw exception when id is null")
    void shouldThrowExceptionWhenIdIsNull() {
        // Arrange
        UUID id = null;
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> User.create( id, "John Silva", "john@email.com", userType ) );

        // Assert
        assertEquals( "The id is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        // Arrange
        UUID id = UUID.randomUUID();
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> User.create( id, null, "john@email.com", userType ) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when name is blank")
    void shouldThrowExceptionWhenNameIsBlank() {
        // Arrange
        UUID id = UUID.randomUUID();
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> User.create( id, " ", "john@email.com", userType ) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when email is null")
    void shouldThrowExceptionWhenEmailIsNull() {
        // Arrange
        UUID id = UUID.randomUUID();
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> User.create( id, "John Silva", null, userType ) );

        // Assert
        assertEquals( "The email is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when email is blank")
    void shouldThrowExceptionWhenEmailIsBlank() {
        // Arrange
        UUID id = UUID.randomUUID();
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );

        // Act
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> User.create( id, "John Silva", " ", userType ) );

        // Assert
        assertEquals( "The email is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when user type is null")
    void shouldThrowExceptionWhenUserTypeIsNull() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> User.create( id, "John Silva", "john@email.com", null ) );

        // Assert
        assertEquals( "The user type is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update User id successfully")
    void shouldUpdateUserIdSuccessfully() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );
        User user = User.create( UUID.randomUUID(), "John Silva", "john@email.com", userType ); UUID newId = UUID.randomUUID();

        // Action
        user.setId(newId);

        // Assert
        assertEquals(newId, user.getId());
        assertEquals("John Silva", user.getName());
        assertEquals("john@email.com", user.getEmail());
        assertEquals(userType, user.getUserType());
    }

    @Test
    @DisplayName("Should throw exception when setting id to null")
    void shouldThrowExceptionWhenSettingIdToNull() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );
        User user = User.create( UUID.randomUUID(), "John Silva", "john@email.com", userType );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> user.setId(null) );

        // Assert
        assertEquals( "The id is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update User name successfully")
    void shouldUpdateUserNameSuccessfully() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );
        User user = User.create( UUID.randomUUID(), "John Silva", "john@email.com", userType );

        // Action
        user.setName("Michael Silva");

        // Assert
        assertEquals("Michael Silva", user.getName());
    }

    @Test
    @DisplayName("Should throw exception when setting name to null")
    void shouldThrowExceptionWhenSettingNameToNull() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );
        User user = User.create( UUID.randomUUID(), "John Silva", "john@email.com", userType );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> user.setName(null) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when setting name to blank")
    void shouldThrowExceptionWhenSettingNameToBlank() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );
        User user = User.create( UUID.randomUUID(), "John Silva", "john@email.com", userType );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> user.setName(" ") );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update User email successfully")
    void shouldUpdateUserEmailSuccessfully() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );
        User user = User.create( UUID.randomUUID(), "John Silva", "john@email.com", userType );

        // Action
        user.setEmail("new.email@email.com");

        // Assert
        assertEquals( "new.email@email.com", user.getEmail() );
    }

    @Test
    @DisplayName("Should throw exception when setting email to null")
    void shouldThrowExceptionWhenSettingEmailToNull() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );
        User user = User.create( UUID.randomUUID(), "John Silva", "john@email.com", userType );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> user.setEmail(null) );

        // Assert
        assertEquals( "The email is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when setting email to blank")
    void shouldThrowExceptionWhenSettingEmailToBlank() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );
        User user = User.create( UUID.randomUUID(), "John Silva", "john@email.com", userType );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> user.setEmail(" ") );

        // Assert
        assertEquals( "The email is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update User type successfully")
    void shouldUpdateUserTypeSuccessfully() {
        // Arrange
        UserType firstUserType = UserType.create( UUID.randomUUID(), "Administrator", true );
        UserType secondUserType = UserType.create( UUID.randomUUID(), "Customer", false );
        User user = User.create( UUID.randomUUID(), "John Silva", "john@email.com", firstUserType );

        // Action
        user.setUserType(secondUserType);

        // Assert
        assertEquals(secondUserType, user.getUserType());
    }

    @Test
    @DisplayName("Should throw exception when setting user type to null")
    void shouldThrowExceptionWhenSettingUserTypeToNull() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );
        User user = User.create( UUID.randomUUID(), "John Silva", "john@email.com", userType );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> user.setUserType(null) );

        // Assert
        assertEquals( "The user type is required field", exception.getMessage() );
    }

}