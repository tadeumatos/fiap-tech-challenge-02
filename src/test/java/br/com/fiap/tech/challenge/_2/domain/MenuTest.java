package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.exceptions.ValidationFieldsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @Test
    @DisplayName("Should create a menu successfully")
    void shouldCreateMenuSuccessfully() {
        // Arrange
        UUID id = UUID.randomUUID(); Restaurant restaurant = createValidRestaurant();

        // Action
        Menu menu = Menu.create( id, "Feijoada", "Traditional Brazilian feijoada", new BigDecimal("45.90"), false, "feijoada.jpg", restaurant, true );

        // Assert
        assertNotNull(menu);
        assertEquals(id, menu.getId());
        assertEquals("Feijoada", menu.getName());
        assertEquals( "Traditional Brazilian feijoada", menu.getDescription() );
        assertEquals( new BigDecimal("45.90"), menu.getPrice() );
        assertFalse(menu.isOnlyLocal());
        assertEquals("feijoada.jpg", menu.getFoodPhoto());
        assertEquals(restaurant, menu.getRestaurant());
        assertTrue(menu.isActive());
    }

    @Test
    @DisplayName("Should create a menu with inactive status")
    void shouldCreateMenuWithInactiveStatus() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        Menu menu = Menu.create( UUID.randomUUID(), "Pizza", "Traditional pizza", new BigDecimal("50.00"), false, "pizza.jpg", restaurant, false );

        // Assert
        assertNotNull(menu);
        assertFalse(menu.isActive());

    }

    @Test
    @DisplayName("Should create a menu with only local option enabled")
    void shouldCreateMenuWithOnlyLocalEnabled() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        Menu menu = Menu.create( UUID.randomUUID(), "Pasta", "Fresh pasta", new BigDecimal("35.00"), true, "pasta.jpg", restaurant, true );

        // Assert
        assertNotNull(menu);
        assertTrue(menu.isOnlyLocal());
    }

    @Test
    @DisplayName("Should throw exception when id is null")
    void shouldThrowExceptionWhenIdIsNull() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Menu.create( null, "Feijoada", "Traditional Brazilian feijoada", new BigDecimal("45.90"),
                 false, "feijoada.jpg", restaurant, true ) );

        // Assert
        assertEquals( "The id is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Menu.create( UUID.randomUUID(), null, "Traditional Brazilian feijoada",
                                               new BigDecimal("45.90"), false, "feijoada.jpg", restaurant, true ) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );

    }

    @Test
    @DisplayName("Should throw exception when name is blank")
    void shouldThrowExceptionWhenNameIsBlank() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Menu.create( UUID.randomUUID(), " ", "Traditional Brazilian feijoada",
                new BigDecimal("45.90"), false, "feijoada.jpg", restaurant, true ) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when description is null")
    void shouldThrowExceptionWhenDescriptionIsNull() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Menu.create( UUID.randomUUID(), "Feijoada", null, new BigDecimal("45.90"),
                false, "feijoada.jpg", restaurant, true ) );

        // Assert
        assertEquals( "The description is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when description is blank")
    void shouldThrowExceptionWhenDescriptionIsBlank() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Menu.create( UUID.randomUUID(), "Feijoada", " ", new BigDecimal("45.90"),
                false, "feijoada.jpg", restaurant, true ) );

        // Assert
        assertEquals( "The description is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when price is null")
    void shouldThrowExceptionWhenPriceIsNull() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Menu.create( UUID.randomUUID(), "Feijoada", "Traditional Brazilian feijoada",
                null, false, "feijoada.jpg", restaurant, true ) );

        // Assert
        assertEquals( "The price is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when price is zero")
    void shouldThrowExceptionWhenPriceIsZero() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Menu.create( UUID.randomUUID(), "Feijoada", "Traditional Brazilian feijoada", BigDecimal.ZERO,
                false, "feijoada.jpg", restaurant, true ) );

        // Assert
        assertEquals( "The price not be < 0", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when price is negative")
    void shouldThrowExceptionWhenPriceIsNegative() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Menu.create( UUID.randomUUID(), "Feijoada", "Traditional Brazilian feijoada",
                new BigDecimal("-10.00"), false, "feijoada.jpg", restaurant, true ) );

        // Assert
        assertEquals( "The price not be < 0", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when food photo is null")
    void shouldThrowExceptionWhenFoodPhotoIsNull() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Menu.create( UUID.randomUUID(), "Feijoada", "Traditional Brazilian feijoada",
                new BigDecimal("45.90"), false, null, restaurant, true ) );

        // Assert
        assertEquals( "The food photo is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when food photo is blank")
    void shouldThrowExceptionWhenFoodPhotoIsBlank() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Menu.create( UUID.randomUUID(), "Feijoada", "Traditional Brazilian feijoada",
                new BigDecimal("45.90"), false, " ", restaurant, true ) );

        // Assert
        assertEquals( "The food photo is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when restaurant is null")
    void shouldThrowExceptionWhenRestaurantIsNull() {

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Menu.create( UUID.randomUUID(), "Feijoada", "Traditional Brazilian feijoada",
                new BigDecimal("45.90"), false, "feijoada.jpg", null, true ) );

        // Assert
        assertEquals( "The restaurant is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update menu id successfully")
    void shouldUpdateMenuIdSuccessfully() {
        // Arrange
        Menu menu = createValidMenu();
        UUID newId = UUID.randomUUID();

        // Action
        menu.setId(newId);

        // Assert
        assertEquals(newId, menu.getId());
    }

    @Test
    @DisplayName("Should throw exception when setting null id")
    void shouldThrowExceptionWhenSettingNullId() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> menu.setId(null) );

        // Assert
        assertEquals( "The id is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update menu name successfully")
    void shouldUpdateMenuNameSuccessfully() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        menu.setName("Updated Feijoada");

        // Assert
        assertEquals( "Updated Feijoada", menu.getName() );
    }

    @Test
    @DisplayName("Should throw exception when setting null name")
    void shouldThrowExceptionWhenSettingNullName() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> menu.setName(null) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when setting blank name")
    void shouldThrowExceptionWhenSettingBlankName() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> menu.setName(" ") );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update menu description successfully")
    void shouldUpdateMenuDescriptionSuccessfully() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        menu.setDescription("Updated description");

        // Assert
        assertEquals( "Updated description", menu.getDescription() );
    }

    @Test
    @DisplayName("Should throw exception when setting null description")
    void shouldThrowExceptionWhenSettingNullDescription() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> menu.setDescription(null) );

        // Assert
        assertEquals( "The description is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when setting blank description")
    void shouldThrowExceptionWhenSettingBlankDescription() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> menu.setDescription(" ") );

        // Assert
        assertEquals( "The description is required field", exception.getMessage() );

    } @Test
    @DisplayName("Should update menu price successfully")
    void shouldUpdateMenuPriceSuccessfully() {
        // Arrange
        Menu menu = createValidMenu();
        BigDecimal newPrice = new BigDecimal("59.90");

        // Action
        menu.setPrice(newPrice);

        // Assert
        assertEquals( newPrice, menu.getPrice() );
    }

    @Test
    @DisplayName("Should throw exception when setting null price")
    void shouldThrowExceptionWhenSettingNullPrice() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> menu.setPrice(null) );

        // Assert
        assertEquals( "The price is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when setting zero price")
    void shouldThrowExceptionWhenSettingZeroPrice() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> menu.setPrice(BigDecimal.ZERO) );

        // Assert
        assertEquals( "The price not be < 0", exception.getMessage() );

    }

    @Test
    @DisplayName("Should throw exception when setting negative price")
    void shouldThrowExceptionWhenSettingNegativePrice() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> menu.setPrice(new BigDecimal("-5.00")) );

        // Assert
        assertEquals( "The price not be < 0", exception.getMessage() );

    }

    @Test
    @DisplayName("Should update food photo successfully")
    void shouldUpdateFoodPhotoSuccessfully() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        menu.setFoodPhoto("updated-photo.jpg");

        // Assert
        assertEquals( "updated-photo.jpg", menu.getFoodPhoto() );
    }

    @Test
    @DisplayName("Should throw exception when setting null food photo")
    void shouldThrowExceptionWhenSettingNullFoodPhoto() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> menu.setFoodPhoto(null) );

        // Assert
        assertEquals( "The food photo is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when setting blank food photo")
    void shouldThrowExceptionWhenSettingBlankFoodPhoto() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> menu.setFoodPhoto(" ") );

        // Assert
        assertEquals( "The food photo is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update restaurant successfully")
    void shouldUpdateRestaurantSuccessfully() {
        // Arrange
        Menu menu = createValidMenu();
        Restaurant newRestaurant = createValidRestaurant();

        // Action
        menu.setRestaurant(newRestaurant);

        // Assert
        assertEquals( newRestaurant, menu.getRestaurant() );
    }

    @Test
    @DisplayName("Should throw exception when setting null restaurant")
    void shouldThrowExceptionWhenSettingNullRestaurant() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> menu.setRestaurant(null) );

        // Assert
        assertEquals( "The restaurant is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update active status successfully")
    void shouldUpdateActiveStatusSuccessfully() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        menu.setActive(false);

        // Assert
        assertFalse(menu.isActive());
    }

    @Test
    @DisplayName("Should update only local status successfully")
    void shouldUpdateOnlyLocalStatusSuccessfully() {
        // Arrange
        Menu menu = createValidMenu();

        // Action
        menu.setOnlyLocal(true);

        // Assert
        assertTrue(menu.isOnlyLocal());
    }

    private Menu createValidMenu() {

        return Menu.create( UUID.randomUUID(), "Feijoada", "Traditional Brazilian feijoada", new BigDecimal("45.90"),
                false, "feijoada.jpg", createValidRestaurant(), true );
    }

    private Restaurant createValidRestaurant() {
        Address address = Address.create( UUID.randomUUID(), "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" );
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );
        User user = User.create( UUID.randomUUID(), "John Silva", "john@email.com", userType );
        FoodType foodType = FoodType.create( UUID.randomUUID(), "Brazilian" );

        return Restaurant.create( UUID.randomUUID(), "Brazilian Restaurant", "Traditional Brazilian food", address, "100", "Near the main square",
                user, foodType, LocalTime.of(11, 0), LocalTime.of(23, 0) );
    }

}