package br.com.fiap.tech.challenge._2.application.usecase.restaurant.Impl;

import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.domain.Address;
import br.com.fiap.tech.challenge._2.domain.FoodType;
import br.com.fiap.tech.challenge._2.domain.Restaurant;
import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.presentation.controller.request.RestaurantRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private UserGateway userGateway;

    @Mock
    private FoodTypeGateway foodTypeGateway;

    @Mock
    private AddressGateway addressGateway;

    private UpdateRestaurantUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateRestaurantUseCaseImpl(
                restaurantGateway,
                userGateway,
                foodTypeGateway,
                addressGateway
        );
    }

    @Test
    @DisplayName("Should update restaurant successfully")
    void shouldUpdateRestaurantSuccessfully() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID foodTypeId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Restaurant Owner",
                true
        );

        User user = User.create(
                userId,
                "John Silva",
                "john@email.com",
                userType
        );

        FoodType foodType = FoodType.create(
                foodTypeId,
                "Brazilian Food"
        );

        Address address = Address.create(
                addressId,
                "Main Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        RestaurantRequest request = createRequest(
                userId,
                foodTypeId,
                addressId
        );

        Restaurant updatedRestaurant = Restaurant.create(
                UUID.randomUUID(),
                request.name(),
                request.description(),
                address,
                request.addressNumber(),
                request.addressComplement(),
                user,
                foodType,
                request.startTime(),
                request.endTime()
        );

        when(userGateway.findById(userId))
                .thenReturn(Optional.of(user));

        when(foodTypeGateway.findById(foodTypeId))
                .thenReturn(Optional.of(foodType));

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.of(address));

        when(restaurantGateway.update(
                eq(restaurantId),
                any(Restaurant.class)
        )).thenReturn(updatedRestaurant);

        // Act
        Restaurant result = useCase.execute(
                restaurantId,
                request
        );

        // Assert
        assertNotNull(result);

        assertEquals(
                updatedRestaurant,
                result
        );

        verify(userGateway)
                .findById(userId);

        verify(foodTypeGateway)
                .findById(foodTypeId);

        verify(addressGateway)
                .findById(addressId);

        verify(restaurantGateway)
                .update(
                        eq(restaurantId),
                        any(Restaurant.class)
                );
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user does not exist")
    void shouldThrowResourceNotFoundExceptionWhenUserDoesNotExist() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID foodTypeId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        RestaurantRequest request = createRequest(
                userId,
                foodTypeId,
                addressId
        );

        when(userGateway.findById(userId))
                .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(
                        restaurantId,
                        request
                )
        );

        // Assert
        assertEquals(
                "The user not found",
                exception.getMessage()
        );

        verify(userGateway)
                .findById(userId);

        verify(foodTypeGateway, never())
                .findById(any(UUID.class));

        verify(addressGateway, never())
                .findById(any(UUID.class));

        verify(restaurantGateway, never())
                .update(
                        any(UUID.class),
                        any(Restaurant.class)
                );
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when food type does not exist")
    void shouldThrowResourceNotFoundExceptionWhenFoodTypeDoesNotExist() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID foodTypeId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Restaurant Owner",
                true
        );

        User user = User.create(
                userId,
                "John Silva",
                "john@email.com",
                userType
        );

        RestaurantRequest request = createRequest(
                userId,
                foodTypeId,
                addressId
        );

        when(userGateway.findById(userId))
                .thenReturn(Optional.of(user));

        when(foodTypeGateway.findById(foodTypeId))
                .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(
                        restaurantId,
                        request
                )
        );

        // Assert
        assertEquals(
                "The food type not found",
                exception.getMessage()
        );

        verify(userGateway)
                .findById(userId);

        verify(foodTypeGateway)
                .findById(foodTypeId);

        verify(addressGateway, never())
                .findById(any(UUID.class));

        verify(restaurantGateway, never())
                .update(
                        any(UUID.class),
                        any(Restaurant.class)
                );
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when address does not exist")
    void shouldThrowResourceNotFoundExceptionWhenAddressDoesNotExist() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID foodTypeId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Restaurant Owner",
                true
        );

        User user = User.create(
                userId,
                "John Silva",
                "john@email.com",
                userType
        );

        FoodType foodType = FoodType.create(
                foodTypeId,
                "Brazilian Food"
        );

        RestaurantRequest request = createRequest(
                userId,
                foodTypeId,
                addressId
        );

        when(userGateway.findById(userId))
                .thenReturn(Optional.of(user));

        when(foodTypeGateway.findById(foodTypeId))
                .thenReturn(Optional.of(foodType));

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(
                        restaurantId,
                        request
                )
        );

        // Assert
        assertEquals(
                "The address not found",
                exception.getMessage()
        );

        verify(userGateway)
                .findById(userId);

        verify(foodTypeGateway)
                .findById(foodTypeId);

        verify(addressGateway)
                .findById(addressId);

        verify(restaurantGateway, never())
                .update(
                        any(UUID.class),
                        any(Restaurant.class)
                );
    }

    @Test
    @DisplayName("Should update restaurant with request data")
    void shouldUpdateRestaurantWithRequestData() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID foodTypeId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Restaurant Owner",
                true
        );

        User user = User.create(
                userId,
                "John Silva",
                "john@email.com",
                userType
        );

        FoodType foodType = FoodType.create(
                foodTypeId,
                "Brazilian Food"
        );

        Address address = Address.create(
                addressId,
                "Main Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        RestaurantRequest request = createRequest(
                userId,
                foodTypeId,
                addressId
        );

        when(userGateway.findById(userId))
                .thenReturn(Optional.of(user));

        when(foodTypeGateway.findById(foodTypeId))
                .thenReturn(Optional.of(foodType));

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.of(address));

        when(restaurantGateway.update(
                eq(restaurantId),
                any(Restaurant.class)
        )).thenAnswer(invocation -> invocation.getArgument(1));

        ArgumentCaptor<Restaurant> restaurantCaptor =
                ArgumentCaptor.forClass(Restaurant.class);

        // Act
        Restaurant result = useCase.execute(
                restaurantId,
                request
        );

        // Assert
        verify(restaurantGateway)
                .update(
                        eq(restaurantId),
                        restaurantCaptor.capture()
                );

        Restaurant capturedRestaurant =
                restaurantCaptor.getValue();

        assertNotNull(
                capturedRestaurant.getId()
        );

        assertNotEquals(
                restaurantId,
                capturedRestaurant.getId()
        );

        assertEquals(
                request.name(),
                capturedRestaurant.getName()
        );

        assertEquals(
                request.description(),
                capturedRestaurant.getDescription()
        );

        assertEquals(
                request.addressNumber(),
                capturedRestaurant.getAddressNumber()
        );

        assertEquals(
                request.addressComplement(),
                capturedRestaurant.getAddressComplement()
        );

        assertEquals(
                request.startTime(),
                capturedRestaurant.getStartTime()
        );

        assertEquals(
                request.endTime(),
                capturedRestaurant.getEndTime()
        );

        assertEquals(
                address,
                capturedRestaurant.getAddress()
        );

        assertEquals(
                user,
                capturedRestaurant.getUser()
        );

        assertEquals(
                foodType,
                capturedRestaurant.getFoodType()
        );

        assertSame(
                capturedRestaurant,
                result
        );
    }

    @Test
    @DisplayName("Should use the provided restaurant ID when updating")
    void shouldUseProvidedRestaurantIdWhenUpdating() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID foodTypeId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Restaurant Owner",
                true
        );

        User user = User.create(
                userId,
                "John Silva",
                "john@email.com",
                userType
        );

        FoodType foodType = FoodType.create(
                foodTypeId,
                "Brazilian Food"
        );

        Address address = Address.create(
                addressId,
                "Main Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        RestaurantRequest request = createRequest(
                userId,
                foodTypeId,
                addressId
        );

        when(userGateway.findById(userId))
                .thenReturn(Optional.of(user));

        when(foodTypeGateway.findById(foodTypeId))
                .thenReturn(Optional.of(foodType));

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.of(address));

        when(restaurantGateway.update(
                eq(restaurantId),
                any(Restaurant.class)
        )).thenAnswer(invocation -> invocation.getArgument(1));

        // Act
        useCase.execute(
                restaurantId,
                request
        );

        // Assert
        verify(
                restaurantGateway,
                times(1)
        ).update(
                eq(restaurantId),
                any(Restaurant.class)
        );
    }

    @Test
    @DisplayName("Should create restaurant with a new generated ID")
    void shouldCreateRestaurantWithNewGeneratedId() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID foodTypeId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Restaurant Owner",
                true
        );

        User user = User.create(
                userId,
                "John Silva",
                "john@email.com",
                userType
        );

        FoodType foodType = FoodType.create(
                foodTypeId,
                "Brazilian Food"
        );

        Address address = Address.create(
                addressId,
                "Main Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        RestaurantRequest request = createRequest(
                userId,
                foodTypeId,
                addressId
        );

        when(userGateway.findById(userId))
                .thenReturn(Optional.of(user));

        when(foodTypeGateway.findById(foodTypeId))
                .thenReturn(Optional.of(foodType));

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.of(address));

        when(restaurantGateway.update(
                eq(restaurantId),
                any(Restaurant.class)
        )).thenAnswer(invocation -> invocation.getArgument(1));

        ArgumentCaptor<Restaurant> restaurantCaptor =
                ArgumentCaptor.forClass(Restaurant.class);

        // Act
        useCase.execute(
                restaurantId,
                request
        );

        // Assert
        verify(restaurantGateway)
                .update(
                        eq(restaurantId),
                        restaurantCaptor.capture()
                );

        Restaurant capturedRestaurant =
                restaurantCaptor.getValue();

        assertNotNull(
                capturedRestaurant.getId()
        );

        assertNotEquals(
                restaurantId,
                capturedRestaurant.getId()
        );
    }

    @Test
    @DisplayName("Should search dependencies in the correct order")
    void shouldSearchDependenciesInCorrectOrder() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID foodTypeId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Restaurant Owner",
                true
        );

        User user = User.create(
                userId,
                "John Silva",
                "john@email.com",
                userType
        );

        FoodType foodType = FoodType.create(
                foodTypeId,
                "Brazilian Food"
        );

        Address address = Address.create(
                addressId,
                "Main Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        RestaurantRequest request = createRequest(
                userId,
                foodTypeId,
                addressId
        );

        when(userGateway.findById(userId))
                .thenReturn(Optional.of(user));

        when(foodTypeGateway.findById(foodTypeId))
                .thenReturn(Optional.of(foodType));

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.of(address));

        when(restaurantGateway.update(
                eq(restaurantId),
                any(Restaurant.class)
        )).thenAnswer(invocation -> invocation.getArgument(1));

        InOrder inOrder = inOrder(
                userGateway,
                foodTypeGateway,
                addressGateway,
                restaurantGateway
        );

        // Act
        useCase.execute(
                restaurantId,
                request
        );

        // Assert
        inOrder.verify(userGateway)
                .findById(userId);

        inOrder.verify(foodTypeGateway)
                .findById(foodTypeId);

        inOrder.verify(addressGateway)
                .findById(addressId);

        inOrder.verify(restaurantGateway)
                .update(
                        eq(restaurantId),
                        any(Restaurant.class)
                );
    }

    @Test
    @DisplayName("Should call each dependency only once")
    void shouldCallEachDependencyOnlyOnce() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID foodTypeId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Restaurant Owner",
                true
        );

        User user = User.create(
                userId,
                "John Silva",
                "john@email.com",
                userType
        );

        FoodType foodType = FoodType.create(
                foodTypeId,
                "Brazilian Food"
        );

        Address address = Address.create(
                addressId,
                "Main Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        RestaurantRequest request = createRequest(
                userId,
                foodTypeId,
                addressId
        );

        when(userGateway.findById(userId))
                .thenReturn(Optional.of(user));

        when(foodTypeGateway.findById(foodTypeId))
                .thenReturn(Optional.of(foodType));

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.of(address));

        when(restaurantGateway.update(
                eq(restaurantId),
                any(Restaurant.class)
        )).thenAnswer(invocation -> invocation.getArgument(1));

        // Act
        useCase.execute(
                restaurantId,
                request
        );

        // Assert
        verify(
                userGateway,
                times(1)
        ).findById(userId);

        verify(
                foodTypeGateway,
                times(1)
        ).findById(foodTypeId);

        verify(
                addressGateway,
                times(1)
        ).findById(addressId);

        verify(
                restaurantGateway,
                times(1)
        ).update(
                eq(restaurantId),
                any(Restaurant.class)
        );
    }

    private RestaurantRequest createRequest(
            UUID userId,
            UUID foodTypeId,
            UUID addressId
    ) {
        return new RestaurantRequest(
                "John's Restaurant Updated",
                "Updated Brazilian food restaurant",
                addressId.toString(),
                "456",
                "Near the main square",
                userId.toString(),
                foodTypeId.toString(),
                LocalTime.of(11, 0),
                LocalTime.of(23, 0)
        );
    }
}

