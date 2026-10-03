package br.com.fiap.tech.challenge._2.application.usecase.usertype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
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
class FindUserTypeUseCaseImplTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    private FindUserTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new FindUserTypeUseCaseImpl(
                userTypeGateway
        );
    }

    @Test
    @DisplayName("Should find user type successfully")
    void shouldFindUserTypeSuccessfully() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(Optional.of(userType));

        // Action
        UserType result = useCase.execute(userTypeId);

        // Assert
        assertNotNull(result);
        assertEquals(userTypeId, result.getId());
        assertEquals("Administrator", result.getName());
        assertTrue(result.isOwner());

        verify(userTypeGateway)
                .findById(userTypeId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user type does not exist")
    void shouldThrowResourceNotFoundExceptionWhenUserTypeDoesNotExist() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(Optional.empty());

        // Action
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(userTypeId)
        );

        // Assert
        assertEquals(
                "User Type not found",
                exception.getMessage()
        );

        verify(userTypeGateway)
                .findById(userTypeId);
    }

    @Test
    @DisplayName("Should call findById only once")
    void shouldCallFindByIdOnlyOnce() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(Optional.empty());

        // Action
        assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(userTypeId)
        );

        // Assert
        verify(
                userTypeGateway,
                times(1)
        ).findById(userTypeId);
    }

    @Test
    @DisplayName("Should return the exact user type returned by the gateway")
    void shouldReturnTheExactUserTypeReturnedByGateway() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserType userType = UserType.create(
                userTypeId,
                "Restaurant Owner",
                true
        );

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(Optional.of(userType));

        // Action
        UserType result = useCase.execute(userTypeId);

        // Assert
        assertSame(userType, result);

        verify(userTypeGateway)
                .findById(userTypeId);
    }
}

