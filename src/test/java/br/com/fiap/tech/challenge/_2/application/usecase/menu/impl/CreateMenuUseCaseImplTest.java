package br.com.fiap.tech.challenge._2.application.usecase.menu.impl;

import br.com.fiap.tech.challenge._2.application.gateway.MenuGateway;
import br.com.fiap.tech.challenge._2.application.gateway.RestaurantGateway;
import br.com.fiap.tech.challenge._2.domain.Menu;
import br.com.fiap.tech.challenge._2.domain.Restaurant;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.presentation.controller.request.MenuRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateMenuUseCaseImplTest {

    @Mock
    private MenuGateway menuGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    private CreateMenuUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateMenuUseCaseImpl(
                menuGateway,
                restaurantGateway
        );
    }

    @Test
    @DisplayName("Should create menu successfully")
    void shouldCreateMenuSuccessfully() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        Restaurant restaurant = mock(Restaurant.class);

        MenuRequest request = new MenuRequest(
                "Feijoada",
                "Traditional Brazilian feijoada",
                new BigDecimal("39.90"),
                true,
                "feijoada.jpg",
                restaurantId.toString(),
                true
        );

        Menu savedMenu = mock(Menu.class);

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.save(any(Menu.class)))
                .thenReturn(savedMenu);

        // Act
        Menu result = useCase.execute(request);

        // Assert
        assertNotNull(result);

        assertSame(
                savedMenu,
                result
        );

        verify(restaurantGateway)
                .findById(restaurantId);

        verify(menuGateway)
                .save(any(Menu.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when restaurant does not exist")
    void shouldThrowResourceNotFoundExceptionWhenRestaurantDoesNotExist() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        MenuRequest request = createRequest(
                restaurantId
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.empty());

        // Act
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(request)
        );

        // Assert
        assertEquals(
                "The restaurant not found",
                exception.getMessage()
        );

        verify(restaurantGateway)
                .findById(restaurantId);

        verify(
                menuGateway,
                never()
        ).save(any(Menu.class));
    }

    @Test
    @DisplayName("Should create menu with request data")
    void shouldCreateMenuWithRequestData() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        Restaurant restaurant = mock(Restaurant.class);

        MenuRequest request = new MenuRequest(
                "Feijoada",
                "Traditional Brazilian feijoada",
                new BigDecimal("39.90"),
                true,
                "feijoada.jpg",
                restaurantId.toString(),
                true
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.save(any(Menu.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Menu> menuCaptor =
                ArgumentCaptor.forClass(Menu.class);

        // Act
        Menu result = useCase.execute(request);

        // Assert
        verify(menuGateway)
                .save(menuCaptor.capture());

        Menu capturedMenu =
                menuCaptor.getValue();

        assertNotNull(
                capturedMenu.getId()
        );

        assertEquals(
                request.name(),
                capturedMenu.getName()
        );

        assertEquals(
                request.description(),
                capturedMenu.getDescription()
        );

        assertEquals(
                request.price(),
                capturedMenu.getPrice()
        );

        assertEquals(
                request.onlyLocal(),
                capturedMenu.isOnlyLocal()
        );

        assertEquals(
                request.foodPhoto(),
                capturedMenu.getFoodPhoto()
        );

        assertEquals(
                request.active(),
                capturedMenu.isActive()
        );

        assertSame(
                restaurant,
                capturedMenu.getRestaurant()
        );

        assertSame(
                capturedMenu,
                result
        );
    }

    @Test
    @DisplayName("Should generate a new ID for the menu")
    void shouldGenerateNewIdForMenu() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        Restaurant restaurant = mock(Restaurant.class);

        MenuRequest request = createRequest(
                restaurantId
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.save(any(Menu.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Menu> menuCaptor =
                ArgumentCaptor.forClass(Menu.class);

        // Act
        useCase.execute(request);

        // Assert
        verify(menuGateway)
                .save(menuCaptor.capture());

        Menu capturedMenu =
                menuCaptor.getValue();

        assertNotNull(
                capturedMenu.getId()
        );
    }

    @Test
    @DisplayName("Should use the restaurant returned by the gateway")
    void shouldUseRestaurantReturnedByGateway() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        Restaurant restaurant = mock(Restaurant.class);

        MenuRequest request = createRequest(
                restaurantId
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.save(any(Menu.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Menu> menuCaptor =
                ArgumentCaptor.forClass(Menu.class);

        // Act
        useCase.execute(request);

        // Assert
        verify(menuGateway)
                .save(menuCaptor.capture());

        Menu capturedMenu =
                menuCaptor.getValue();

        assertSame(
                restaurant,
                capturedMenu.getRestaurant()
        );
    }

    @Test
    @DisplayName("Should search restaurant before saving menu")
    void shouldSearchRestaurantBeforeSavingMenu() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        Restaurant restaurant = mock(Restaurant.class);

        MenuRequest request = createRequest(
                restaurantId
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.save(any(Menu.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        InOrder inOrder = inOrder(
                restaurantGateway,
                menuGateway
        );

        // Act
        useCase.execute(request);

        // Assert
        inOrder.verify(restaurantGateway)
                .findById(restaurantId);

        inOrder.verify(menuGateway)
                .save(any(Menu.class));
    }

    @Test
    @DisplayName("Should call findById only once")
    void shouldCallFindByIdOnlyOnce() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        Restaurant restaurant = mock(Restaurant.class);

        MenuRequest request = createRequest(
                restaurantId
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.save(any(Menu.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        useCase.execute(request);

        // Assert
        verify(
                restaurantGateway,
                times(1)
        ).findById(restaurantId);
    }

    @Test
    @DisplayName("Should call save only once")
    void shouldCallSaveOnlyOnce() {

        // Arrange
        UUID restaurantId = UUID.randomUUID();

        Restaurant restaurant = mock(Restaurant.class);

        MenuRequest request = createRequest(
                restaurantId
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.save(any(Menu.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        useCase.execute(request);

        // Assert
        verify(
                menuGateway,
                times(1)
        ).save(any(Menu.class));
    }

    private MenuRequest createRequest(UUID restaurantId) {

        return new MenuRequest(
                "Feijoada",
                "Traditional Brazilian feijoada",
                new BigDecimal("39.90"),
                true,
                "feijoada.jpg",
                restaurantId.toString(),
                true
        );
    }
}

