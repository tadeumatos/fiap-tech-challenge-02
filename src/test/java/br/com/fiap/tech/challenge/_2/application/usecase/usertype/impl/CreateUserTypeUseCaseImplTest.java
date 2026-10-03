package br.com.fiap.tech.challenge._2.application.usecase.usertype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
import br.com.fiap.tech.challenge._2.domain.UserType;
import br.com.fiap.tech.challenge._2.presentation.controller.request.UserTypeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserTypeUseCaseImplTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    private CreateUserTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateUserTypeUseCaseImpl(
                userTypeGateway
        );
    }

    @Test
    @DisplayName("Should create user type successfully")
    void shouldCreateUserTypeSuccessfully() {

        // Arrange
        UserTypeRequest request = new UserTypeRequest(
                "Administrator",
                true
        );

        ArgumentCaptor<UserType> userTypeCaptor =
                ArgumentCaptor.forClass(UserType.class);

        when(userTypeGateway.save(any(UserType.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Action
        UserType result = useCase.execute(request);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Administrator", result.getName());
        assertTrue(result.isOwner());

        verify(userTypeGateway)
                .save(userTypeCaptor.capture());

        UserType capturedUserType =
                userTypeCaptor.getValue();

        assertNotNull(capturedUserType.getId());
        assertEquals("Administrator", capturedUserType.getName());
        assertTrue(capturedUserType.isOwner());
    }

    @Test
    @DisplayName("Should generate a new id when creating user type")
    void shouldGenerateNewIdWhenCreatingUserType() {

        // Arrange
        UserTypeRequest request = new UserTypeRequest(
                "Employee",
                false
        );

        when(userTypeGateway.save(any(UserType.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<UserType> userTypeCaptor =
                ArgumentCaptor.forClass(UserType.class);

        // Action
        useCase.execute(request);

        // Assert
        verify(userTypeGateway)
                .save(userTypeCaptor.capture());

        UserType capturedUserType =
                userTypeCaptor.getValue();

        assertNotNull(capturedUserType.getId());
    }

    @Test
    @DisplayName("Should create user type using the name from request")
    void shouldCreateUserTypeUsingNameFromRequest() {

        // Arrange
        UserTypeRequest request = new UserTypeRequest(
                "Manager",
                false
        );

        when(userTypeGateway.save(any(UserType.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<UserType> userTypeCaptor =
                ArgumentCaptor.forClass(UserType.class);

        // Action
        useCase.execute(request);

        // Assert
        verify(userTypeGateway)
                .save(userTypeCaptor.capture());

        UserType capturedUserType =
                userTypeCaptor.getValue();

        assertEquals(
                "Manager",
                capturedUserType.getName()
        );
    }

    @Test
    @DisplayName("Should create user type using owner value from request")
    void shouldCreateUserTypeUsingOwnerValueFromRequest() {

        // Arrange
        UserTypeRequest request = new UserTypeRequest(
                "Restaurant Owner",
                true
        );

        when(userTypeGateway.save(any(UserType.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<UserType> userTypeCaptor =
                ArgumentCaptor.forClass(UserType.class);

        // Action
        useCase.execute(request);

        // Assert
        verify(userTypeGateway)
                .save(userTypeCaptor.capture());

        UserType capturedUserType =
                userTypeCaptor.getValue();

        assertTrue(capturedUserType.isOwner());
    }

    @Test
    @DisplayName("Should create user type with owner set to false")
    void shouldCreateUserTypeWithOwnerSetToFalse() {

        // Arrange
        UserTypeRequest request = new UserTypeRequest(
                "Customer",
                false
        );

        when(userTypeGateway.save(any(UserType.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<UserType> userTypeCaptor =
                ArgumentCaptor.forClass(UserType.class);

        // Action
        useCase.execute(request);

        // Assert
        verify(userTypeGateway)
                .save(userTypeCaptor.capture());

        UserType capturedUserType =
                userTypeCaptor.getValue();

        assertFalse(capturedUserType.isOwner());
    }

    @Test
    @DisplayName("Should call save only once")
    void shouldCallSaveOnlyOnce() {

        // Arrange
        UserTypeRequest request = new UserTypeRequest(
                "Administrator",
                true
        );

        when(userTypeGateway.save(any(UserType.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Action
        useCase.execute(request);

        // Assert
        verify(
                userTypeGateway,
                times(1)
        ).save(any(UserType.class));
    }

    @Test
    @DisplayName("Should return the exact user type returned by the gateway")
    void shouldReturnTheExactUserTypeReturnedByGateway() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserType savedUserType = UserType.create(
                userTypeId,
                "Administrator",
                true
        );

        UserTypeRequest request = new UserTypeRequest(
                "Administrator",
                true
        );

        when(userTypeGateway.save(any(UserType.class)))
                .thenReturn(savedUserType);

        // Action
        UserType result = useCase.execute(request);

        // Assert
        assertSame(
                savedUserType,
                result
        );

        verify(userTypeGateway)
                .save(any(UserType.class));
    }
}

