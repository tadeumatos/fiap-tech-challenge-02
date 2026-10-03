package br.com.fiap.tech.challenge._2.application.usecase.usertype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.UserTypeGateway;
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
class GetAllUserTypeUseCaseImplTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    private GetAllUserTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetAllUserTypeUseCaseImpl(
                userTypeGateway
        );
    }

    @Test
    @DisplayName("Should return all user types successfully")
    void shouldReturnAllUserTypesSuccessfully() {

        // Arrange
        UserType userType1 = UserType.create(
                UUID.randomUUID(),
                "Administrator",
                true
        );

        UserType userType2 = UserType.create(
                UUID.randomUUID(),
                "Restaurant Owner",
                true
        );

        UserType userType3 = UserType.create(
                UUID.randomUUID(),
                "Customer",
                false
        );

        List<UserType> userTypes = List.of(
                userType1,
                userType2,
                userType3
        );

        when(userTypeGateway.getAll())
                .thenReturn(userTypes);

        // Action
        List<UserType> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());

        assertEquals(userType1, result.get(0));
        assertEquals(userType2, result.get(1));
        assertEquals(userType3, result.get(2));

        verify(userTypeGateway)
                .getAll();
    }

    @Test
    @DisplayName("Should return an empty list when there are no user types")
    void shouldReturnEmptyListWhenThereAreNoUserTypes() {

        // Arrange
        when(userTypeGateway.getAll())
                .thenReturn(List.of());

        // Action
        List<UserType> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userTypeGateway)
                .getAll();
    }

    @Test
    @DisplayName("Should call getAll only once")
    void shouldCallGetAllOnlyOnce() {

        // Arrange
        when(userTypeGateway.getAll())
                .thenReturn(List.of());

        // Action
        useCase.execute();

        // Assert
        verify(
                userTypeGateway,
                times(1)
        ).getAll();
    }

    @Test
    @DisplayName("Should return the exact list returned by the gateway")
    void shouldReturnTheExactListReturnedByGateway() {

        // Arrange
        UserType userType = UserType.create(
                UUID.randomUUID(),
                "Administrator",
                true
        );

        List<UserType> userTypes = List.of(userType);

        when(userTypeGateway.getAll())
                .thenReturn(userTypes);

        // Action
        List<UserType> result = useCase.execute();

        // Assert
        assertSame(userTypes, result);

        verify(userTypeGateway)
                .getAll();
    }
}

