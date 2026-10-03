 package br.com.fiap.tech.challenge._2.application.usecase.restaurant.Impl;

import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.domain.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    private GetAllRestaurantUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetAllRestaurantUseCaseImpl(
                restaurantGateway
        );
    }

    @Test
    @DisplayName("Should return all restaurants successfully")
    void shouldReturnAllRestaurantsSuccessfully() {

        // Arrange
        Restaurant restaurant1 = mock(Restaurant.class);
        Restaurant restaurant2 = mock(Restaurant.class);

        List<Restaurant> restaurants = List.of(
                restaurant1,
                restaurant2
        );

        when(restaurantGateway.getAll())
                .thenReturn(restaurants);

        // Act
        List<Restaurant> result = useCase.execute();

        // Assert
        assertNotNull(result);

        assertEquals(
                2,
                result.size()
        );

        assertEquals(
                restaurant1,
                result.get(0)
        );

        assertEquals(
                restaurant2,
                result.get(1)
        );

        verify(restaurantGateway)
                .getAll();
    }

    @Test
    @DisplayName("Should return an empty list when there are no restaurants")
    void shouldReturnEmptyListWhenThereAreNoRestaurants() {

        // Arrange
        when(restaurantGateway.getAll())
                .thenReturn(List.of());

        // Act
        List<Restaurant> result = useCase.execute();

        // Assert
        assertNotNull(result);

        assertTrue(
                result.isEmpty()
        );

        verify(restaurantGateway)
                .getAll();
    }

    @Test
    @DisplayName("Should call getAll only once")
    void shouldCallGetAllOnlyOnce() {

        // Arrange
        when(restaurantGateway.getAll())
                .thenReturn(List.of());

        // Act
        useCase.execute();

        // Assert
        verify(
                restaurantGateway,
                times(1)
        ).getAll();
    }

    @Test
    @DisplayName("Should return the exact list returned by the gateway")
    void shouldReturnTheExactListReturnedByGateway() {

        // Arrange
        Restaurant restaurant = mock(Restaurant.class);

        List<Restaurant> restaurants = List.of(
                restaurant
        );

        when(restaurantGateway.getAll())
                .thenReturn(restaurants);

        // Act
        List<Restaurant> result = useCase.execute();

        // Assert
        assertSame(
                restaurants,
                result
        );

        verify(restaurantGateway)
                .getAll();
    }

    @Test
    @DisplayName("Should preserve the order returned by the gateway")
    void shouldPreserveTheOrderReturnedByGateway() {

        // Arrange
        Restaurant restaurant1 = mock(Restaurant.class);
        Restaurant restaurant2 = mock(Restaurant.class);
        Restaurant restaurant3 = mock(Restaurant.class);

        List<Restaurant> restaurants = List.of(
                restaurant1,
                restaurant2,
                restaurant3
        );

        when(restaurantGateway.getAll())
                .thenReturn(restaurants);

        // Act
        List<Restaurant> result = useCase.execute();

        // Assert
        assertEquals(
                restaurant1,
                result.get(0)
        );

        assertEquals(
                restaurant2,
                result.get(1)
        );

        assertEquals(
                restaurant3,
                result.get(2)
        );

        verify(restaurantGateway)
                .getAll();
    }
}

