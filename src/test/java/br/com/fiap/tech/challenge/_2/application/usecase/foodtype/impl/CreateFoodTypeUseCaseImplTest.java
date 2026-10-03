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
class CreateFoodTypeUseCaseImplTest {

    @Mock
    private FoodTypeGateway foodTypeGateway;
    private CreateFoodTypeUseCaseImpl useCase;

    @BeforeEach void setUp() {
        useCase = new CreateFoodTypeUseCaseImpl( foodTypeGateway );
    }

    @Test
    @DisplayName("Should create a food type successfully")
    void shouldCreateFoodTypeSuccessfully() {
        // Arrange
        FoodTypeRequest request = new FoodTypeRequest( "Brazilian" );
        FoodType savedFoodType = FoodType.create( UUID.randomUUID(), request.name() );
        when(foodTypeGateway.save(any(FoodType.class))).thenReturn(savedFoodType);

        // Action
        FoodType result = useCase.execute(request);

        // Assert
        assertNotNull(result);
        assertEquals( savedFoodType.getId(), result.getId() );
        assertEquals( request.name(), result.getName() );
        verify(foodTypeGateway).save(any(FoodType.class));

    }

    @Test
    @DisplayName("Should save food type with the request name")
    void shouldSaveFoodTypeWithRequestName() {
        // Arrange
        FoodTypeRequest request = new FoodTypeRequest( "Brazilian" );
        when(foodTypeGateway.save(any(FoodType.class))).thenAnswer( invocation -> invocation.getArgument(0) );
        ArgumentCaptor<FoodType> foodTypeCaptor = ArgumentCaptor.forClass(FoodType.class);

        // Action
        FoodType result = useCase.execute(request);

        // Assert
        verify(foodTypeGateway).save(foodTypeCaptor.capture());
        FoodType savedFoodType = foodTypeCaptor.getValue();
        assertNotNull(savedFoodType);
        assertEquals( request.name(), savedFoodType.getName() );
        assertEquals( savedFoodType.getId(), result.getId() );

    }

    @Test
    @DisplayName("Should generate a new id when creating a food type")
    void shouldGenerateNewIdWhenCreatingFoodType() {
        // Arrange
        FoodTypeRequest request = new FoodTypeRequest( "Brazilian" );
        when(foodTypeGateway.save(any(FoodType.class))).thenAnswer( invocation -> invocation.getArgument(0) );
        ArgumentCaptor<FoodType> foodTypeCaptor = ArgumentCaptor.forClass(FoodType.class);

        // Action
        useCase.execute(request);

        // Assert
        verify(foodTypeGateway).save(foodTypeCaptor.capture());
        FoodType savedFoodType = foodTypeCaptor.getValue();
        assertNotNull( savedFoodType.getId() );

    }

    @Test
    @DisplayName("Should call save only once")
    void shouldCallSaveOnlyOnce() {
        // Arrange
        FoodTypeRequest request = new FoodTypeRequest( "Brazilian" );
        when(foodTypeGateway.save(any(FoodType.class))).thenAnswer( invocation -> invocation.getArgument(0) );

        // Action
        useCase.execute(request);

        // Assert
        verify( foodTypeGateway, times(1) ).save(any(FoodType.class));

    }

    @Test
    @DisplayName("Should return the exact food type returned by the gateway")
    void shouldReturnTheExactFoodTypeReturnedByGateway() {
        // Arrange
        FoodTypeRequest request = new FoodTypeRequest( "Brazilian" );
        FoodType savedFoodType = FoodType.create( UUID.randomUUID(), request.name() );
        when(foodTypeGateway.save(any(FoodType.class))).thenReturn(savedFoodType);

        // Action
        FoodType result = useCase.execute(request);

        // Assert
        assertSame( savedFoodType, result );
        verify(foodTypeGateway).save(any(FoodType.class));
    }
}