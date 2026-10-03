package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.exceptions.BusinessException;
import br.com.fiap.tech.challenge._2.exceptions.ValidationFieldsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    @DisplayName("Should create Restaurant successfully with valid data")
    void shouldCreateRestaurantSuccessfullyWithValidData() {
        // Arrange
        UUID id = UUID.randomUUID();
        Address address = createValidAddress();
        User user = createOwnerUser();
        FoodType foodType = createValidFoodType();

        // Action
        Restaurant restaurant = Restaurant.create( id, "Brazilian Restaurant", "Traditional Brazilian food", address, "100", "Near the main square", user, foodType, LocalTime.of(11, 0), LocalTime.of(23, 0) );

        // Assert
        assertNotNull(restaurant);
        assertEquals(id, restaurant.getId());
        assertEquals("Brazilian Restaurant", restaurant.getName());
        assertEquals( "Traditional Brazilian food", restaurant.getDescription() );
        assertEquals(address, restaurant.getAddress());
        assertEquals("100", restaurant.getAddressNumber());
        assertEquals( "Near the main square", restaurant.getAddressComplement() );
        assertEquals(user, restaurant.getUser());
        assertEquals(foodType, restaurant.getFoodType());
        assertEquals( LocalTime.of(11, 0), restaurant.getStartTime() );
        assertEquals( LocalTime.of(23, 0), restaurant.getEndTime() );
    }

    @Test
    @DisplayName("Should create Restaurant when start time equals end time")
    void shouldCreateRestaurantWhenStartTimeEqualsEndTime() {
        // Arrange
        LocalTime time = LocalTime.of(12, 0);

        // Action
        Restaurant restaurant = Restaurant.create( UUID.randomUUID(), "Brazilian Restaurant", "Traditional Brazilian food",
                                createValidAddress(), "100", "Near the main square", createOwnerUser(), createValidFoodType(), time, time );

        // Assert
        assertNotNull(restaurant);
        assertEquals(time, restaurant.getStartTime());
        assertEquals(time, restaurant.getEndTime());
    }

    @Test
    @DisplayName("Should throw exception when id is null")
    void shouldThrowExceptionWhenIdIsNull() {
        // Arrange
        UUID id = null;

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Restaurant.create( id, "Brazilian Restaurant", "Traditional Brazilian food",
                                                           createValidAddress(), "100", "Near the main square", createOwnerUser(), createValidFoodType(), LocalTime.of(11, 0), LocalTime.of(23, 0) ) );

        // Assert
        assertEquals( "The id is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        // Arrange
        String name = null;

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Restaurant.create( UUID.randomUUID(), name, "Traditional Brazilian food",
                                                            createValidAddress(), "100", "Near the main square", createOwnerUser(), createValidFoodType(), LocalTime.of(11, 0), LocalTime.of(23, 0) ) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when name is blank")
    void shouldThrowExceptionWhenNameIsBlank() {
        // Arrange
        String name = " ";

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Restaurant.create( UUID.randomUUID(), name, "Traditional Brazilian food",
                                                           createValidAddress(), "100", "Near the main square", createOwnerUser(), createValidFoodType(),
                                                           LocalTime.of(11, 0), LocalTime.of(23, 0) ) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when user is null")
    void shouldThrowExceptionWhenUserIsNull() {
        // Arrange
        User user = null;

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Restaurant.create( UUID.randomUUID(), "Brazilian Restaurant", "Traditional Brazilian food",
                                                            createValidAddress(), "100", "Near the main square", user, createValidFoodType(), LocalTime.of(11, 0),
                                                            LocalTime.of(23, 0) ) );

        // Assert
        assertEquals( "The user is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when address is null")
    void shouldThrowExceptionWhenAddressIsNull() {
        // Arrange
        Address address = null;

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Restaurant.create( UUID.randomUUID(), "Brazilian Restaurant", "Traditional Brazilian food", address,
                                                                                                        "100", "Near the main square", createOwnerUser(), createValidFoodType(),
                                                                                                  LocalTime.of(11, 0), LocalTime.of(23, 0) ) );

        // Assert
        assertEquals( "The address is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when address number is null")
    void shouldThrowExceptionWhenAddressNumberIsNull() {
        // Arrange
        String addressNumber = null;

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Restaurant.create( UUID.randomUUID(), "Brazilian Restaurant", "Traditional Brazilian food",
                                                            createValidAddress(), addressNumber, "Near the main square", createOwnerUser(), createValidFoodType(), LocalTime.of(11, 0), LocalTime.of(23, 0) ) );

        // Assert
        assertEquals( "The address complement is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when address number is blank")
    void shouldThrowExceptionWhenAddressNumberIsBlank() {
        // Arrange
        String addressNumber = " ";

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Restaurant.create( UUID.randomUUID(), "Brazilian Restaurant", "Traditional Brazilian food",
                                                           createValidAddress(), addressNumber, "Near the main square", createOwnerUser(), createValidFoodType(), LocalTime.of(11, 0), LocalTime.of(23, 0) ) );

        // Assert
        assertEquals( "The address complement is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when food type is null")
    void shouldThrowExceptionWhenFoodTypeIsNull() {
        // Arrange
        FoodType foodType = null;

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Restaurant.create( UUID.randomUUID(), "Brazilian Restaurant", "Traditional Brazilian food",
                                                           createValidAddress(), "100", "Near the main square", createOwnerUser(), foodType, LocalTime.of(11, 0), LocalTime.of(23, 0) ) );

        // Assert
        assertEquals( "The food type is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when start time is null")
    void shouldThrowExceptionWhenStartTimeIsNull() {
        // Arrange
        LocalTime startTime = null;

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Restaurant.create( UUID.randomUUID(), "Brazilian Restaurant", "Traditional Brazilian food",
                createValidAddress(), "100", "Near the main square", createOwnerUser(), createValidFoodType(), startTime, LocalTime.of(23, 0) ) );

        // Assert
        assertEquals( "The start time type is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when end time is null")
    void shouldThrowExceptionWhenEndTimeIsNull() {
        // Arrange
        LocalTime endTime = null;

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Restaurant.create( UUID.randomUUID(), "Brazilian Restaurant", "Traditional Brazilian food",
                createValidAddress(), "100", "Near the main square", createOwnerUser(), createValidFoodType(), LocalTime.of(11, 0), endTime ) );

        // Assert
        assertEquals( "The end time type is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when start time is after end time")
    void shouldThrowExceptionWhenStartTimeIsAfterEndTime() {
        // Arrange
        LocalTime startTime = LocalTime.of(23, 0);
        LocalTime endTime = LocalTime.of(11, 0);

        // Action
        BusinessException exception = assertThrows( BusinessException.class, () -> Restaurant.create( UUID.randomUUID(), "Brazilian Restaurant", "Traditional Brazilian food",
                createValidAddress(), "100", "Near the main square", createOwnerUser(), createValidFoodType(), startTime, endTime ) );

        // Assert
        assertEquals( "The start time cannot be after the end time", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when user is not an owner")
    void shouldThrowExceptionWhenUserIsNotAnOwner() {
        // Arrange
        UserType userType = UserType.create( UUID.randomUUID(), "Customer", false );
        User user = User.create( UUID.randomUUID(), "John Silva", "john@email.com", userType );

        // Action
        BusinessException exception = assertThrows( BusinessException.class, () -> Restaurant.create( UUID.randomUUID(), "Brazilian Restaurant", "Traditional Brazilian food",
                createValidAddress(), "100", "Near the main square", user, createValidFoodType(), LocalTime.of(11, 0), LocalTime.of(23, 0) ) );

        // Assert
        assertEquals( "The user have be owner", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update Restaurant name successfully")
    void shouldUpdateRestaurantNameSuccessfully() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        restaurant.setName("Updated Restaurant");
        // Assert
        assertEquals( "Updated Restaurant", restaurant.getName() );
    }

    @Test
    @DisplayName("Should throw exception when setting name to null")
    void shouldThrowExceptionWhenSettingNameToNull() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> restaurant.setName(null) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when setting name to blank")
    void shouldThrowExceptionWhenSettingNameToBlank() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> restaurant.setName(" ") );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update Restaurant food type successfully")
    void shouldUpdateRestaurantFoodTypeSuccessfully() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();
        FoodType newFoodType = FoodType.create( UUID.randomUUID(), "Italian" );

        // Action
        restaurant.setFoodType(newFoodType);

        // Assert
        assertEquals( newFoodType, restaurant.getFoodType() );

    }

    @Test
    @DisplayName("Should throw exception when setting food type to null")
    void shouldThrowExceptionWhenSettingFoodTypeToNull() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> restaurant.setFoodType(null) );

        // Assert
        assertEquals( "The food type is required field", exception.getMessage() );

    }

    @Test
    @DisplayName("Should update Restaurant start time successfully")
    void shouldUpdateRestaurantStartTimeSuccessfully() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();
        LocalTime newStartTime = LocalTime.of(10, 0);

        // Action
        restaurant.setStartTime(newStartTime);

        // Assert
        assertEquals( newStartTime, restaurant.getStartTime() );
    }

    @Test
    @DisplayName("Should throw exception when setting start time to null")
    void shouldThrowExceptionWhenSettingStartTimeToNull() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> restaurant.setStartTime(null) );

        // Assert
        assertEquals( "The start time type is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update Restaurant end time successfully")
    void shouldUpdateRestaurantEndTimeSuccessfully() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();
        LocalTime newEndTime = LocalTime.of(22, 0);

        // Action
        restaurant.setEndTime(newEndTime);

        // Assert
        assertEquals( newEndTime, restaurant.getEndTime() );
    }

    @Test
    @DisplayName("Should throw exception when setting end time to null")
    void shouldThrowExceptionWhenSettingEndTimeToNull() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> restaurant.setEndTime(null) );

        // Assert
        assertEquals( "The end time type is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when setting start time after end time")
    void shouldThrowExceptionWhenSettingStartTimeAfterEndTime() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        BusinessException exception = assertThrows( BusinessException.class, () -> restaurant.setStartTime( LocalTime.of(23, 30) ) );

        // Assert
        assertEquals( "The start time cannot be after the end time", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when setting end time before start time")
    void shouldThrowExceptionWhenSettingEndTimeBeforeStartTime() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        BusinessException exception = assertThrows( BusinessException.class, () -> restaurant.setEndTime( LocalTime.of(10, 0) ) );

        // Assert
        assertEquals( "The start time cannot be after the end time", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update Restaurant user successfully")
    void shouldUpdateRestaurantUserSuccessfully() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();
        User newUser = createOwnerUser();

        // Action
        restaurant.setUser(newUser);

        // Assert
        assertEquals( newUser, restaurant.getUser() );
    }

    @Test
    @DisplayName("Should throw exception when setting user to null")
    void shouldThrowExceptionWhenSettingUserToNull() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> restaurant.setUser(null) );

        // Assert
        assertEquals( "The user is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update Restaurant address successfully")
    void shouldUpdateRestaurantAddressSuccessfully() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();
        Address newAddress = Address.create( UUID.randomUUID(), "New Street", "Aldeota", "Fortaleza", "CE", "60100-000", "Brazil" );

        // Action
        restaurant.setAddress(newAddress);

        // Assert
        assertEquals( newAddress, restaurant.getAddress() );

    }

    @Test
    @DisplayName("Should throw exception when setting address to null")
    void shouldThrowExceptionWhenSettingAddressToNull() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> restaurant.setAddress(null) );

        // Assert
        assertEquals( "The address is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update Restaurant address number successfully")
    void shouldUpdateRestaurantAddressNumberSuccessfully() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        restaurant.setAddressNumber("200");

        // Assert
        assertEquals( "200", restaurant.getAddressNumber() );
    }

    @Test
    @DisplayName("Should throw exception when setting address number to null")
    void shouldThrowExceptionWhenSettingAddressNumberToNull() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> restaurant.setAddressNumber(null) );

        // Assert
        assertEquals( "The address complement is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update Restaurant description successfully")
    void shouldUpdateRestaurantDescriptionSuccessfully() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        restaurant.setDescription("New restaurant description");

        // Assert
        assertEquals( "New restaurant description", restaurant.getDescription() );
    }

    @Test
    @DisplayName("Should update Restaurant address complement successfully")
    void shouldUpdateRestaurantAddressComplementSuccessfully() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();

        // Action
        restaurant.setAddressComplement("Apartment 101");

        // Assert
        assertEquals( "Apartment 101", restaurant.getAddressComplement() );
    }

    @Test
    @DisplayName("Should update Restaurant id successfully")
    void shouldUpdateRestaurantIdSuccessfully() {
        // Arrange
        Restaurant restaurant = createValidRestaurant();
        UUID newId = UUID.randomUUID();

        // Action
        restaurant.setId(newId);

        // Assert
        assertEquals( newId, restaurant.getId() );
    }

    private Restaurant createValidRestaurant() {

        return Restaurant.create( UUID.randomUUID(), "Brazilian Restaurant", "Traditional Brazilian food",
                                  createValidAddress(), "100", "Near the main square", createOwnerUser(),
                                  createValidFoodType(), LocalTime.of(11, 0), LocalTime.of(23, 0) );
    }

    private Address createValidAddress() {

        return Address.create( UUID.randomUUID(), "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" );

    }

    private User createOwnerUser() {
        UserType userType = UserType.create( UUID.randomUUID(), "Administrator", true );

        return User.create( UUID.randomUUID(), "John Silva", "john@email.com", userType );
    }

    private FoodType createValidFoodType() {
        return FoodType.create( UUID.randomUUID(), "Brazilian" );
    }
}