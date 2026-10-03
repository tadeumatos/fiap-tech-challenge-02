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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private UserTypeGateway userTypeGateway;

    private CreateUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateUserUseCaseImpl(
                userGateway,
                userTypeGateway
        );
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserRequest request = new UserRequest(
                "John Silva",
                "john@email.com",
                userTypeId.toString()
        );

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        when(userGateway.existEmail(request.email()))
                .thenReturn(false);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(java.util.Optional.of(userType));

        when(userGateway.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Action
        User result = useCase.execute(request);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());

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
                userTypeId,
                result.getUserType().getId()
        );

        assertEquals(
                "Administrator",
                result.getUserType().getName()
        );

        assertTrue(
                result.getUserType().isOwner()
        );

        verify(userGateway)
                .existEmail(request.email());

        verify(userTypeGateway)
                .findById(userTypeId);

        verify(userGateway)
                .save(any(User.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when email already exists")
    void shouldThrowBusinessExceptionWhenEmailAlreadyExists() {

        // Arrange
        UserRequest request = new UserRequest(
                "John Silva",
                "john@email.com",
                UUID.randomUUID().toString()
        );

        when(userGateway.existEmail(request.email()))
                .thenReturn(true);

        // Action
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> useCase.execute(request)
        );

        // Assert
        assertEquals(
                "Already email with register, please choice other email",
                exception.getMessage()
        );

        verify(userGateway)
                .existEmail(request.email());

        verify(
                userTypeGateway,
                never()
        ).findById(any(UUID.class));

        verify(
                userGateway,
                never()
        ).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user type does not exist")
    void shouldThrowResourceNotFoundExceptionWhenUserTypeDoesNotExist() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserRequest request = new UserRequest(
                "John Silva",
                "john@email.com",
                userTypeId.toString()
        );

        when(userGateway.existEmail(request.email()))
                .thenReturn(false);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(java.util.Optional.empty());

        // Action
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(request)
        );

        // Assert
        assertEquals(
                "Register not found",
                exception.getMessage()
        );

        verify(userGateway)
                .existEmail(request.email());

        verify(userTypeGateway)
                .findById(userTypeId);

        verify(
                userGateway,
                never()
        ).save(any(User.class));
    }

    @Test
    @DisplayName("Should create user using data from request")
    void shouldCreateUserUsingDataFromRequest() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserRequest request = new UserRequest(
                "Maria Silva",
                "maria@email.com",
                userTypeId.toString()
        );

        UserType userType = UserType.create(
                userTypeId,
                "Customer",
                false
        );

        when(userGateway.existEmail(request.email()))
                .thenReturn(false);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(java.util.Optional.of(userType));

        when(userGateway.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        // Action
        useCase.execute(request);

        // Assert
        verify(userGateway)
                .save(userCaptor.capture());

        User capturedUser =
                userCaptor.getValue();

        assertNotNull(capturedUser.getId());

        assertEquals(
                "Maria Silva",
                capturedUser.getName()
        );

        assertEquals(
                "maria@email.com",
                capturedUser.getEmail()
        );

        assertEquals(
                userTypeId,
                capturedUser.getUserType().getId()
        );

        assertEquals(
                "Customer",
                capturedUser.getUserType().getName()
        );

        assertFalse(
                capturedUser.getUserType().isOwner()
        );
    }

    @Test
    @DisplayName("Should generate a new id when creating user")
    void shouldGenerateNewIdWhenCreatingUser() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserRequest request = new UserRequest(
                "John Silva",
                "john@email.com",
                userTypeId.toString()
        );

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        when(userGateway.existEmail(request.email()))
                .thenReturn(false);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(java.util.Optional.of(userType));

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        when(userGateway.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Action
        useCase.execute(request);

        // Assert
        verify(userGateway)
                .save(userCaptor.capture());

        assertNotNull(
                userCaptor.getValue().getId()
        );
    }

    @Test
    @DisplayName("Should copy user type data to the created user")
    void shouldCopyUserTypeDataToCreatedUser() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserRequest request = new UserRequest(
                "John Silva",
                "john@email.com",
                userTypeId.toString()
        );

        UserType userType = UserType.create(
                userTypeId,
                "Restaurant Owner",
                true
        );

        when(userGateway.existEmail(request.email()))
                .thenReturn(false);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(java.util.Optional.of(userType));

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        when(userGateway.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Action
        useCase.execute(request);

        // Assert
        verify(userGateway)
                .save(userCaptor.capture());

        User capturedUser =
                userCaptor.getValue();

        assertNotSame(
                userType,
                capturedUser.getUserType()
        );

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
    }

    @Test
    @DisplayName("Should validate email before searching for user type")
    void shouldValidateEmailBeforeSearchingForUserType() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserRequest request = new UserRequest(
                "John Silva",
                "john@email.com",
                userTypeId.toString()
        );

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        when(userGateway.existEmail(request.email()))
                .thenReturn(false);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(java.util.Optional.of(userType));

        when(userGateway.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Action
        useCase.execute(request);

        // Assert
        InOrder inOrder = inOrder(
                userGateway,
                userTypeGateway
        );

        inOrder.verify(userGateway)
                .existEmail(request.email());

        inOrder.verify(userTypeGateway)
                .findById(userTypeId);

        inOrder.verify(userGateway)
                .save(any(User.class));
    }

    @Test
    @DisplayName("Should call existEmail only once")
    void shouldCallExistEmailOnlyOnce() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserRequest request = new UserRequest(
                "John Silva",
                "john@email.com",
                userTypeId.toString()
        );

        UserType userType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        when(userGateway.existEmail(request.email()))
                .thenReturn(false);

        when(userTypeGateway.findById(userTypeId))
                .thenReturn(java.util.Optional.of(userType));

        when(userGateway.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Action
        useCase.execute(request);

        // Assert
        verify(
                userGateway,
                times(1)
        ).existEmail(request.email());
    }

    @Test
    @DisplayName("Should not save user when email already exists")
    void shouldNotSaveUserWhenEmailAlreadyExists() {

        // Arrange
        UserRequest request = new UserRequest(
                "John Silva",
                "john@email.com",
                UUID.randomUUID().toString()
        );

        when(userGateway.existEmail(request.email()))
                .thenReturn(true);

        // Action
        assertThrows(
                BusinessException.class,
                () -> useCase.execute(request)
        );

        // Assert
        verify(
                userGateway,
                never()
        ).save(any(User.class));
    }
}

