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
class UpdateUserTypeUseCaseImplTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    private UpdateUserTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateUserTypeUseCaseImpl(
                userTypeGateway
        );
    }

    @Test
    @DisplayName("Should update user type successfully")
    void shouldUpdateUserTypeSuccessfully() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserTypeRequest request = new UserTypeRequest(
                "Administrator",
                true
        );

        UserType updatedUserType = UserType.create(
                userTypeId,
                request.name(),
                request.owner()
        );

        when(userTypeGateway.update(
                eq(userTypeId),
                any(UserType.class)
        )).thenReturn(updatedUserType);

        // Action
        UserType result = useCase.execute(
                userTypeId,
                request
        );

        // Assert
        assertNotNull(result);
        assertEquals(userTypeId, result.getId());
        assertEquals("Administrator", result.getName());
        assertTrue(result.isOwner());

        verify(userTypeGateway)
                .update(
                        eq(userTypeId),
                        any(UserType.class)
                );
    }

    @Test
    @DisplayName("Should create user type using the provided id")
    void shouldCreateUserTypeUsingProvidedId() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserTypeRequest request = new UserTypeRequest(
                "Manager",
                true
        );

        UserType gatewayResult = UserType.create(
                userTypeId,
                request.name(),
                request.owner()
        );

        when(userTypeGateway.update(
                eq(userTypeId),
                any(UserType.class)
        )).thenReturn(gatewayResult);

        ArgumentCaptor<UserType> userTypeCaptor =
                ArgumentCaptor.forClass(UserType.class);

        // Action
        useCase.execute(
                userTypeId,
                request
        );

        // Assert
        verify(userTypeGateway)
                .update(
                        eq(userTypeId),
                        userTypeCaptor.capture()
                );

        UserType capturedUserType =
                userTypeCaptor.getValue();

        assertEquals(
                userTypeId,
                capturedUserType.getId()
        );
    }

    @Test
    @DisplayName("Should create user type using the name from the request")
    void shouldCreateUserTypeUsingNameFromRequest() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserTypeRequest request = new UserTypeRequest(
                "Restaurant Owner",
                true
        );

        UserType gatewayResult = UserType.create(
                userTypeId,
                request.name(),
                request.owner()
        );

        when(userTypeGateway.update(
                eq(userTypeId),
                any(UserType.class)
        )).thenReturn(gatewayResult);

        ArgumentCaptor<UserType> userTypeCaptor =
                ArgumentCaptor.forClass(UserType.class);

        // Action
        useCase.execute(
                userTypeId,
                request
        );

        // Assert
        verify(userTypeGateway)
                .update(
                        eq(userTypeId),
                        userTypeCaptor.capture()
                );

        UserType capturedUserType =
                userTypeCaptor.getValue();

        assertEquals(
                "Restaurant Owner",
                capturedUserType.getName()
        );
    }

    @Test
    @DisplayName("Should create user type using owner value from the request")
    void shouldCreateUserTypeUsingOwnerValueFromRequest() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserTypeRequest request = new UserTypeRequest(
                "Customer",
                false
        );

        UserType gatewayResult = UserType.create(
                userTypeId,
                request.name(),
                request.owner()
        );

        when(userTypeGateway.update(
                eq(userTypeId),
                any(UserType.class)
        )).thenReturn(gatewayResult);

        ArgumentCaptor<UserType> userTypeCaptor =
                ArgumentCaptor.forClass(UserType.class);

        // Action
        useCase.execute(
                userTypeId,
                request
        );

        // Assert
        verify(userTypeGateway)
                .update(
                        eq(userTypeId),
                        userTypeCaptor.capture()
                );

        UserType capturedUserType =
                userTypeCaptor.getValue();

        assertFalse(capturedUserType.isOwner());
    }

    @Test
    @DisplayName("Should pass the same id to the gateway update method")
    void shouldPassSameIdToGatewayUpdateMethod() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserTypeRequest request = new UserTypeRequest(
                "Employee",
                false
        );

        UserType gatewayResult = UserType.create(
                userTypeId,
                request.name(),
                request.owner()
        );

        when(userTypeGateway.update(
                eq(userTypeId),
                any(UserType.class)
        )).thenReturn(gatewayResult);

        // Action
        useCase.execute(
                userTypeId,
                request
        );

        // Assert
        verify(
                userTypeGateway,
                times(1)
        ).update(
                eq(userTypeId),
                any(UserType.class)
        );
    }

    @Test
    @DisplayName("Should return the exact user type returned by the gateway")
    void shouldReturnTheExactUserTypeReturnedByGateway() {

        // Arrange
        UUID userTypeId = UUID.randomUUID();

        UserTypeRequest request = new UserTypeRequest(
                "Administrator",
                true
        );

        UserType updatedUserType = UserType.create(
                userTypeId,
                request.name(),
                request.owner()
        );

        when(userTypeGateway.update(
                eq(userTypeId),
                any(UserType.class)
        )).thenReturn(updatedUserType);

        // Action
        UserType result = useCase.execute(
                userTypeId,
                request
        );

        // Assert
        assertSame(
                updatedUserType,
                result
        );

        verify(userTypeGateway)
                .update(
                        eq(userTypeId),
                        any(UserType.class)
                );
    }
}

