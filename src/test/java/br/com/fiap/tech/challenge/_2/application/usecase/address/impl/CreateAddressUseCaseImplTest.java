package br.com.fiap.tech.challenge._2.application.usecase.address.impl;

import br.com.fiap.tech.challenge._2.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge._2.domain.Address;
import br.com.fiap.tech.challenge._2.exceptions.BusinessException;
import br.com.fiap.tech.challenge._2.presentation.controller.request.AddressRequest;
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
class CreateAddressUseCaseImplTest {

    @Mock
    private AddressGateway addressGateway;
    private CreateAddressUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateAddressUseCaseImpl( addressGateway );
    }

    @Test
    @DisplayName("Should create an address successfully")
    void shouldCreateAddressSuccessfully() {
        // Arrange
        AddressRequest request = new AddressRequest( "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" );
        Address savedAddress = Address.create( UUID.randomUUID(), request.name(), request.neighborhood(), request.city(), request.state(), request.postalCode(), request.country() );
        when(addressGateway.existPostalCode(request.postalCode())) .thenReturn(false); when(addressGateway.save(any(Address.class))) .thenReturn(savedAddress);

        // Action
        Address result = useCase.execute(request);

        // Assert
        assertNotNull(result);
        assertEquals( savedAddress.getId(), result.getId() );
        assertEquals( request.name(), result.getName() );
        assertEquals( request.neighborhood(), result.getNeighborhood() );
        assertEquals( request.city(), result.getCity() );
        assertEquals( request.state(), result.getState() );
        assertEquals( request.postalCode(), result.getPostalCode() );
        assertEquals( request.country(), result.getCountry() );
        verify(addressGateway).existPostalCode(request.postalCode());
        verify(addressGateway) .save(any(Address.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when postal code already exists")
    void shouldThrowBusinessExceptionWhenPostalCodeAlreadyExists() {
        // Arrange
        AddressRequest request = new AddressRequest( "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" );
        when(addressGateway.existPostalCode(request.postalCode())).thenReturn(true);

        // Action
        BusinessException exception = assertThrows( BusinessException.class, () -> useCase.execute(request) );

        // Assert
        assertEquals( "Already postal code with register, please choice other postal code", exception.getMessage() );
        verify(addressGateway).existPostalCode(request.postalCode());
        verify(addressGateway, never()).save(any(Address.class));
    }

    @Test
    @DisplayName("Should call existPostalCode only once")
    void shouldCallExistPostalCodeOnlyOnce() {
        // Arrange
        AddressRequest request = new AddressRequest( "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" );
        when(addressGateway.existPostalCode(request.postalCode())).thenReturn(false);
        when(addressGateway.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Action
        useCase.execute(request);

        // Assert
        verify( addressGateway, times(1) ).existPostalCode(request.postalCode());
    }

    @Test
    @DisplayName("Should save the address with the request data")
    void shouldSaveAddressWithRequestData() {
        // Arrange
        AddressRequest request = new AddressRequest( "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" );
        when(addressGateway.existPostalCode(request.postalCode())).thenReturn(false);
        when(addressGateway.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);

        // Action
        Address result = useCase.execute(request);

        // Assert
        verify(addressGateway).save(addressCaptor.capture());
        Address savedAddress = addressCaptor.getValue();
        assertNotNull(savedAddress);
        assertNotNull(savedAddress.getId());
        assertEquals( request.name(), savedAddress.getName() );
        assertEquals( request.neighborhood(), savedAddress.getNeighborhood() );
        assertEquals( request.city(), savedAddress.getCity() );
        assertEquals( request.state(), savedAddress.getState() );
        assertEquals( request.postalCode(), savedAddress.getPostalCode() );
        assertEquals( request.country(), savedAddress.getCountry() );
        assertEquals( savedAddress.getId(), result.getId() );
    }

    @Test
    @DisplayName("Should generate a new id when creating an address")
    void shouldGenerateNewIdWhenCreatingAddress() {
        // Arrange
        AddressRequest request = new AddressRequest( "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" );
        when(addressGateway.existPostalCode(request.postalCode())).thenReturn(false);
        when(addressGateway.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);

        // Action
        useCase.execute(request);

        // Assert
        verify(addressGateway).save(addressCaptor.capture());
        Address savedAddress = addressCaptor.getValue();
        assertNotNull(savedAddress.getId());
    }
}