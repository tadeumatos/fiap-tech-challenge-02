package br.com.fiap.tech.challenge._2.application.usecase.address.impl;

import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
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
class DeleteAddressUseCaseImplTest {

    @Mock
    private AddressGateway addressGateway;
    @Mock
    private RestaurantGateway restaurantGatewayGateway;
    private DeleteAddressUseCaseImpl useCase;

    @BeforeEach void setUp() {
        useCase = new DeleteAddressUseCaseImpl( addressGateway, restaurantGatewayGateway );
    }

    @Test
    @DisplayName("Should delete address successfully when it is not in use")
    void shouldDeleteAddressSuccessfullyWhenAddressIsNotInUse() {
        // Arrange
        UUID addressId = UUID.randomUUID();
        when(restaurantGatewayGateway.existAddress(addressId)).thenReturn(false);

        // Action
        useCase.execute(addressId);

        // Assert
        verify(restaurantGatewayGateway).existAddress(addressId);
        verify(addressGateway).delete(addressId);
    }

    @Test
    @DisplayName("Should throw EntityInUseException when address is in use")
    void shouldThrowEntityInUseExceptionWhenAddressIsInUse() {
        // Arrange
        UUID addressId = UUID.randomUUID();
        when(restaurantGatewayGateway.existAddress(addressId)).thenReturn(true);

        // Action
        EntityInUseException exception = assertThrows( EntityInUseException.class, () -> useCase.execute(addressId) );

        // Assert
        assertEquals( "The cannot be deleted, the register this in use", exception.getMessage());
        verify(restaurantGatewayGateway).existAddress(addressId);
        verify(addressGateway, never()).delete(addressId);
    }

    @Test
    @DisplayName("Should check if address is in use before deleting")
    void shouldCheckIfAddressIsInUseBeforeDeleting() {
        // Arrange
        UUID addressId = UUID.randomUUID();
        when(restaurantGatewayGateway.existAddress(addressId)).thenReturn(false);

        // Action
        useCase.execute(addressId);

        // Assert
        var inOrder = inOrder( restaurantGatewayGateway, addressGateway );
        inOrder.verify( restaurantGatewayGateway ).existAddress(addressId);
        inOrder.verify( addressGateway ).delete(addressId);
    }

    @Test
    @DisplayName("Should call existAddress only once")
    void shouldCallExistAddressOnlyOnce() {
        // Arrange
        UUID addressId = UUID.randomUUID();
        when(restaurantGatewayGateway.existAddress(addressId)).thenReturn(false);

        // Action
        useCase.execute(addressId);

        // Assert
        verify( restaurantGatewayGateway, times(1) ).existAddress(addressId);
        verify( addressGateway, times(1) ).delete(addressId);
    }

    @Test
    @DisplayName("Should not delete address when it is in use")
    void shouldNotDeleteAddressWhenItIsInUse() {
        // Arrange
        UUID addressId = UUID.randomUUID();
        when(restaurantGatewayGateway.existAddress(addressId)).thenReturn(true);

        // Action
        assertThrows( EntityInUseException.class, () -> useCase.execute(addressId) );

        // Assert
        verify( addressGateway, never() ).delete(any(UUID.class));
    }
}