package br.com.fiap.tech.challenge._2.application.usecase.address.impl;

import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge._2.domain.Address;
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
class FindAddressUseCaseImplTest {

    @Mock
    private AddressGateway addressGateway;
    private FindAddressUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new FindAddressUseCaseImpl( addressGateway );
    }

    @Test
    @DisplayName("Should find address successfully")
    void shouldFindAddressSuccessfully() {
        // Arrange
        UUID addressId = UUID.randomUUID();
        Address address = Address.create( addressId, "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" );
        when(addressGateway.findById(addressId)).thenReturn(Optional.of(address));

        // Action
        Address result = useCase.execute(addressId);

        // Assert
        assertNotNull(result);
        assertEquals( addressId, result.getId() );
        assertEquals( "Main Street", result.getName() );
        assertEquals( "Downtown", result.getNeighborhood() );
        assertEquals( "Fortaleza", result.getCity() );
        assertEquals( "CE", result.getState() );
        assertEquals( "60000-000", result.getPostalCode() );
        assertEquals( "Brazil", result.getCountry() );
        verify(addressGateway).findById(addressId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when address does not exist")
    void shouldThrowResourceNotFoundExceptionWhenAddressDoesNotExist() {
        // Arrange
        UUID addressId = UUID.randomUUID();
        when(addressGateway.findById(addressId)).thenReturn(Optional.empty());

        // Action
        ResourceNotFoundException exception = assertThrows( ResourceNotFoundException.class, () -> useCase.execute(addressId) );

        // Assert
        assertEquals( "The address not found", exception.getMessage() );
        verify(addressGateway).findById(addressId);
    }

    @Test
    @DisplayName("Should call findById only once")
    void shouldCallFindByIdOnlyOnce() {
        // Arrange
        UUID addressId = UUID.randomUUID();
        when(addressGateway.findById(addressId)).thenReturn(Optional.empty());

        // Action
        assertThrows( ResourceNotFoundException.class, () -> useCase.execute(addressId) );

        // Assert
        verify( addressGateway, times(1) ).findById(addressId);
    }

    @Test
    @DisplayName("Should return the exact address returned by the gateway")
    void shouldReturnTheExactAddressReturnedByGateway() {
        // Arrange
        UUID addressId = UUID.randomUUID();
        Address address = Address.create( addressId, "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" );
        when(addressGateway.findById(addressId)).thenReturn(Optional.of(address));

        // Act
        Address result = useCase.execute(addressId);

        // Assert
        assertSame( address, result );
        verify(addressGateway).findById(addressId);
    }
}