package br.com.fiap.tech.challenge._2.application.usecase.foodtype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.exceptions.EntityInUseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteFoodTypeUseCaseImplTest {

    @Mock
    private FoodTypeGateway foodTypeGateway;
    @Mock
    private RestaurantGateway restaurantGateway;
    private DeleteFoodTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteFoodTypeUseCaseImpl( foodTypeGateway, restaurantGateway );
    }

    @Test
    @DisplayName("Should delete food type successfully when it is not in use")
    void shouldDeleteFoodTypeSuccessfullyWhenItIsNotInUse() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        when(restaurantGateway.existFoodType(foodTypeId)).thenReturn(false);

        // Action
        useCase.execute(foodTypeId);

        // Assert
        verify(restaurantGateway).existFoodType(foodTypeId);
        verify(foodTypeGateway).delete(foodTypeId);

    }

    @Test
    @DisplayName("Should throw EntityInUseException when food type is in use")
    void shouldThrowEntityInUseExceptionWhenFoodTypeIsInUse() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        when(restaurantGateway.existFoodType(foodTypeId)).thenReturn(true);

        // Action
        EntityInUseException exception = assertThrows( EntityInUseException.class, () -> useCase.execute(foodTypeId) );

        // Assert
        assertEquals( "The cannot be deleted, the register this in use", exception.getMessage() );
        verify(restaurantGateway).existFoodType(foodTypeId);
        verify( foodTypeGateway, never() ).delete(foodTypeId);

    }

    @Test
    @DisplayName("Should check if food type is in use before deleting")
    void shouldCheckIfFoodTypeIsInUseBeforeDeleting() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        when(restaurantGateway.existFoodType(foodTypeId)).thenReturn(false);

        // Action
        useCase.execute(foodTypeId);

        // Assert
        var inOrder = inOrder( restaurantGateway, foodTypeGateway );
        inOrder.verify( restaurantGateway ).existFoodType(foodTypeId);
        inOrder.verify( foodTypeGateway ).delete(foodTypeId);
    }

    @Test
    @DisplayName("Should call existFoodType only once")
    void shouldCallExistFoodTypeOnlyOnce() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        when(restaurantGateway.existFoodType(foodTypeId)).thenReturn(false);

        // Action
        useCase.execute(foodTypeId);

        // Assert
        verify( restaurantGateway, times(1) ).existFoodType(foodTypeId);
        verify( foodTypeGateway, times(1) ).delete(foodTypeId);

    }

    @Test
    @DisplayName("Should not delete food type when it is in use")
    void shouldNotDeleteFoodTypeWhenItIsInUse() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        when(restaurantGateway.existFoodType(foodTypeId)).thenReturn(true);

        // Action
        assertThrows( EntityInUseException.class, () -> useCase.execute(foodTypeId) );

        // Assert
        verify( foodTypeGateway, never()).delete(any(UUID.class));
    }
}