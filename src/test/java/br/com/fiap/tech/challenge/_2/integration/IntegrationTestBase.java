package br.com.fiap.tech.challenge._2.integration;

import br.com.fiap.tech.challenge._2.infrastructure.persistence.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class IntegrationTestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected MenuRepository menuRepository;

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected FoodTypeRepository foodTypeRepository;

    @Autowired
    protected UserTypeRepository userTypeRepository;

    @Autowired
    protected AddressRepository addressRepository;

    @BeforeEach
    void cleanDatabase() {
        menuRepository.deleteAll();
        restaurantRepository.deleteAll();
        userRepository.deleteAll();
        foodTypeRepository.deleteAll();
        userTypeRepository.deleteAll();
        addressRepository.deleteAll();
    }
}

