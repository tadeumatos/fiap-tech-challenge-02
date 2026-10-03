package br.com.fiap.tech.challenge._2.application.usecase.foodtype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.domain.FoodType;
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
class GetAllFoodTypeUseCaseImplTest {

    @Mock
    private FoodTypeGateway foodTypeGateway;
    private GetAllFoodTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetAllFoodTypeUseCaseImpl( foodTypeGateway );
    }

    @Test
    @DisplayName("Should return all food types successfully")
    void shouldReturnAllFoodTypesSuccessfully() {
        // Arrange
        FoodType foodType1 = FoodType.create( UUID.randomUUID(), "Brazilian" );
        FoodType foodType2 = FoodType.create( UUID.randomUUID(), "Italian" );
        FoodType foodType3 = FoodType.create( UUID.randomUUID(), "Japanese" );
        List<FoodType> foodTypes = List.of( foodType1, foodType2, foodType3 );
        when(foodTypeGateway.getAll()).thenReturn(foodTypes);

        // Action
        List<FoodType> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(foodType1, result.get(0));
        assertEquals(foodType2, result.get(1));
        assertEquals(foodType3, result.get(2));
        verify(foodTypeGateway) .getAll();
    }

    @Test
    @DisplayName("Should return an empty list when there are no food types")
    void shouldReturnEmptyListWhenThereAreNoFoodTypes() {
        // Arrange
        when(foodTypeGateway.getAll()).thenReturn(List.of());

        // Action
        List<FoodType> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(foodTypeGateway).getAll();
    }

    @Test
    @DisplayName("Should call getAll only once")
    void shouldCallGetAllOnlyOnce() {
        // Arrange
        when(foodTypeGateway.getAll()).thenReturn(List.of());

        // Action
        useCase.execute();

        // Assert
        verify( foodTypeGateway, times(1) ).getAll();
    }

    @Test
    @DisplayName("Should return the exact list returned by the gateway")
    void shouldReturnTheExactListReturnedByGateway() {
        // Arrange
        FoodType foodType = FoodType.create( UUID.randomUUID(), "Brazilian" );
        List<FoodType> foodTypes = List.of(foodType);
        when(foodTypeGateway.getAll()).thenReturn(foodTypes);

        // Action
        List<FoodType> result = useCase.execute();

        // Assert
        assertSame(foodTypes, result); verify(foodTypeGateway).getAll();
    }
}