package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.domain.exception.ValidationFieldsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Food Type Entity Tests")
class FoodTypeTest {
    private final UUID id = UUID.randomUUID();
    private final String name = "Pizza";

    @Test
    @DisplayName("Should create food type when all fields are valid")
    void shouldCreateFoodTypeWhenAllFieldsAreValid() {
        var foodType = new FoodType(id, name);

        assertThat(foodType.getId()).isEqualTo(id);
        assertThat(foodType.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Should create a food type successfully with static method")
    void shouldCreateFoodTypeSuccessfullyWithStaticMethod() {
        //Arrange
        String name = "Hamburger";
        //Action
        FoodType foodType = FoodType.create(name);
        //Assert
        Assertions.assertThat(foodType).isNotNull();
        Assertions.assertThat(foodType.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Should throw exception when id is null")
    void shouldThrowExceptionWhenIdIsNull() {
        assertThatThrownBy(() -> new FoodType(null, name))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The id is required field");
    }

    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        assertThatThrownBy(() -> new FoodType(id, null))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should throw exception when name is empty")
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThatThrownBy(() -> new FoodType(id, ""))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should throw exception when name contains only spaces")
    void shouldThrowExceptionWhenNameContainsOnlySpaces() {
        assertThatThrownBy(() -> new FoodType(id, "   "))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should update id using setter")
    void shouldUpdateIdUsingSetter() {
        var foodType = new FoodType(id, name);
        var newId = UUID.randomUUID();

        foodType.setId(newId);

        assertThat(foodType.getId()).isEqualTo(newId);
    }

    @Test
    @DisplayName("Should update name using setter")
    void shouldUpdateNameUsingSetter() {
        var foodType = new FoodType(id, name);

        foodType.setName("Burger");

        assertThat(foodType.getName()).isEqualTo("Burger");
    }
}