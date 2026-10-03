package br.com.fiap.tech.challenge._2.application.usecase.restaurant.Impl;

import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.domain.Restaurant;
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
class FindRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    private FindRestaurantUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new FindRestaurantUseCaseImpl(
                restaurantGateway
        );
    }

    @Test
    @DisplayName("Should find restaurant successfully")
    void shouldFindRestaurantSuccessfully() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        // Act
        Restaurant result = useCase.execute(restaurantId);

        // Assert
        assertNotNull(result);

        assertSame(
                restaurant,
                result
        );

        verify(restaurantGateway)
                .findById(restaurantId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when restaurant does not exist")
    void shouldThrowResourceNotFoundExceptionWhenRestaurantDoesNotExist() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(restaurantId)
        );

        // Assert
        assertEquals(
                "The restaurant not found",
                exception.getMessage()
        );

        verify(restaurantGateway)
                .findById(restaurantId);
    }

    @Test
    @DisplayName("Should call findById only once")
    void shouldCallFindByIdOnlyOnce() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.empty());

        // Act
        assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(restaurantId)
        );

        // Assert
        verify(
                restaurantGateway,
                times(1)
        ).findById(restaurantId);
    }

    @Test
    @DisplayName("Should return the exact restaurant returned by the gateway")
    void shouldReturnTheExactRestaurantReturnedByGateway() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        // Act
        Restaurant result = useCase.execute(restaurantId);

        // Assert
        assertSame(
                restaurant,
                result
        );

        verify(restaurantGateway)
                .findById(restaurantId);
    }

    @Test
    @DisplayName("Should search restaurant using the provided ID")
    void shouldSearchRestaurantUsingProvidedId() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        // Act
        useCase.execute(restaurantId);

        // Assert
        verify(
                restaurantGateway,
                times(1)
        ).findById(restaurantId);
    }
}

