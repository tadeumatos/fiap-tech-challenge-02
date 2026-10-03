package br.com.fiap.tech.challenge._2.application.usecase.address.impl;

import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge._2.domain.Address;
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
class GetAllAddressUseCaseImplTest {

    @Mock
    private AddressGateway addressGateway;
    private GetAllAddressUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetAllAddressUseCaseImpl( addressGateway );
    }

    @Test
    @DisplayName("Should return all addresses successfully")
    void shouldReturnAllAddressesSuccessfully() {
        // Arrange
        Address address1 = createAddress( "Main Street", "Downtown", "Fortaleza", "CE", "60000-000" );
        Address address2 = createAddress( "Second Street", "Aldeota", "Fortaleza", "CE", "60100-000" );
        List<Address> addresses = List.of( address1, address2 );
        when(addressGateway.getAll()).thenReturn(addresses);

        // Action
        List<Address> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals( address1, result.get(0) );
        assertEquals( address2, result.get(1) );
        verify(addressGateway).getAll();
    }

    @Test
    @DisplayName("Should return an empty list when there are no addresses")
    void shouldReturnEmptyListWhenThereAreNoAddresses() {
        // Arrange
        when(addressGateway.getAll()) .thenReturn(List.of());

        // Action
        List<Address> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(addressGateway).getAll();

    }

    @Test
    @DisplayName("Should call getAll only once")
    void shouldCallGetAllOnlyOnce() {
        // Arrange
        when(addressGateway.getAll()).thenReturn(List.of());

        // Action
        useCase.execute();

        // Assert
        verify( addressGateway, times(1) ).getAll();

    }

    @Test
    @DisplayName("Should return the exact list returned by the gateway")
    void shouldReturnTheExactListReturnedByGateway() {
        // Arrange
        Address address = createAddress( "Main Street", "Downtown", "Fortaleza", "CE", "60000-000" );
        List<Address> addresses = List.of(address);
        when(addressGateway.getAll()).thenReturn(addresses);

        // Action
        List<Address> result = useCase.execute();

        // Assert
        assertSame( addresses, result );
        verify(addressGateway).getAll();
    }

    private Address createAddress(String name, String neighborhood,String city, String state,String postalCode ) {

        return Address.create( UUID.randomUUID(), name, neighborhood, city, state, postalCode, "Brazil" );
    }
}