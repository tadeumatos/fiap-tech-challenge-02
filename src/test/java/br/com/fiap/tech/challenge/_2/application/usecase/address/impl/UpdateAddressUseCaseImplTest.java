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

import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateAddressUseCaseImplTest {

    @Mock
    private AddressGateway addressGateway;
    private UpdateAddressUseCaseImpl useCase;

    @BeforeEach void setUp() {
        useCase = new UpdateAddressUseCaseImpl( addressGateway );
    }


    @Test
    @DisplayName("Should update address successfully with a new postal code")
    void shouldUpdateAddressSuccessfullyWithNewPostalCode() {

        // Arrange
        UUID addressId = UUID.randomUUID();

        Address existingAddress = Address.create(
                addressId,
                "Old Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        AddressRequest request = new AddressRequest(
                "Main Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60100-000",
                "Brazil"
        );

        Address updatedAddress = Address.create(
                addressId,
                request.name(),
                request.neighborhood(),
                request.city(),
                request.state(),
                request.postalCode(),
                request.country()
        );

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.of(existingAddress));

        when(addressGateway.findByPostalCode(request.postalCode()))
                .thenReturn(null);

        when(addressGateway.update(
                eq(addressId),
                any(Address.class)
        )).thenReturn(updatedAddress);

        // Act
        Address result = useCase.execute(
                addressId,
                request
        );

        // Assert
        assertNotNull(result);

        assertEquals(
                addressId,
                result.getId()
        );

        assertEquals(
                request.name(),
                result.getName()
        );

        assertEquals(
                request.neighborhood(),
                result.getNeighborhood()
        );

        assertEquals(
                request.city(),
                result.getCity()
        );

        assertEquals(
                request.state(),
                result.getState()
        );

        assertEquals(
                request.postalCode(),
                result.getPostalCode()
        );

        assertEquals(
                request.country(),
                result.getCountry()
        );

        verify(
                addressGateway,
                times(1)
        ).findById(addressId);

        verify(
                addressGateway,
                times(1)
        ).findByPostalCode(
                request.postalCode()
        );

        verify(
                addressGateway,
                times(1)
        ).update(
                eq(addressId),
                any(Address.class)
        );
    }

    @Test
    @DisplayName("Should update address successfully keeping its owner postal code")
    void shouldUpdateAddressSuccessfullyKeepingItsOwnerPostalCode() {

        // Arrange
        UUID addressId = UUID.randomUUID();

        Address existingAddress = Address.create(
                addressId,
                "Old Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        AddressRequest request = new AddressRequest(
                "Updated Street",
                "Aldeota",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        Address updatedAddress = Address.create(
                addressId,
                request.name(),
                request.neighborhood(),
                request.city(),
                request.state(),
                request.postalCode(),
                request.country()
        );

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.of(existingAddress));

        when(addressGateway.findByPostalCode(request.postalCode()))
                .thenReturn(existingAddress);

        when(addressGateway.update(
                eq(addressId),
                any(Address.class)
        )).thenReturn(updatedAddress);

        // Act
        Address result = useCase.execute(
                addressId,
                request
        );

        // Assert
        assertNotNull(result);

        assertEquals(
                addressId,
                result.getId()
        );

        assertEquals(
                "Updated Street",
                result.getName()
        );

        assertEquals(
                "Aldeota",
                result.getNeighborhood()
        );

        assertEquals(
                "60000-000",
                result.getPostalCode()
        );

        verify(
                addressGateway,
                times(1)
        ).findById(addressId);

        verify(
                addressGateway,
                times(1)
        ).findByPostalCode(
                request.postalCode()
        );

        verify(
                addressGateway,
                times(1)
        ).update(
                eq(addressId),
                any(Address.class)
        );
    }




    @Test
    @DisplayName("Should throw BusinessException when postal code belongs to another address")
    void shouldThrowBusinessExceptionWhenPostalCodeBelongsToAnotherAddress() {

        // Arrange
        UUID addressId = UUID.randomUUID();
        UUID anotherAddressId = UUID.randomUUID();

        AddressRequest request = new AddressRequest(
                "Updated Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        Address currentAddress = Address.create(
                addressId,
                "Current Street",
                "Centro",
                "Fortaleza",
                "CE",
                "60100-000",
                "Brazil"
        );

        Address anotherAddress = Address.create(
                anotherAddressId,
                "Another Street",
                "Aldeota",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.of(currentAddress));

        when(addressGateway.findByPostalCode(request.postalCode()))
                .thenReturn(anotherAddress);

        // Act
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> useCase.execute(addressId, request)
        );

        // Assert
        assertEquals(
                "Already exist postal code with other register, please choice other postal code",
                exception.getMessage()
        );

        verify(
                addressGateway,
                times(1)
        ).findById(addressId);

        verify(
                addressGateway,
                times(1)
        ).findByPostalCode(
                request.postalCode()
        );

        verify(
                addressGateway,
                never()
        ).update(
                any(UUID.class),
                any(Address.class)
        );
    }

    @Test
    @DisplayName("Should update address with the request data")
    void shouldUpdateAddressWithRequestData() {

        // Arrange
        UUID addressId = UUID.randomUUID();

        Address existingAddress = Address.create(
                addressId,
                "Old Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        AddressRequest request = new AddressRequest(
                "Updated Street",
                "Aldeota",
                "Fortaleza",
                "CE",
                "60100-000",
                "Brazil"
        );

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.of(existingAddress));

        when(addressGateway.findByPostalCode(request.postalCode()))
                .thenReturn(null);

        when(addressGateway.update(
                eq(addressId),
                any(Address.class)
        )).thenAnswer(
                invocation -> invocation.getArgument(1)
        );

        ArgumentCaptor<Address> addressCaptor =
                ArgumentCaptor.forClass(Address.class);

        // Act
        Address result = useCase.execute(
                addressId,
                request
        );

        // Assert
        verify(
                addressGateway
        ).findById(addressId);

        verify(
                addressGateway
        ).findByPostalCode(
                request.postalCode()
        );

        verify(
                addressGateway
        ).update(
                eq(addressId),
                addressCaptor.capture()
        );

        Address updatedAddress = addressCaptor.getValue();

        assertNotNull(updatedAddress);

        assertEquals(
                addressId,
                updatedAddress.getId()
        );

        assertEquals(
                request.name(),
                updatedAddress.getName()
        );

        assertEquals(
                request.neighborhood(),
                updatedAddress.getNeighborhood()
        );

        assertEquals(
                request.city(),
                updatedAddress.getCity()
        );

        assertEquals(
                request.state(),
                updatedAddress.getState()
        );

        assertEquals(
                request.postalCode(),
                updatedAddress.getPostalCode()
        );

        assertEquals(
                request.country(),
                updatedAddress.getCountry()
        );

        assertSame(
                updatedAddress,
                result
        );
    }



    @Test
    @DisplayName("Should check postal code before updating address")
    void shouldCheckPostalCodeBeforeUpdatingAddress() {

        // Arrange
        UUID addressId = UUID.randomUUID();

        Address existingAddress = Address.create(
                addressId,
                "Old Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        AddressRequest request = new AddressRequest(
                "Updated Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60300-000",
                "Brazil"
        );

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.of(existingAddress));

        when(addressGateway.findByPostalCode(request.postalCode()))
                .thenReturn(null);

        when(addressGateway.update(
                eq(addressId),
                any(Address.class)
        )).thenAnswer(
                invocation -> invocation.getArgument(1)
        );

        // Act
        useCase.execute(
                addressId,
                request
        );

        // Assert
        var inOrder = inOrder(addressGateway);

        inOrder.verify(addressGateway)
                .findById(addressId);

        inOrder.verify(addressGateway)
                .findByPostalCode(
                        request.postalCode()
                );

        inOrder.verify(addressGateway)
                .update(
                        eq(addressId),
                        any(Address.class)
                );
    }




    @Test
    @DisplayName("Should call findByPostalCode only once")
    void shouldCallFindByPostalCodeOnlyOnce() {

        // Arrange
        UUID addressId = UUID.randomUUID();

        Address existingAddress = Address.create(
                addressId,
                "Old Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60000-000",
                "Brazil"
        );

        AddressRequest request = new AddressRequest(
                "Updated Street",
                "Downtown",
                "Fortaleza",
                "CE",
                "60400-000",
                "Brazil"
        );

        when(addressGateway.findById(addressId))
                .thenReturn(Optional.of(existingAddress));

        when(addressGateway.findByPostalCode(request.postalCode()))
                .thenReturn(null);

        when(addressGateway.update(
                eq(addressId),
                any(Address.class)
        )).thenAnswer(
                invocation -> invocation.getArgument(1)
        );

        // Act
        useCase.execute(addressId, request);

        // Assert
        verify(
                addressGateway,
                times(1)
        ).findById(addressId);

        verify(
                addressGateway,
                times(1)
        ).findByPostalCode(request.postalCode());

        verify(
                addressGateway,
                times(1)
        ).update(
                eq(addressId),
                any(Address.class)
        );
    }


}