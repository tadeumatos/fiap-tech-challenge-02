package br.com.fiap.tech.challenge._2.application.usecase.user.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    private FindUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new FindUserUseCaseImpl(
                userGateway
        );
    }

    @Test
    @DisplayName("Should find user successfully")
    void shouldFindUserSuccessfully() {

        // Arrange
        UUID userId = UUID.randomUUID();

        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Administrator",
                true
        );

        User user = User.create(
                userId,
                "John Silva",
                "john@email.com",
                userType
        );

        when(userGateway.findById(userId))
                .thenReturn(Optional.of(user));

        // Act
        User result = useCase.execute(userId);

        // Assert
        assertNotNull(result);

        assertEquals(
                userId,
                result.getId()
        );

        assertEquals(
                "John Silva",
                result.getName()
        );

        assertEquals(
                "john@email.com",
                result.getEmail()
        );

        assertNotNull(result.getUserType());

        assertEquals(
                userType.getId(),
                result.getUserType().getId()
        );

        verify(userGateway)
                .findById(userId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user does not exist")
    void shouldThrowResourceNotFoundExceptionWhenUserDoesNotExist() {

        // Arrange
        UUID userId = UUID.randomUUID();

        when(userGateway.findById(userId))
                .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(userId)
        );

        // Assert
        assertEquals(
                "The user not found",
                exception.getMessage()
        );

        verify(userGateway)
                .findById(userId);
    }

    @Test
    @DisplayName("Should call findById only once")
    void shouldCallFindByIdOnlyOnce() {

        // Arrange
        UUID userId = UUID.randomUUID();

        when(userGateway.findById(userId))
                .thenReturn(Optional.empty());

        // Act
        assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(userId)
        );

        // Assert
        verify(
                userGateway,
                times(1)
        ).findById(userId);
    }

    @Test
    @DisplayName("Should return the exact user returned by the gateway")
    void shouldReturnTheExactUserReturnedByGateway() {

        // Arrange
        UUID userId = UUID.randomUUID();

        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Customer",
                false
        );

        User user = User.create(
                userId,
                "Maria Silva",
                "maria@email.com",
                userType
        );

        when(userGateway.findById(userId))
                .thenReturn(Optional.of(user));

        // Act
        User result = useCase.execute(userId);

        // Assert
        assertSame(
                user,
                result
        );

        verify(userGateway)
                .findById(userId);
    }

    @Test
    @DisplayName("Should search for user using the provided id")
    void shouldSearchForUserUsingProvidedId() {

        // Arrange
        UUID userId = UUID.randomUUID();

        when(userGateway.findById(userId))
                .thenReturn(Optional.empty());

        // Act
        assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(userId)
        );

        // Assert
        verify(
                userGateway,
                times(1)
        ).findById(userId);
    }
}

