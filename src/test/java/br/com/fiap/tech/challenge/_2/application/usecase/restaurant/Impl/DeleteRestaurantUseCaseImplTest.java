package br.com.fiap.tech.challenge._2.application.usecase.restaurant.Impl;

import br.com.fiap.tech.challenge._2.application.gateway.MenuGateway;
import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.exceptions.EntityInUseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private MenuGateway menuGateway;

    private DeleteRestaurantUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteRestaurantUseCaseImpl(
                restaurantGateway,
                menuGateway
        );
    }

    @Test
    @DisplayName("Should delete restaurant successfully when it is not in use")
    void shouldDeleteRestaurantSuccessfullyWhenNotInUse() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        when(menuGateway.existRestaurant(restaurantId))
                .thenReturn(false);

        // Act
        assertDoesNotThrow(
                () -> useCase.execute(restaurantId)
        );

        // Assert
        verify(menuGateway)
                .existRestaurant(restaurantId);

        verify(restaurantGateway)
                .delete(restaurantId);
    }

    @Test
    @DisplayName("Should throw EntityInUseException when restaurant is in use")
    void shouldThrowEntityInUseExceptionWhenRestaurantIsInUse() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        when(menuGateway.existRestaurant(restaurantId))
                .thenReturn(true);

        // Act
        EntityInUseException exception = assertThrows(
                EntityInUseException.class,
                () -> useCase.execute(restaurantId)
        );

        // Assert
        assertEquals(
                "The cannot be deleted, the register this in use",
                exception.getMessage()
        );

        verify(menuGateway)
                .existRestaurant(restaurantId);

        verify(
                restaurantGateway,
                never()
        ).delete(any(UUID.class));
    }

    @Test
    @DisplayName("Should validate restaurant usage before deleting")
    void shouldValidateRestaurantUsageBeforeDeleting() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        when(menuGateway.existRestaurant(restaurantId))
                .thenReturn(false);

        InOrder inOrder = inOrder(
                menuGateway,
                restaurantGateway
        );

        // Act
        useCase.execute(restaurantId);

        // Assert
        inOrder.verify(menuGateway)
                .existRestaurant(restaurantId);

        inOrder.verify(restaurantGateway)
                .delete(restaurantId);
    }

    @Test
    @DisplayName("Should call existRestaurant only once")
    void shouldCallExistRestaurantOnlyOnce() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        when(menuGateway.existRestaurant(restaurantId))
                .thenReturn(false);

        // Act
        useCase.execute(restaurantId);

        // Assert
        verify(
                menuGateway,
                times(1)
        ).existRestaurant(restaurantId);
    }

    @Test
    @DisplayName("Should not delete restaurant when it is in use")
    void shouldNotDeleteRestaurantWhenItIsInUse() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        when(menuGateway.existRestaurant(restaurantId))
                .thenReturn(true);

        // Act
        assertThrows(
                EntityInUseException.class,
                () -> useCase.execute(restaurantId)
        );

        // Assert
        verify(
                restaurantGateway,
                never()
        ).delete(any(UUID.class));
    }

    @Test
    @DisplayName("Should delete the restaurant using the provided ID")
    void shouldDeleteRestaurantUsingProvidedId() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        when(menuGateway.existRestaurant(restaurantId))
                .thenReturn(false);

        // Act
        useCase.execute(restaurantId);

        // Assert
        verify(
                restaurantGateway,
                times(1)
        ).delete(restaurantId);
    }
}

