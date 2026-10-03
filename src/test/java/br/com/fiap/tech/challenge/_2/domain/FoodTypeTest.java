package br.com.fiap.tech.challenge._2.domain;


import br.com.fiap.tech.challenge._2.exceptions.ValidationFieldsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class FoodTypeTest {

    @Test
    @DisplayName("Should create FoodType successfully with valid data")
    void shouldCreateFoodTypeSuccessfullyWithValidData() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        FoodType foodType = FoodType.create( id, "Brazilian" );

        // Assert
        assertNotNull(foodType);
        assertEquals(id, foodType.getId());
        assertEquals("Brazilian", foodType.getName());
    }

    @Test
    @DisplayName("Should throw exception when id is null")
    void shouldThrowExceptionWhenIdIsNull() {
        // Arrange
        UUID id = null;

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> FoodType.create( id, "Brazilian" ) );

        // Assert
        assertEquals( "The id is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> FoodType.create( id, null ) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when name is blank")
    void shouldThrowExceptionWhenNameIsBlank() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> FoodType.create( id, " " ) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update FoodType id successfully")
    void shouldUpdateFoodTypeIdSuccessfully() {
        // Arrange
        FoodType foodType = FoodType.create( UUID.randomUUID(), "Brazilian" );
        UUID newId = UUID.randomUUID();

        // Action
        foodType.setId(newId);

        // Assert
        assertEquals(newId, foodType.getId());
        assertEquals("Brazilian", foodType.getName());
    }

    @Test
    @DisplayName("Should throw exception when setting id to null")
    void shouldThrowExceptionWhenSettingIdToNull() {
        // Arrange
        FoodType foodType = FoodType.create( UUID.randomUUID(), "Brazilian" );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> foodType.setId(null) );

        // Assert
        assertEquals( "The id is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update FoodType name successfully")
    void shouldUpdateFoodTypeNameSuccessfully() {
        // Arrange
        FoodType foodType = FoodType.create( UUID.randomUUID(), "Brazilian" );

        // Action
        foodType.setName("Italian");

        // Assert
        assertEquals("Italian", foodType.getName());
    }

    @Test
    @DisplayName("Should throw exception when setting name to null")
    void shouldThrowExceptionWhenSettingNameToNull() {
        // Arrange
        FoodType foodType = FoodType.create( UUID.randomUUID(), "Brazilian" );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> foodType.setName(null) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when setting name to blank")
    void shouldThrowExceptionWhenSettingNameToBlank() {
        // Arrange
        FoodType foodType = FoodType.create( UUID.randomUUID(), "Brazilian" );

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> foodType.setName(" ") );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }












}