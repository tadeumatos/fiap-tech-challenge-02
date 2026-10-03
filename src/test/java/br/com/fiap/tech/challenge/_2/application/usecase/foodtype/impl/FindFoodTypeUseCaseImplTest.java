package br.com.fiap.tech.challenge._2.application.usecase.foodtype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.domain.FoodType;
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
class FindFoodTypeUseCaseImplTest {

    @Mock
    private FoodTypeGateway foodTypeGateway;
    private FindFoodTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new FindFoodTypeUseCaseImpl( foodTypeGateway );
    }

    @Test
    @DisplayName("Should find food type successfully")
    void shouldFindFoodTypeSuccessfully() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        FoodType foodType = FoodType.create( foodTypeId, "Brazilian" );
        when(foodTypeGateway.findById(foodTypeId)).thenReturn(Optional.of(foodType));

        // Action
        FoodType result = useCase.execute(foodTypeId);

        // Assert
        assertNotNull(result);
        assertEquals(foodTypeId, result.getId());
        assertEquals("Brazilian", result.getName());
        verify(foodTypeGateway).findById(foodTypeId);

    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when food type does not exist")
    void shouldThrowResourceNotFoundExceptionWhenFoodTypeDoesNotExist() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        when(foodTypeGateway.findById(foodTypeId)).thenReturn(Optional.empty());

        // Action
        ResourceNotFoundException exception = assertThrows( ResourceNotFoundException.class, () -> useCase.execute(foodTypeId) );

        // Assert
        assertEquals( "Food Type not found", exception.getMessage() );
        verify(foodTypeGateway).findById(foodTypeId);
    }

    @Test
    @DisplayName("Should call findById only once")
    void shouldCallFindByIdOnlyOnce() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        when(foodTypeGateway.findById(foodTypeId)).thenReturn(Optional.empty());

        // Action
        assertThrows( ResourceNotFoundException.class, () -> useCase.execute(foodTypeId) );

        // Assert
        verify( foodTypeGateway, times(1) ).findById(foodTypeId);

    }

    @Test
    @DisplayName("Should return the exact food type returned by the gateway")
    void shouldReturnTheExactFoodTypeReturnedByGateway() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        FoodType foodType = FoodType.create( foodTypeId, "Italian" );
        when(foodTypeGateway.findById(foodTypeId)).thenReturn(Optional.of(foodType));

        // Act
        FoodType result = useCase.execute(foodTypeId);

        // Assert
        assertSame(foodType, result);
        verify(foodTypeGateway).findById(foodTypeId);
    }

}