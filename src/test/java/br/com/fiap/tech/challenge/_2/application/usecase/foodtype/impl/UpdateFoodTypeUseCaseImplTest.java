package br.com.fiap.tech.challenge._2.application.usecase.foodtype.impl;

import br.com.fiap.tech.challenge._2.application.gateway.FoodTypeGateway;
import br.com.fiap.tech.challenge._2.domain.FoodType;
import br.com.fiap.tech.challenge._2.presentation.controller.request.FoodTypeRequest;
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
class UpdateFoodTypeUseCaseImplTest {

    @Mock
    private FoodTypeGateway foodTypeGateway;
    private UpdateFoodTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateFoodTypeUseCaseImpl( foodTypeGateway );
    }

    @Test
    @DisplayName("Should update food type successfully")
    void shouldUpdateFoodTypeSuccessfully() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        FoodTypeRequest request = new FoodTypeRequest( "Brazilian Food" );
        FoodType updatedFoodType = FoodType.create( foodTypeId, request.name() );
        when(foodTypeGateway.update( eq(foodTypeId), any(FoodType.class) )).thenReturn(updatedFoodType);

        // Action
        FoodType result = useCase.execute( foodTypeId, request );

        // Assert
        assertNotNull(result);
        assertEquals(foodTypeId, result.getId());
        assertEquals("Brazilian Food", result.getName());
        verify(foodTypeGateway).update( eq(foodTypeId), any(FoodType.class) );

    }

    @Test
    @DisplayName("Should create food type using the provided id")
    void shouldCreateFoodTypeUsingProvidedId() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        FoodTypeRequest request = new FoodTypeRequest( "Italian Food" );
        FoodType gatewayResult = FoodType.create( foodTypeId, request.name() );
        when(foodTypeGateway.update( eq(foodTypeId), any(FoodType.class) )).thenReturn(gatewayResult);
        ArgumentCaptor<FoodType> foodTypeCaptor = ArgumentCaptor.forClass(FoodType.class);

        // Action
        useCase.execute( foodTypeId, request );

        // Assert
        verify(foodTypeGateway).update( eq(foodTypeId), foodTypeCaptor.capture() );
        FoodType capturedFoodType = foodTypeCaptor.getValue();
        assertEquals( foodTypeId, capturedFoodType.getId() );

    }

    @Test
    @DisplayName("Should create food type using the name from the request")
    void shouldCreateFoodTypeUsingNameFromRequest() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        FoodTypeRequest request = new FoodTypeRequest( "Japanese Food" );
        ArgumentCaptor<FoodType> foodTypeCaptor = ArgumentCaptor.forClass(FoodType.class);
        FoodType gatewayResult = FoodType.create( foodTypeId, request.name() );
        when(foodTypeGateway.update( eq(foodTypeId), any(FoodType.class) )).thenReturn(gatewayResult);

        // Action
        useCase.execute( foodTypeId, request );

        // Assert
        verify(foodTypeGateway).update( eq(foodTypeId), foodTypeCaptor.capture() );
        FoodType capturedFoodType = foodTypeCaptor.getValue();
        assertEquals( "Japanese Food", capturedFoodType.getName() );

    }

    @Test
    @DisplayName("Should pass the same id to the gateway update method")
    void shouldPassSameIdToGatewayUpdateMethod() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        FoodTypeRequest request = new FoodTypeRequest( "Mexican Food" );
        FoodType gatewayResult = FoodType.create( foodTypeId, request.name() );
        when(foodTypeGateway.update( eq(foodTypeId), any(FoodType.class) )).thenReturn(gatewayResult);

        // Action
        useCase.execute( foodTypeId, request );

        // Assert
        verify( foodTypeGateway, times(1) ).update( eq(foodTypeId), any(FoodType.class) );

    }

    @Test
    @DisplayName("Should return the exact food type returned by the gateway")
    void shouldReturnTheExactFoodTypeReturnedByGateway() {
        // Arrange
        UUID foodTypeId = UUID.randomUUID();
        FoodTypeRequest request = new FoodTypeRequest( "French Food" );
        FoodType updatedFoodType = FoodType.create( foodTypeId, request.name() );
        when(foodTypeGateway.update( eq(foodTypeId), any(FoodType.class) )).thenReturn(updatedFoodType);

        // Action
        FoodType result = useCase.execute( foodTypeId, request );

        // Assert
        assertSame( updatedFoodType, result );
        verify(foodTypeGateway).update( eq(foodTypeId), any(FoodType.class) );
    }

}