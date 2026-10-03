package br.com.fiap.tech.challenge._2.integration;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.entities.AddressEntity;
import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.AddressRepository;
import br.com.fiap.tech.challenge._2.presentation.controller.request.AddressRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AddressControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Should create address successfully")
    void shouldCreateAddressSuccessfully() throws Exception {

        // Arrange
        AddressRequest request = new AddressRequest(
                "Rua das Flores",
                "Centro",
                "Fortaleza",
                "Ceara",
                "60000000",
                "Brasil"
        );

        // Act / Assert
        mockMvc.perform(
                        post("/addresses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name")
                        .value("Rua das Flores"))
                .andExpect(jsonPath("$.neighborhood")
                        .value("Centro"))
                .andExpect(jsonPath("$.city")
                        .value("Fortaleza"))
                .andExpect(jsonPath("$.state")
                        .value("Ceara"))
                .andExpect(jsonPath("$.postalCode")
                        .value("60000000"))
                .andExpect(jsonPath("$.country")
                        .value("Brasil"));

        // Assert
        assertThat(addressRepository.findAll())
                .hasSize(1);

        AddressEntity address =
                addressRepository.findAll().get(0);

        assertThat(address.getName())
                .isEqualTo("Rua das Flores");

        assertThat(address.getNeighborhood())
                .isEqualTo("Centro");

        assertThat(address.getCity())
                .isEqualTo("Fortaleza");

        assertThat(address.getState())
                .isEqualTo("Ceara");

        assertThat(address.getPostalCode())
                .isEqualTo("60000000");

        assertThat(address.getCountry())
                .isEqualTo("Brasil");
    }

    @Test
    @DisplayName("Should return 409 when creating address with existing postal code")
    void shouldReturn409WhenCreatingAddressWithExistingPostalCode()
            throws Exception {

        // Arrange
        addressRepository.save(
                new AddressEntity(
                        "Rua das Flores",
                        "Centro",
                        "Fortaleza",
                        "Ceara",
                        "60000000",
                        "Brasil"
                )
        );

        AddressRequest request = new AddressRequest(
                "Avenida Brasil",
                "Aldeota",
                "Fortaleza",
                "Ceara",
                "60000000",
                "Brasil"
        );

        // Act / Assert
        mockMvc.perform(
                        post("/addresses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title")
                        .value("Error rule business"))
                .andExpect(jsonPath("$.detail")
                        .value(
                                "Already postal code with register, please choice other postal code"
                        ));

        // Assert
        assertThat(addressRepository.findAll())
                .hasSize(1);
    }

    @Test
    @DisplayName("Should find address by ID successfully")
    void shouldFindAddressByIdSuccessfully()
            throws Exception {

        // Arrange
        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua das Flores",
                                "Centro",
                                "Fortaleza",
                                "Ceara",
                                "60000000",
                                "Brasil"
                        )
                );

        UUID id = address.getId();

        // Act / Assert
        mockMvc.perform(
                        get("/addresses/{id}", id)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(id.toString()))
                .andExpect(jsonPath("$.name")
                        .value("Rua das Flores"))
                .andExpect(jsonPath("$.neighborhood")
                        .value("Centro"))
                .andExpect(jsonPath("$.city")
                        .value("Fortaleza"))
                .andExpect(jsonPath("$.state")
                        .value("Ceara"))
                .andExpect(jsonPath("$.postalCode")
                        .value("60000000"))
                .andExpect(jsonPath("$.country")
                        .value("Brasil"));
    }

    @Test
    @DisplayName("Should return 404 when address does not exist")
    void shouldReturn404WhenAddressDoesNotExist()
            throws Exception {

        // Arrange
        UUID id = UUID.randomUUID();

        // Act / Assert
        mockMvc.perform(
                        get("/addresses/{id}", id)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title")
                        .value("Resource not found"))
                .andExpect(jsonPath("$.detail")
                        .value("The address not found"));
    }

    @Test
    @DisplayName("Should return all addresses successfully")
    void shouldReturnAllAddressesSuccessfully()
            throws Exception {

        // Arrange
        addressRepository.save(
                new AddressEntity(
                        "Rua das Flores",
                        "Centro",
                        "Fortaleza",
                        "Ceara",
                        "60000000",
                        "Brasil"
                )
        );

        addressRepository.save(
                new AddressEntity(
                        "Avenida Brasil",
                        "Aldeota",
                        "Fortaleza",
                        "Ceara",
                        "60100000",
                        "Brasil"
                )
        );

        // Act / Assert
        mockMvc.perform(
                        get("/addresses")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(2))
                .andExpect(jsonPath("$[0].name")
                        .value("Rua das Flores"))
                .andExpect(jsonPath("$[1].name")
                        .value("Avenida Brasil"));
    }

    @Test
    @DisplayName("Should return an empty list when no addresses exist")
    void shouldReturnEmptyListWhenNoAddressesExist()
            throws Exception {

        // Arrange
        // Database is cleaned before each test.

        // Act / Assert
        mockMvc.perform(
                        get("/addresses")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(0));
    }

    @Test
    @DisplayName("Should update address successfully")
    void shouldUpdateAddressSuccessfully()
            throws Exception {

        // Arrange
        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua das Flores",
                                "Centro",
                                "Fortaleza",
                                "Ceara",
                                "60000000",
                                "Brasil"
                        )
                );

        UUID id = address.getId();

        AddressRequest request = new AddressRequest(
                "Avenida Beira Mar",
                "Meireles",
                "Fortaleza",
                "Ceara",
                "60165000",
                "Brasil"
        );

        // Act / Assert
        mockMvc.perform(
                        put("/addresses/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(id.toString()))
                .andExpect(jsonPath("$.name")
                        .value("Avenida Beira Mar"))
                .andExpect(jsonPath("$.neighborhood")
                        .value("Meireles"))
                .andExpect(jsonPath("$.city")
                        .value("Fortaleza"))
                .andExpect(jsonPath("$.state")
                        .value("Ceara"))
                .andExpect(jsonPath("$.postalCode")
                        .value("60165000"))
                .andExpect(jsonPath("$.country")
                        .value("Brasil"));

        // Assert
        AddressEntity updated =
                addressRepository.findById(id)
                        .orElseThrow();

        assertThat(updated.getName())
                .isEqualTo("Avenida Beira Mar");

        assertThat(updated.getNeighborhood())
                .isEqualTo("Meireles");

        assertThat(updated.getPostalCode())
                .isEqualTo("60165000");
    }

    @Test
    @DisplayName("Should return 404 when updating a non-existing address")
    void shouldReturn404WhenUpdatingNonExistingAddress()
            throws Exception {

        // Arrange
        UUID id = UUID.randomUUID();

        AddressRequest request = new AddressRequest(
                "Avenida Beira Mar",
                "Meireles",
                "Fortaleza",
                "Ceara",
                "60165000",
                "Brasil"
        );

        // Act / Assert
        mockMvc.perform(
                        put("/addresses/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title")
                        .value("Resource not found"))
                .andExpect(jsonPath("$.detail")
                        .value("The address not found"));

        // Assert
        assertThat(addressRepository.findAll())
                .isEmpty();
    }

    @Test
    @DisplayName("Should allow updating address with its own postal code")
    void shouldAllowUpdatingAddressWithItsOwnPostalCode()
            throws Exception {

        // Arrange
        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua das Flores",
                                "Centro",
                                "Fortaleza",
                                "Ceara",
                                "60000000",
                                "Brasil"
                        )
                );

        UUID id = address.getId();

        AddressRequest request = new AddressRequest(
                "Avenida Beira Mar",
                "Meireles",
                "Fortaleza",
                "Ceara",
                "60000000",
                "Brasil"
        );

        // Act / Assert
        mockMvc.perform(
                        put("/addresses/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk());

        // Assert
        AddressEntity updated =
                addressRepository.findById(id)
                        .orElseThrow();

        assertThat(updated.getName())
                .isEqualTo("Avenida Beira Mar");

        assertThat(updated.getPostalCode())
                .isEqualTo("60000000");
    }

    @Test
    @DisplayName("Should return 409 when updating address with another address postal code")
    void shouldReturn409WhenUpdatingAddressWithAnotherAddressPostalCode()
            throws Exception {

        // Arrange
        AddressEntity address1 =
                addressRepository.save(
                        new AddressEntity(
                                "Rua das Flores",
                                "Centro",
                                "Fortaleza",
                                "Ceara",
                                "60000000",
                                "Brasil"
                        )
                );

        addressRepository.save(
                new AddressEntity(
                        "Avenida Brasil",
                        "Aldeota",
                        "Fortaleza",
                        "Ceara",
                        "60100000",
                        "Brasil"
                )
        );

        UUID id = address1.getId();

        AddressRequest request = new AddressRequest(
                "Avenida Beira Mar",
                "Meireles",
                "Fortaleza",
                "Ceara",
                "60100000",
                "Brasil"
        );

        // Act / Assert
        mockMvc.perform(
                        put("/addresses/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title")
                        .value("Error rule business"))
                .andExpect(jsonPath("$.detail")
                        .value(
                                "Already exist postal code with other register, please choice other postal code"
                        ));

        // Assert
        AddressEntity unchanged =
                addressRepository.findById(id)
                        .orElseThrow();

        assertThat(unchanged.getName())
                .isEqualTo("Rua das Flores");

        assertThat(unchanged.getPostalCode())
                .isEqualTo("60000000");
    }

    @Test
    @DisplayName("Should delete address successfully")
    void shouldDeleteAddressSuccessfully()
            throws Exception {

        // Arrange
        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua das Flores",
                                "Centro",
                                "Fortaleza",
                                "Ceara",
                                "60000000",
                                "Brasil"
                        )
                );

        UUID id = address.getId();

        // Act / Assert
        mockMvc.perform(
                        delete("/addresses/{id}", id)
                )
                .andExpect(status().isNoContent());

        // Assert
        assertThat(addressRepository.findById(id))
                .isEmpty();
    }

    @Test
    @DisplayName("Should return 404 when deleting a non-existing address")
    void shouldReturn404WhenDeletingNonExistingAddress() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        mockMvc.perform( delete("/addresses/{id}", id) ).andExpect(status().isNotFound()).andExpect( jsonPath("$.title")
                .value("Resource not found") )
                .andExpect( jsonPath("$.detail")
                        .value("Register not found") );
        // Assert
        assertThat(addressRepository.findById(id)) .isEmpty();
    }

    @Test
    @DisplayName("Should return 422 when creating address with invalid fields")
    void shouldReturn422WhenCreatingAddressWithInvalidFields()
            throws Exception {

        // Arrange
        AddressRequest request = new AddressRequest(
                "",
                "",
                "",
                "",
                "",
                ""
        );

        // Act / Assert
        mockMvc.perform(
                        post("/addresses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.title")
                        .value("Validation"))
                .andExpect(jsonPath("$.errors")
                        .exists());

        // Assert
        assertThat(addressRepository.findAll())
                .isEmpty();
    }

    @Test
    @DisplayName("Should return 422 when creating address with fields below minimum size")
    void shouldReturn422WhenCreatingAddressWithFieldsBelowMinimumSize()
            throws Exception {

        // Arrange
        AddressRequest request = new AddressRequest(
                "Rua",
                "Bairro",
                "City",
                "State",
                "123",
                "BR"
        );

        // Act / Assert
        mockMvc.perform(
                        post("/addresses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.title")
                        .value("Validation"))
                .andExpect(jsonPath("$.errors")
                        .exists());

        // Assert
        assertThat(addressRepository.findAll())
                .isEmpty();
    }

    @Test
    @DisplayName("Should return 422 when updating address with invalid fields")
    void shouldReturn422WhenUpdatingAddressWithInvalidFields()
            throws Exception {

        // Arrange
        AddressEntity address =
                addressRepository.save(
                        new AddressEntity(
                                "Rua das Flores",
                                "Centro",
                                "Fortaleza",
                                "Ceara",
                                "60000000",
                                "Brasil"
                        )
                );

        UUID id = address.getId();

        AddressRequest request = new AddressRequest(
                "",
                "",
                "",
                "",
                "",
                ""
        );

        // Act / Assert
        mockMvc.perform(
                        put("/addresses/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.title")
                        .value("Validation"))
                .andExpect(jsonPath("$.errors")
                        .exists());

        // Assert
        AddressEntity unchanged =
                addressRepository.findById(id)
                        .orElseThrow();

        assertThat(unchanged.getName())
                .isEqualTo("Rua das Flores");

        assertThat(unchanged.getPostalCode())
                .isEqualTo("60000000");
    }

    @Test
    @DisplayName("Should return 422 when creating address with null fields")
    void shouldReturn422WhenCreatingAddressWithNullFields()
            throws Exception {

        // Arrange
        String request = """
                {
                    "name": null,
                    "neighborhood": null,
                    "city": null,
                    "state": null,
                    "postalCode": null,
                    "country": null
                }
                """;

        // Act / Assert
        mockMvc.perform(
                        post("/addresses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.title")
                        .value("Validation"))
                .andExpect(jsonPath("$.errors")
                        .exists());

        // Assert
        assertThat(addressRepository.findAll())
                .isEmpty();
    }
}


