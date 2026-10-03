package br.com.fiap.tech.challenge._2.domain;

import br.com.fiap.tech.challenge._2.exceptions.ValidationFieldsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;


class AddressTest {

    @Test
    @DisplayName("Should create Address successfully with valid data")
    void shouldCreateAddressSuccessfullyWithValidData() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        Address address = Address.create( id, "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" );

        // Assert
        assertNotNull(address);
        assertEquals(id, address.getId());
        assertEquals("Main Street", address.getName());
        assertEquals("Downtown", address.getNeighborhood());
        assertEquals("Fortaleza", address.getCity());
        assertEquals("CE", address.getState());
        assertEquals("60000-000", address.getPostalCode());
        assertEquals("Brazil", address.getCountry());
    }

    @Test
    @DisplayName("Should throw exception when id is null")
    void shouldThrowExceptionWhenIdIsNull() {
        // Arrange
        UUID id = null;

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" ) );

        // Assert
        assertEquals( "The id is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, null, "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" ) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when name is blank")
    void shouldThrowExceptionWhenNameIsBlank() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, " ", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" ) );

        // Assert
        assertEquals( "The name is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when neighborhood is null")
    void shouldThrowExceptionWhenNeighborhoodIsNull() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, "Main Street", null, "Fortaleza", "CE", "60000-000", "Brazil" ) );

        // Assert
        assertEquals( "The neighborhood is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when neighborhood is blank")
    void shouldThrowExceptionWhenNeighborhoodIsBlank() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, "Main Street", " ", "Fortaleza", "CE", "60000-000", "Brazil" ) );

        // Assert
        assertEquals( "The neighborhood is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when city is null")
    void shouldThrowExceptionWhenCityIsNull() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, "Main Street", "Downtown", null, "CE", "60000-000", "Brazil" ) );

        // Assert
        assertEquals( "The city is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when city is blank")
    void shouldThrowExceptionWhenCityIsBlank() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, "Main Street", "Downtown", " ", "CE", "60000-000", "Brazil" ) );

        // Assert
        assertEquals( "The city is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when state is null")
    void shouldThrowExceptionWhenStateIsNull() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, "Main Street", "Downtown", "Fortaleza", null, "60000-000", "Brazil" ) );

        // Assert
        assertEquals( "The state is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when state is blank")
    void shouldThrowExceptionWhenStateIsBlank() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, "Main Street", "Downtown", "Fortaleza", " ", "60000-000", "Brazil" ) );

        // Assert
        assertEquals( "The state is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when postal code is null")
    void shouldThrowExceptionWhenPostalCodeIsNull() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, "Main Street", "Downtown", "Fortaleza", "CE", null, "Brazil" ) );

        // Assert
        assertEquals( "The postal code is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when postal code is blank")
    void shouldThrowExceptionWhenPostalCodeIsBlank() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, "Main Street", "Downtown", "Fortaleza", "CE", " ", "Brazil" ) );

        // Assert
        assertEquals( "The postal code is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when country is null")
    void shouldThrowExceptionWhenCountryIsNull() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", null ) );

        // Assert
        assertEquals( "The country is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should throw exception when country is blank")
    void shouldThrowExceptionWhenCountryIsBlank() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Action
        ValidationFieldsException exception = assertThrows( ValidationFieldsException.class, () -> Address.create( id, "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", " " ) );

        // Assert
        assertEquals( "The country is required field", exception.getMessage() );
    }

    @Test
    @DisplayName("Should update Address fields successfully")
    void shouldUpdateAddressFieldsSuccessfully() {
        // Arrange
        Address address = Address.create( UUID.randomUUID(), "Main Street", "Downtown", "Fortaleza", "CE", "60000-000", "Brazil" ); UUID newId = UUID.randomUUID();

        // Action
        address.setId(newId);
        address.setName("New Street");
        address.setNeighborhood("Aldeota");
        address.setCity("Caucaia");
        address.setState("CE");
        address.setPostalCode("61600-000");
        address.setCountry("Brazil");

        // Assert
        assertEquals(newId, address.getId());
        assertEquals("New Street", address.getName());
        assertEquals("Aldeota", address.getNeighborhood());
        assertEquals("Caucaia", address.getCity());
        assertEquals("CE", address.getState());
        assertEquals("61600-000", address.getPostalCode());
        assertEquals("Brazil", address.getCountry());
    }
}