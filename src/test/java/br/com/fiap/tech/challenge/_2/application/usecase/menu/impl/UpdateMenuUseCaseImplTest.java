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
class UpdateMenuUseCaseImplTest {

    @Mock
    private MenuGateway menuGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private Restaurant restaurant;

    @Mock
    private Menu updatedMenu;

    private UpdateMenuUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateMenuUseCaseImpl(
                menuGateway,
                restaurantGateway
        );
    }

    @Test
    @DisplayName("Should update menu successfully")
    void shouldUpdateMenuSuccessfully() {

        // Arrange
        UUID menuId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        MenuRequest request = new MenuRequest(
                "Pizza Calabresa",
                "Pizza de calabresa com queijo",
                BigDecimal.valueOf(45.90),
                true,
                "pizza-calabresa.jpg",
                restaurantId.toString(),
                true
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.update(
                eq(menuId),
                any(Menu.class)
        )).thenReturn(updatedMenu);

        // Act
        Menu result = useCase.execute(
                menuId,
                request
        );

        // Assert
        assertNotNull(result);
        assertSame(updatedMenu, result);

        verify(restaurantGateway)
                .findById(restaurantId);

        verify(menuGateway)
                .update(
                        eq(menuId),
                        any(Menu.class)
                );
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when restaurant does not exist")
    void shouldThrowResourceNotFoundExceptionWhenRestaurantDoesNotExist() {

        // Arrange
        UUID menuId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        MenuRequest request = new MenuRequest(
                "Pizza Calabresa",
                "Pizza de calabresa com queijo",
                BigDecimal.valueOf(45.90),
                true,
                "pizza-calabresa.jpg",
                restaurantId.toString(),
                true
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.empty());

        // Act + Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(menuId, request)
        );

        assertEquals(
                "The restaurant not found",
                exception.getMessage()
        );

        verify(restaurantGateway)
                .findById(restaurantId);

        verify(
                menuGateway,
                never()
        ).update(
                any(UUID.class),
                any(Menu.class)
        );
    }

    @Test
    @DisplayName("Should use the provided menu ID when updating")
    void shouldUseProvidedMenuIdWhenUpdating() {

        // Arrange
        UUID menuId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        MenuRequest request = new MenuRequest(
                "Hamburger",
                "Hamburger artesanal",
                BigDecimal.valueOf(30.00),
                false,
                "hamburger.jpg",
                restaurantId.toString(),
                true
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.update(
                eq(menuId),
                any(Menu.class)
        )).thenReturn(updatedMenu);

        // Act
        useCase.execute(menuId, request);

        // Assert
        verify(menuGateway)
                .update(
                        eq(menuId),
                        any(Menu.class)
                );
    }

    @Test
    @DisplayName("Should create menu with request data")
    void shouldCreateMenuWithRequestData() {

        // Arrange
        UUID menuId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        MenuRequest request = new MenuRequest(
                "Pizza Portuguesa",
                "Pizza com presunto, queijo e ovos",
                BigDecimal.valueOf(50.00),
                true,
                "pizza-portuguesa.jpg",
                restaurantId.toString(),
                false
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.update(
                eq(menuId),
                any(Menu.class)
        )).thenReturn(updatedMenu);

        ArgumentCaptor<Menu> menuCaptor =
                ArgumentCaptor.forClass(Menu.class);

        // Act
        useCase.execute(
                menuId,
                request
        );

        // Assert
        verify(menuGateway)
                .update(
                        eq(menuId),
                        menuCaptor.capture()
                );

        Menu menu = menuCaptor.getValue();

        assertNotNull(menu);
        assertEquals(
                request.name(),
                menu.getName()
        );

        assertEquals(
                request.description(),
                menu.getDescription()
        );

        assertEquals(
                request.price(),
                menu.getPrice()
        );

        assertEquals(
                request.onlyLocal(),
                menu.isOnlyLocal()
        );

        assertEquals(
                request.foodPhoto(),
                menu.getFoodPhoto()
        );

        assertEquals(
                request.active(),
                menu.isActive()
        );

        assertSame(
                restaurant,
                menu.getRestaurant()
        );
    }

    @Test
    @DisplayName("Should generate a new ID for the updated menu")
    void shouldGenerateNewIdForUpdatedMenu() {

        // Arrange
        UUID menuId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        MenuRequest request = new MenuRequest(
                "Pizza",
                "Pizza tradicional",
                BigDecimal.valueOf(40.00),
                false,
                "pizza.jpg",
                restaurantId.toString(),
                true
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.update(
                eq(menuId),
                any(Menu.class)
        )).thenReturn(updatedMenu);

        ArgumentCaptor<Menu> menuCaptor =
                ArgumentCaptor.forClass(Menu.class);

        // Act
        useCase.execute(
                menuId,
                request
        );

        // Assert
        verify(menuGateway)
                .update(
                        eq(menuId),
                        menuCaptor.capture()
                );

        Menu menu = menuCaptor.getValue();

        assertNotNull(menu.getId());
        assertNotEquals(
                menuId,
                menu.getId()
        );
    }

    @Test
    @DisplayName("Should find restaurant before updating menu")
    void shouldFindRestaurantBeforeUpdatingMenu() {

        // Arrange
        UUID menuId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        MenuRequest request = new MenuRequest(
                "Sushi",
                "Combinado de sushi",
                BigDecimal.valueOf(80.00),
                true,
                "sushi.jpg",
                restaurantId.toString(),
                true
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.update(
                eq(menuId),
                any(Menu.class)
        )).thenReturn(updatedMenu);

        // Act
        useCase.execute(
                menuId,
                request
        );

        // Assert
        InOrder inOrder = inOrder(
                restaurantGateway,
                menuGateway
        );

        inOrder.verify(
                restaurantGateway
        ).findById(restaurantId);

        inOrder.verify(
                menuGateway
        ).update(
                eq(menuId),
                any(Menu.class)
        );
    }

    @Test
    @DisplayName("Should find restaurant only once")
    void shouldFindRestaurantOnlyOnce() {

        // Arrange
        UUID menuId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        MenuRequest request = new MenuRequest(
                "Lasagna",
                "Lasagna tradicional",
                BigDecimal.valueOf(55.00),
                false,
                "lasagna.jpg",
                restaurantId.toString(),
                true
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.update(
                eq(menuId),
                any(Menu.class)
        )).thenReturn(updatedMenu);

        // Act
        useCase.execute(
                menuId,
                request
        );

        // Assert
        verify(
                restaurantGateway,
                times(1)
        ).findById(restaurantId);
    }

    @Test
    @DisplayName("Should update menu only once")
    void shouldUpdateMenuOnlyOnce() {

        // Arrange
        UUID menuId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        MenuRequest request = new MenuRequest(
                "Salada",
                "Salada especial",
                BigDecimal.valueOf(25.00),
                true,
                "salada.jpg",
                restaurantId.toString(),
                true
        );

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(menuGateway.update(
                eq(menuId),
                any(Menu.class)
        )).thenReturn(updatedMenu);

        // Act
        useCase.execute(
                menuId,
                request
        );

        // Assert
        verify(
                menuGateway,
                times(1)
        ).update(
                eq(menuId),
                any(Menu.class)
        );
    }
}

