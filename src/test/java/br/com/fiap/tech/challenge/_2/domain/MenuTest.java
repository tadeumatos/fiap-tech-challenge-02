package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.domain.exception.ValidationFieldsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Menu Entity Tests")
class MenuTest {
    @Test
    @DisplayName("Should create a menu with the provided data")
    void shouldCreateMenuWithProvidedData() {
        // Arrange
        UUID id = UUID.randomUUID();
        String name = "Pizza";
        String description = "Calabrese pizza";
        FoodType foodType =  new FoodType(UUID.randomUUID(),"Test");
        UserType userType = new UserType(UUID.randomUUID(),"Customer Test",true);

        User user = new User(UUID.randomUUID(),"Jonh Doe","test@gmail.com",userType);

        Restaurant restaurant = new Restaurant(UUID.randomUUID(),"Restaurant Test",foodType,LocalTime.of(8,0,0), LocalTime.of(23,0,0),user,"Restaurant Test");
        boolean active = true;

        // Act
        Menu menu = new Menu(
                id,
                name,
                description,
                restaurant,
                active
        );

        // Assert
        assertThat(menu.getId()).isEqualTo(id);
        assertThat(menu.getName()).isEqualTo(name);
        assertThat(menu.getDescription()).isEqualTo(description);
        assertThat(menu.getRestaurant()).isEqualTo(restaurant);
        assertThat(menu.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should update the menu ID")
    void shouldUpdateMenuId() {
        // Arrange
        Menu menu = createMenu();
        UUID newId = UUID.randomUUID();

        // Act
        menu.setId(newId);

        // Assert
        assertThat(menu.getId()).isEqualTo(newId);
    }

    @Test
    @DisplayName("Should update the menu name")
    void shouldUpdateMenuName() {
        // Arrange
        Menu menu = createMenu();

        // Act
        menu.setName("New Name");

        // Assert
        assertThat(menu.getName()).isEqualTo("New Name");
    }

    @Test
    @DisplayName("Should update the menu description")
    void shouldUpdateMenuDescription() {
        // Arrange
        Menu menu = createMenu();

        // Act
        menu.setDescription("New Description");

        // Assert
        assertThat(menu.getDescription()).isEqualTo("New Description");
    }

    @Test
    @DisplayName("Should update the menu restaurant")
    void shouldUpdateMenuRestaurant() {
        // Arrange
        Menu menu = createMenu();
        Restaurant newRestaurant = createRestaurant();

        // Act
        menu.setRestaurant(newRestaurant);

        // Assert
        assertThat(menu.getRestaurant()).isEqualTo(newRestaurant);
    }

    @Test
    @DisplayName("Should activate the menu")
    void shouldActivateMenu() {
        // Arrange
        Menu menu = createMenu();

        // Act
        menu.setActive(true);

        // Assert
        assertThat(menu.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should deactivate the menu")
    void shouldDeactivateMenu() {
        // Arrange
        Menu menu = createMenu();

        // Act
        menu.setActive(false);

        // Assert
        assertThat(menu.isActive()).isFalse();
    }

    @Test
    @DisplayName("Should throw exception when id is null")
    void shouldThrowExceptionWhenIdIsNull() {
        //Arrange
        UUID id = null;
        String name = "Pizza";
        String description = "Calabrese pizza";
        FoodType foodType =  new FoodType(UUID.randomUUID(),"Test");
        UserType userType = new UserType(UUID.randomUUID(),"Customer Test",true);

        User user = new User(UUID.randomUUID(),"Jonh Doe","test@gmail.com",userType);

        Restaurant restaurant = new Restaurant(UUID.randomUUID(),"Restaurant Test",foodType,LocalTime.of(8,0,0), LocalTime.of(23,0,0),user,"Restaurant Test");
        boolean active = true;

        //Action and Assert
        assertThatThrownBy(() -> new Menu(id, name, description, restaurant,active))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The id is required field");
    }

    @Test
    @DisplayName("Should throw exception when description is null")
    void shouldThrowExceptionWhenDescriptionsNull() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "Pizza";
        String description = null;
        FoodType foodType =  new FoodType(UUID.randomUUID(),"Test");
        UserType userType = new UserType(UUID.randomUUID(),"Customer Test",true);

        User user = new User(UUID.randomUUID(),"Jonh Doe","test@gmail.com",userType);

        Restaurant restaurant = new Restaurant(UUID.randomUUID(),"Restaurant Test",foodType,LocalTime.of(8,0,0), LocalTime.of(23,0,0),user,"Restaurant Test");
        boolean active = true;

        //Action and Assert
        assertThatThrownBy(() -> new Menu(id, name, description, restaurant,active))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The description is required field");
    }

    @Test
    @DisplayName("Should throw exception when description contains only spaces")
    void shouldThrowExceptionWhenDescriptionContainsOnlySpaces() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "Pizza";
        String description = "    ";
        FoodType foodType =  new FoodType(UUID.randomUUID(),"Test");
        UserType userType = new UserType(UUID.randomUUID(),"Customer Test",true);

        User user = new User(UUID.randomUUID(),"Jonh Doe","test@gmail.com",userType);

        Restaurant restaurant = new Restaurant(UUID.randomUUID(),"Restaurant Test",foodType,LocalTime.of(8,0,0), LocalTime.of(23,0,0),user,"Restaurant Test");
        boolean active = true;

        //Action and Assert
        assertThatThrownBy(() -> new Menu(id, name, description, restaurant,active))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The description is required field");
    }

    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = null;
        String description = "Calabrese pizza";
        FoodType foodType =  new FoodType(UUID.randomUUID(),"Test");
        UserType userType = new UserType(UUID.randomUUID(),"Customer Test",true);

        User user = new User(UUID.randomUUID(),"Jonh Doe","test@gmail.com",userType);

        Restaurant restaurant = new Restaurant(UUID.randomUUID(),"Restaurant Test",foodType,LocalTime.of(8,0,0), LocalTime.of(23,0,0),user,"Restaurant Test");
        boolean active = true;

        //Action and Assert
        assertThatThrownBy(() -> new Menu(id, name, description, restaurant,active))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should throw exception when name contains only spaces")
    void shouldThrowExceptionWhenNameContainsOnlySpace() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "   ";
        String description = "Calabrese pizza";
        FoodType foodType =  new FoodType(UUID.randomUUID(),"Test");
        UserType userType = new UserType(UUID.randomUUID(),"Customer Test",true);

        User user = new User(UUID.randomUUID(),"Jonh Doe","test@gmail.com",userType);

        Restaurant restaurant = new Restaurant(UUID.randomUUID(),"Restaurant Test",foodType,LocalTime.of(8,0,0), LocalTime.of(23,0,0),user,"Restaurant Test");
        boolean active = true;

        //Action and Assert
        assertThatThrownBy(() -> new Menu(id, name, description, restaurant,active))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The name is required field");
    }

    @Test
    @DisplayName("Should throw exception when restaurant is null")
    void shouldThrowExceptionWhenRestaurantIsNull() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "Restaurant B2";
        String description = "Calabrese pizza";
        boolean active = true;

        //Action and Assert
        assertThatThrownBy(() -> new Menu(id, name, description, null,active))
                .isInstanceOf(ValidationFieldsException.class)
                .hasMessage("The restaurant is required field");
    }

    private Menu createMenu() {
        FoodType foodType =  new FoodType(UUID.randomUUID(),"Test");
        UserType userType = new UserType(UUID.randomUUID(),"Customer Test",true);

        User user = new User(UUID.randomUUID(),"Jonh Doe","test@gmail.com",userType);

       Restaurant restaurant = new Restaurant (UUID.randomUUID(),"Restaurant Test",foodType,LocalTime.of(8,0,0), LocalTime.of(23,0,0),user,"Restaurant Test");

        return new Menu(
                UUID.randomUUID(),
                "Pizza",
                "Calabrese pizza",
                createRestaurant(),
                true
        );
    }

   private Restaurant createRestaurant()
   {
       FoodType foodType =  new FoodType(UUID.randomUUID(),"Test");
       UserType userType = new UserType(UUID.randomUUID(),"Customer Test",true);

       User user = new User(UUID.randomUUID(),"Jonh Doe","test@gmail.com",userType);

       return new Restaurant (UUID.randomUUID(),"Restaurant New Test",foodType,LocalTime.of(8,0,0), LocalTime.of(23,0,0),user,"Restaurant New Test");

   }
}