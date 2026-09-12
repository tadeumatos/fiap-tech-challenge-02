package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.domain.exception.ValidationFieldsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Restaurant Entity Tests")
class RestaurantTest {
    private final UUID id = UUID.randomUUID();
    private final String name = "Pizza Restaurant";
    private final FoodType foodType = new FoodType(
            UUID.randomUUID(),
            "Pizza"
    );
    private final UserType userType = new UserType(
            UUID.randomUUID(),
            "Owner",
            true
    );
    private final LocalTime startTime = LocalTime.of(18, 0);
    private final LocalTime endTime = LocalTime.of(23, 0);
    private final User user = new User(
            UUID.randomUUID(),
            "John Doe",
            "john.doe@email.com",
            userType
    );

    private final String description = "Italian restaurant";

    @Test
    @DisplayName("Should create restaurant when all fields are valid")
    void shouldCreateRestaurantWhenAllFieldsAreValid() {
        var restaurant = new Restaurant(
                id,
                name,
                foodType,
                startTime,
                endTime,
                user,
                description
        );

        assertThat(restaurant.getId()).isEqualTo(id);
        assertThat(restaurant.getName()).isEqualTo(name);
        assertThat(restaurant.getFoodType()).isEqualTo(foodType);
        assertThat(restaurant.getStartTime()).isEqualTo(startTime);
        assertThat(restaurant.getEndTime()).isEqualTo(endTime);
        assertThat(restaurant.getUser()).isEqualTo(user);
        assertThat(restaurant.getDescription()).isEqualTo(description);
    }

    @Test
    @DisplayName("Should throw exception when id is null")
    void shouldThrowExceptionWhenIdIsNull() {
        assertThatThrownBy(() -> new Restaurant(
                null,
                name,
                foodType,
                startTime,
                endTime,
                user,
                description
        ))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The id is required field");
    }

    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        assertThatThrownBy(() -> new Restaurant(
                id,
                null,
                foodType,
                startTime,
                endTime,
                user,
                description
        ))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should throw exception when name is empty")
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThatThrownBy(() -> new Restaurant(
                id,
                "",
                foodType,
                startTime,
                endTime,
                user,
                description
        ))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should throw exception when name contains only spaces")
    void shouldThrowExceptionWhenNameContainsOnlySpaces() {
        assertThatThrownBy(() -> new Restaurant(
                id,
                "   ",
                foodType,
                startTime,
                endTime,
                user,
                description
        ))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should throw exception when food type is null")
    void shouldThrowExceptionWhenFoodTypeIsNull() {
        assertThatThrownBy(() -> new Restaurant(
                id,
                name,
                null,
                startTime,
                endTime,
                user,
                description
        ))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The food type is required field");
    }

    @Test
    @DisplayName("Should throw exception when start time is null")
    void shouldThrowExceptionWhenStartTimeIsNull() {
        assertThatThrownBy(() -> new Restaurant(
                id,
                name,
                foodType,
                null,
                endTime,
                user,
                description
        ))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The start time type is required field");
    }

    @Test
    @DisplayName("Should throw exception when end time is null")
    void shouldThrowExceptionWhenEndTimeIsNull() {
        assertThatThrownBy(() -> new Restaurant(
                id,
                name,
                foodType,
                startTime,
                null,
                user,
                description
        ))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The end time type is required field");
    }

    @Test
    @DisplayName("Should throw exception when user is null")
    void shouldThrowExceptionWhenUserIsNull() {
        assertThatThrownBy(() -> new Restaurant(
                id,
                name,
                foodType,
                startTime,
                endTime,
                null,
                description
        ))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The user is required field");
    }

    @Test
    @DisplayName("Should throw exception when user type is not owner")
    void shouldThrowExceptionWhenUserTypeIsNotOwner() {
        UserType userTypeTest = new UserType(
                UUID.randomUUID(),
                "Customer",
                false
        );

        User userTest = new User(
                UUID.randomUUID(),
                "John Doe",
                "john.doe@email.com",
                userTypeTest
        );

        assertThatThrownBy(() -> new Restaurant(
                id,
                name,
                foodType,
                startTime,
                endTime,
                userTest,
                description
        ))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The user have be owner");
    }

    @Test
    @DisplayName("Should update id using setter")
    void shouldUpdateIdUsingSetter() {
        var restaurant = createRestaurant();
        var newId = UUID.randomUUID();

        restaurant.setId(newId);

        assertThat(restaurant.getId()).isEqualTo(newId);
    }

    @Test
    @DisplayName("Should update name using setter")
    void shouldUpdateNameUsingSetter() {
        var restaurant = createRestaurant();

        restaurant.setName("New Restaurant");

        assertThat(restaurant.getName()).isEqualTo("New Restaurant");
    }

    @Test
    @DisplayName("Should update food type using setter")
    void shouldUpdateFoodTypeUsingSetter() {
        var restaurant = createRestaurant();
        var newFoodType = new FoodType(
                UUID.randomUUID(),
                "Japanese"
        );

        restaurant.setFoodType(newFoodType);

        assertThat(restaurant.getFoodType()).isEqualTo(newFoodType);
    }

    @Test
    @DisplayName("Should update start time using setter")
    void shouldUpdateStartTimeUsingSetter() {
        var restaurant = createRestaurant();
        var newStartTime = LocalTime.of(17, 30);

        restaurant.setStartTime(newStartTime);

        assertThat(restaurant.getStartTime()).isEqualTo(newStartTime);
    }

    @Test
    @DisplayName("Should update end time using setter")
    void shouldUpdateEndTimeUsingSetter() {
        var restaurant = createRestaurant();
        var newEndTime = LocalTime.of(22, 30);

        restaurant.setEndTime(newEndTime);

        assertThat(restaurant.getEndTime()).isEqualTo(newEndTime);
    }

    @Test
    @DisplayName("Should update user using setter")
    void shouldUpdateUserUsingSetter() {
        var restaurant = createRestaurant();
        var newUser = new User(
                UUID.randomUUID(),
                "Jane Doe",
                "jane.doe@email.com",
                userType
        );

        restaurant.setUser(newUser);

        assertThat(restaurant.getUser()).isEqualTo(newUser);
    }

    @Test
    @DisplayName("Should update description using setter")
    void shouldUpdateDescriptionUsingSetter() {
        var restaurant = createRestaurant();

        restaurant.setDescription("New description");

        assertThat(restaurant.getDescription()).isEqualTo("New description");
    }

    @Test
    @DisplayName("Should throw exception when start time is after end time")
    void shouldThrowExceptionWhenStartTimeIsAfterEndTime() {
        assertThatThrownBy(() -> new Restaurant(
                id,
                name,
                foodType,
                LocalTime.of(23, 0),
                LocalTime.of(18, 0),
                user,
                description
        ))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The start time cannot be after the end time");
    }

    private Restaurant createRestaurant() {
        return new Restaurant(
                id,
                name,
                foodType,
                startTime,
                endTime,
                user,
                description
        );
    }
}