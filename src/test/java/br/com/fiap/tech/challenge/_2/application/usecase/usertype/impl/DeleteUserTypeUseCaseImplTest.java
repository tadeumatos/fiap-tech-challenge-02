package br.com.fiap.tech.challenge._2.application.usecase.usertype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.exceptions.EntityInUseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserTypeUseCaseImplTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @Mock
    private UserGateway userGateway;

    private DeleteUserTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteUserTypeUseCaseImpl(
                userTypeGateway,
                userGateway
        );
    }

    @Test
    @DisplayName("Should delete user type successfully when it is not in use")
    void shouldDeleteUserTypeSuccessfullyWhenItIsNotInUse() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        when(userGateway.existUserType(userTypeId))
                .thenReturn(false);

        // Action
        useCase.execute(userTypeId);

        // Assert
        verify(userGateway)
                .existUserType(userTypeId);

        verify(userTypeGateway)
                .delete(userTypeId);
    }

    @Test
    @DisplayName("Should throw EntityInUseException when user type is in use")
    void shouldThrowEntityInUseExceptionWhenUserTypeIsInUse() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        when(userGateway.existUserType(userTypeId))
                .thenReturn(true);

        // Action
        EntityInUseException exception = assertThrows(
                EntityInUseException.class,
                () -> useCase.execute(userTypeId)
        );

        // Assert
        verify(userGateway)
                .existUserType(userTypeId);

        verify(
                userTypeGateway,
                never()
        ).delete(userTypeId);

        assert exception.getMessage() != null;
        org.junit.jupiter.api.Assertions.assertEquals(
                "The cannot be deleted, the register this in use",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Should validate user type usage before deleting")
    void shouldValidateUserTypeUsageBeforeDeleting() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        when(userGateway.existUserType(userTypeId))
                .thenReturn(false);

        // Action
        useCase.execute(userTypeId);

        // Assert
        InOrder inOrder = inOrder(
                userGateway,
                userTypeGateway
        );

        inOrder.verify(userGateway)
                .existUserType(userTypeId);

        inOrder.verify(userTypeGateway)
                .delete(userTypeId);
    }

    @Test
    @DisplayName("Should call existUserType only once")
    void shouldCallExistUserTypeOnlyOnce() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        when(userGateway.existUserType(userTypeId))
                .thenReturn(false);

        // Action
        useCase.execute(userTypeId);

        // Assert
        verify(
                userGateway,
                times(1)
        ).existUserType(userTypeId);
    }

    @Test
    @DisplayName("Should not delete user type when it is in use")
    void shouldNotDeleteUserTypeWhenItIsInUse() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        when(userGateway.existUserType(userTypeId))
                .thenReturn(true);

        // Action
        assertThrows(
                EntityInUseException.class,
                () -> useCase.execute(userTypeId)
        );

        // Assert
        verify(
                userTypeGateway,
                never()
        ).delete(any(UUID.class));
    }
}

