package br.com.fiap.tech.challenge._2.application.usecase.user.impl;

import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
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
class DeleteUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    private DeleteUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteUserUseCaseImpl(
                userGateway,
                restaurantGateway
        );
    }

    @Test
    @DisplayName("Should delete user successfully when user is not in use")
    void shouldDeleteUserSuccessfullyWhenUserIsNotInUse() {

        // Arrange
        UUID userId = UUID.randomUUID();

        when(restaurantGateway.existUser(userId))
                .thenReturn(false);

        // Act
        useCase.execute(userId);

        // Assert
        verify(restaurantGateway)
                .existUser(userId);

        verify(userGateway)
                .delete(userId);
    }

    @Test
    @DisplayName("Should throw EntityInUseException when user is in use")
    void shouldThrowEntityInUseExceptionWhenUserIsInUse() {

        // Arrange
        UUID userId = UUID.randomUUID();

        when(restaurantGateway.existUser(userId))
                .thenReturn(true);

        // Act
        EntityInUseException exception = assertThrows(
                EntityInUseException.class,
                () -> useCase.execute(userId)
        );

        // Assert
        assertEquals(
                "The cannot be deleted, the register this in use",
                exception.getMessage()
        );

        verify(restaurantGateway)
                .existUser(userId);

        verify(
                userGateway,
                never()
        ).delete(userId);
    }

    @Test
    @DisplayName("Should validate user usage before deleting")
    void shouldValidateUserUsageBeforeDeleting() {

        // Arrange
        UUID userId = UUID.randomUUID();

        when(restaurantGateway.existUser(userId))
                .thenReturn(false);

        // Act
        useCase.execute(userId);

        // Assert
        InOrder inOrder = inOrder(
                restaurantGateway,
                userGateway
        );

        inOrder.verify(restaurantGateway)
                .existUser(userId);

        inOrder.verify(userGateway)
                .delete(userId);
    }

    @Test
    @DisplayName("Should call existUser only once")
    void shouldCallExistUserOnlyOnce() {

        // Arrange
        UUID userId = UUID.randomUUID();

        when(restaurantGateway.existUser(userId))
                .thenReturn(false);

        // Act
        useCase.execute(userId);

        // Assert
        verify(
                restaurantGateway,
                times(1)
        ).existUser(userId);
    }

    @Test
    @DisplayName("Should not delete user when user is in use")
    void shouldNotDeleteUserWhenUserIsInUse() {

        // Arrange
        UUID userId = UUID.randomUUID();

        when(restaurantGateway.existUser(userId))
                .thenReturn(true);

        // Act
        assertThrows(
                EntityInUseException.class,
                () -> useCase.execute(userId)
        );

        // Assert
        verify(
                userGateway,
                never()
        ).delete(any(UUID.class));
    }

    @Test
    @DisplayName("Should delete the user using the provided id")
    void shouldDeleteUserUsingProvidedId() {

        // Arrange
        UUID userId = UUID.randomUUID();

        when(restaurantGateway.existUser(userId))
                .thenReturn(false);

        // Act
        useCase.execute(userId);

        // Assert
        verify(
                userGateway,
                times(1)
        ).delete(userId);
    }
}

