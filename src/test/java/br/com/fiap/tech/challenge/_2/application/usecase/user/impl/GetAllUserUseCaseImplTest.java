package br.com.fiap.tech.challenge._2.application.usecase.user.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.domain.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    private GetAllUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetAllUserUseCaseImpl(
                userGateway
        );
    }

    @Test
    @DisplayName("Should return all users successfully")
    void shouldReturnAllUsersSuccessfully() {

        // Arrange
        UserType administratorType = UserType.create(
                UUID.randomUUID(),
                "Administrator",
                true
        );

        UserType customerType = UserType.create(
                UUID.randomUUID(),
                "Customer",
                false
        );

        User user1 = User.create(
                UUID.randomUUID(),
                "John Silva",
                "john@email.com",
                administratorType
        );

        User user2 = User.create(
                UUID.randomUUID(),
                "Maria Silva",
                "maria@email.com",
                customerType
        );

        List<User> users = List.of(
                user1,
                user2
        );

        when(userGateway.getAll())
                .thenReturn(users);

        // Act
        List<User> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(
                user1,
                result.get(0)
        );

        assertEquals(
                user2,
                result.get(1)
        );

        verify(userGateway)
                .getAll();
    }

    @Test
    @DisplayName("Should return an empty list when there are no users")
    void shouldReturnEmptyListWhenThereAreNoUsers() {

        // Arrange
        when(userGateway.getAll())
                .thenReturn(List.of());

        // Act
        List<User> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userGateway)
                .getAll();
    }

    @Test
    @DisplayName("Should call getAll only once")
    void shouldCallGetAllOnlyOnce() {

        // Arrange
        when(userGateway.getAll())
                .thenReturn(List.of());

        // Act
        useCase.execute();

        // Assert
        verify(
                userGateway,
                times(1)
        ).getAll();
    }

    @Test
    @DisplayName("Should return the exact list returned by the gateway")
    void shouldReturnTheExactListReturnedByGateway() {

        // Arrange
        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Customer",
                false
        );

        User user = User.create(
                UUID.randomUUID(),
                "John Silva",
                "john@email.com",
                userType
        );

        List<User> users = List.of(user);

        when(userGateway.getAll())
                .thenReturn(users);

        // Act
        List<User> result = useCase.execute();

        // Assert
        assertSame(
                users,
                result
        );

        verify(userGateway)
                .getAll();
    }

    @Test
    @DisplayName("Should preserve the order returned by the gateway")
    void shouldPreserveTheOrderReturnedByGateway() {

        // Arrange
        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Customer",
                false
        );

        User user1 = User.create(
                UUID.randomUUID(),
                "John Silva",
                "john@email.com",
                userType
        );

        User user2 = User.create(
                UUID.randomUUID(),
                "Maria Silva",
                "maria@email.com",
                userType
        );

        List<User> users = List.of(
                user1,
                user2
        );

        when(userGateway.getAll())
                .thenReturn(users);

        // Act
        List<User> result = useCase.execute();

        // Assert
        assertEquals(
                user1,
                result.get(0)
        );

        assertEquals(
                user2,
                result.get(1)
        );

        verify(userGateway)
                .getAll();
    }
}

