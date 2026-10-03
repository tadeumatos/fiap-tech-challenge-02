package br.com.fiap.tech.challenge._2.application.usecase.user.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserGateway;
import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.domain.User;
import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.exceptions.BusinessException;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private UserTypeGateway userTypeGateway;

    private UpdateUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateUserUseCaseImpl(
                userGateway,
                userTypeGateway
        );
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserSuccessfully() {

        // Arrange
        UUID userId = UUID.randomUUID();
        UUID userTypeId = UUID.randomUUID();

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        UserRequest request = new UserRequest(
                "John Updated",
                "john.updated@email.com",
                userTypeId.toString()
        );

        User updatedUser = User.create(
                UUID.randomUUID(),
                request.name(),
                request.email(),
                userType
        );

        when(userGateway.findByEmail(request.email()))
                .thenReturn(null);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(Optional.of(userType));

        when(userGateway.update(
                eq(userId),
                any(User.class)
        )).thenReturn(updatedUser);

        // Act
        User result = useCase.execute(
                userId,
                request
        );

        // Assert
        assertNotNull(result);
        assertEquals(
                updatedUser,
                result
        );

        verify(userGateway)
                .findByEmail(request.email());

        verify(userTypeGateway)
                .findById(userTypeId);

        verify(userGateway)
                .update(
                        eq(userId),
                        any(User.class)
                );
    }

    @Test
    @DisplayName("Should allow updating user while keeping own email")
    void shouldAllowUpdatingUserWithOwnEmail() {

        // Arrange
        UUID userId = UUID.randomUUID();
        UUID userTypeId = UUID.randomUUID();

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        User existingUser = User.create(
                userId,
                "John Silva",
                "john@email.com",
                userType
        );

        UserRequest request = new UserRequest(
                "John Updated",
                "john@email.com",
                userTypeId.toString()
        );

        User updatedUser = User.create(
                UUID.randomUUID(),
                request.name(),
                request.email(),
                userType
        );

        when(userGateway.findByEmail(request.email()))
                .thenReturn(existingUser);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(Optional.of(userType));

        when(userGateway.update(
                eq(userId),
                any(User.class)
        )).thenReturn(updatedUser);

        // Act
        User result = useCase.execute(
                userId,
                request
        );

        // Assert
        assertNotNull(result);
        assertEquals(
                updatedUser,
                result
        );

        verify(userGateway)
                .findByEmail(request.email());

        verify(userTypeGateway)
                .findById(userTypeId);

        verify(userGateway)
                .update(
                        eq(userId),
                        any(User.class)
                );
    }

    @Test
    @DisplayName("Should throw BusinessException when email belongs to another user")
    void shouldThrowBusinessExceptionWhenEmailBelongsToAnotherUser() {

        // Arrange
        UUID userId = UUID.randomUUID();
        UUID anotherUserId = UUID.randomUUID();

        UUID userTypeId = UUID.randomUUID();

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        User anotherUser = User.create(
                anotherUserId,
                "Maria Silva",
                "maria@email.com",
                userType
        );

        UserRequest request = new UserRequest(
                "John Updated",
                "maria@email.com",
                userTypeId.toString()
        );

        when(userGateway.findByEmail(request.email()))
                .thenReturn(anotherUser);

        // Act
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> useCase.execute(
                        userId,
                        request
                )
        );

        // Assert
        assertEquals(
                "Already exist email with other register, please choice other email",
                exception.getMessage()
        );

        verify(userGateway)
                .findByEmail(request.email());

        verify(userTypeGateway, never())
                .findById(any(UUID.class));

        verify(userGateway, never())
                .update(
                        any(UUID.class),
                        any(User.class)
                );
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user type does not exist")
    void shouldThrowResourceNotFoundExceptionWhenUserTypeDoesNotExist() {

        // Arrange
        UUID userId = UUID.randomUUID();
        UUID userTypeId = UUID.randomUUID();

        UserRequest request = new UserRequest(
                "John Updated",
                "john.updated@email.com",
                userTypeId.toString()
        );

        when(userGateway.findByEmail(request.email()))
                .thenReturn(null);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(
                        userId,
                        request
                )
        );

        // Assert
        assertEquals(
                "Register not found",
                exception.getMessage()
        );

        verify(userGateway)
                .findByEmail(request.email());

        verify(userTypeGateway)
                .findById(userTypeId);

        verify(userGateway, never())
                .update(
                        any(UUID.class),
                        any(User.class)
                );
    }

    @Test
    @DisplayName("Should create the updated user with a new generated ID")
    void shouldCreateUpdatedUserWithNewGeneratedId() {

        // Arrange
        UUID userId = UUID.randomUUID();
        UUID userTypeId = UUID.randomUUID();

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        UserRequest request = new UserRequest(
                "John Updated",
                "john.updated@email.com",
                userTypeId.toString()
        );

        when(userGateway.findByEmail(request.email()))
                .thenReturn(null);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(Optional.of(userType));

        when(userGateway.update(
                eq(userId),
                any(User.class)
        )).thenAnswer(invocation -> invocation.getArgument(1));

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        // Act
        User result = useCase.execute(
                userId,
                request
        );

        // Assert
        verify(userGateway)
                .update(
                        eq(userId),
                        userCaptor.capture()
                );

        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser.getId());

        assertNotEquals(
                userId,
                capturedUser.getId()
        );

        assertEquals(
                request.name(),
                capturedUser.getName()
        );

        assertEquals(
                request.email(),
                capturedUser.getEmail()
        );

        assertEquals(
                result,
                capturedUser
        );
    }

    @Test
    @DisplayName("Should copy user type data when updating user")
    void shouldCopyUserTypeDataWhenUpdatingUser() {

        // Arrange
        UUID userId = UUID.randomUUID();
        UUID userTypeId = UUID.randomUUID();

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        UserRequest request = new UserRequest(
                "John Updated",
                "john.updated@email.com",
                userTypeId.toString()
        );

        when(userGateway.findByEmail(request.email()))
                .thenReturn(null);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(Optional.of(userType));

        when(userGateway.update(
                eq(userId),
                any(User.class)
        )).thenAnswer(invocation -> invocation.getArgument(1));

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        // Act
        useCase.execute(
                userId,
                request
        );

        // Assert
        verify(userGateway)
                .update(
                        eq(userId),
                        userCaptor.capture()
                );

        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser.getUserType());

        assertEquals(
                userType.getId(),
                capturedUser.getUserType().getId()
        );

        assertEquals(
                userType.getName(),
                capturedUser.getUserType().getName()
        );

        assertEquals(
                userType.isOwner(),
                capturedUser.getUserType().isOwner()
        );

        assertNotSame(
                userType,
                capturedUser.getUserType()
        );
    }

    @Test
    @DisplayName("Should validate email before searching for user type")
    void shouldValidateEmailBeforeSearchingForUserType() {

        // Arrange
        UUID userId = UUID.randomUUID();
        UUID userTypeId = UUID.randomUUID();

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        UserRequest request = new UserRequest(
                "John Updated",
                "john.updated@email.com",
                userTypeId.toString()
        );

        when(userGateway.findByEmail(request.email()))
                .thenReturn(null);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(Optional.of(userType));

        when(userGateway.update(
                eq(userId),
                any(User.class)
        )).thenAnswer(invocation -> invocation.getArgument(1));

        InOrder inOrder = inOrder(
                userGateway,
                userTypeGateway
        );

        // Act
        useCase.execute(
                userId,
                request
        );

        // Assert
        inOrder.verify(userGateway)
                .findByEmail(request.email());

        inOrder.verify(userTypeGateway)
                .findById(userTypeId);

        inOrder.verify(userGateway)
                .update(
                        eq(userId),
                        any(User.class)
                );
    }

    @Test
    @DisplayName("Should call findByEmail only once")
    void shouldCallFindByEmailOnlyOnce() {

        // Arrange
        UUID userId = UUID.randomUUID();
        UUID userTypeId = UUID.randomUUID();

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        UserRequest request = new UserRequest(
                "John Updated",
                "john.updated@email.com",
                userTypeId.toString()
        );

        when(userGateway.findByEmail(request.email()))
                .thenReturn(null);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(Optional.of(userType));

        when(userGateway.update(
                eq(userId),
                any(User.class)
        )).thenAnswer(invocation -> invocation.getArgument(1));

        // Act
        useCase.execute(
                userId,
                request
        );

        // Assert
        verify(
                userGateway,
                times(1)
        ).findByEmail(request.email());
    }

    @Test
    @DisplayName("Should call findById on user type gateway only once")
    void shouldCallFindByIdOnUserTypeGatewayOnlyOnce() {

        // Arrange
        UUID userId = UUID.randomUUID();
        UUID userTypeId = UUID.randomUUID();

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        UserRequest request = new UserRequest(
                "John Updated",
                "john.updated@email.com",
                userTypeId.toString()
        );

        when(userGateway.findByEmail(request.email()))
                .thenReturn(null);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(Optional.of(userType));

        when(userGateway.update(
                eq(userId),
                any(User.class)
        )).thenAnswer(invocation -> invocation.getArgument(1));

        // Act
        useCase.execute(
                userId,
                request
        );

        // Assert
        verify(
                userTypeGateway,
                times(1)
        ).findById(userTypeId);
    }
}

