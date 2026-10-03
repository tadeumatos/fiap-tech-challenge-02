package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.exceptions.ValidationFieldsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;

class UserTypeTest {

    @Test
    @DisplayName("Should create UserType successfully with valid data")
    void shouldCreateUserTypeSuccessfullyWithValidData() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        UserType userType = UserType.create( id, "Administrator", true );

        // Assert
        assertNotNull(userType);
        assertEquals(id, userType.getId());
        assertEquals("Administrator", userType.getName());
        assertTrue(userType.isOwner());
    }

    @Test
    @DisplayName("Should create UserType successfully when owner is false")
    void shouldCreateUserTypeSuccessfullyWhenOwnerIsFalse() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        UserType userType = UserType.create( id, "Customer", false );

        // Assert
        assertNotNull(userType);
        assertEquals(id, userType.getId());
        assertEquals("Customer", userType.getName());
        assertFalse(userType.isOwner());
    }

    @Test
    @DisplayName("Should throw exception when id is null")
    void shouldThrowExceptionWhenIdIsNull() {
        // Arrange
        UUID id = null;

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> UserType.create( id, "Administrator", true ) );

        // Assert
        assertEquals( "The id is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> UserType.create( id, null, true ) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when name is blank")
    void shouldThrowExceptionWhenNameIsBlank() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> UserType.create( id, " ", true ) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update UserType id successfully")
    void shouldUpdateUserTypeIdSuccessfully() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );
        UUID newId = UUID.randomUUID();

        // Action
        userType.setId(newId);

        // Assert
        assertEquals(newId, userType.getId());
        assertEquals("Administrator", userType.getName());
        assertTrue(userType.isOwner());
    }

    @Test
    @DisplayName("Should throw exception when setting id to null")
    void shouldThrowExceptionWhenSettingIdToNull() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> userType.setId(null) );

        // Assert
        assertEquals( "The id is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update UserType name successfully")
    void shouldUpdateUserTypeNameSuccessfully() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );

        // Action
        userType.setName("Manager");

        // Assert
        assertEquals("Manager", userType.getName());
        assertTrue(userType.isOwner());
    }

    @Test
    @DisplayName("Should throw exception when setting name to null")
    void shouldThrowExceptionWhenSettingNameToNull() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> userType.setName(null) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when setting name to blank")
    void shouldThrowExceptionWhenSettingNameToBlank() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> userType.setName(" ") );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update owner successfully")
    void shouldUpdateOwnerSuccessfully() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );

        // Action
        userType.setOwner(false);

        // Assert
        assertFalse(userType.isOwner());
    }

    @Test
    @DisplayName("Should update owner from false to true successfully")
    void shouldUpdateOwnerFromFalseToTrueSuccessfully() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Customer", false );

        // Action
        userType.setOwner(true);

        // Assert
        assertTrue(userType.isOwner());
    }

}