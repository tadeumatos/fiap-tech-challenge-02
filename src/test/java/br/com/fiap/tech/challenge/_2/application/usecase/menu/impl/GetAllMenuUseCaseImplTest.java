package br.com.fiap.tech.challenge._2.application.usecase.menu.impl;

import br.com.fiap.tech.challenge._2.application.gateway.MenuGateway;
import br.com.fiap.tech.challenge._2.domain.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllMenuUseCaseImplTest {

    @Mock
    private MenuGateway menuGateway;

    private GetAllMenuUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetAllMenuUseCaseImpl(
                menuGateway
        );
    }

    @Test
    @DisplayName("Should return all menus successfully")
    void shouldReturnAllMenusSuccessfully() {

        // Arrange
        Menu menu1 = mock(Menu.class);
        Menu menu2 = mock(Menu.class);

        List<Menu> menus = Arrays.asList(
                menu1,
                menu2
        );

        when(menuGateway.getAll())
                .thenReturn(menus);

        // Act
        List<Menu> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(menus, result);

        verify(menuGateway)
                .getAll();
    }

    @Test
    @DisplayName("Should return an empty list when no menus exist")
    void shouldReturnEmptyListWhenNoMenusExist() {

        // Arrange
        List<Menu> menus = Collections.emptyList();

        when(menuGateway.getAll())
                .thenReturn(menus);

        // Act
        List<Menu> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(menuGateway)
                .getAll();
    }

    @Test
    @DisplayName("Should call getAll only once")
    void shouldCallGetAllOnlyOnce() {

        // Arrange
        List<Menu> menus = Collections.emptyList();

        when(menuGateway.getAll())
                .thenReturn(menus);

        // Act
        useCase.execute();

        // Assert
        verify(
                menuGateway,
                times(1)
        ).getAll();
    }

    @Test
    @DisplayName("Should return the exact list returned by the gateway")
    void shouldReturnTheExactListReturnedByGateway() {

        // Arrange
        Menu menu1 = mock(Menu.class);
        Menu menu2 = mock(Menu.class);

        List<Menu> menus = Arrays.asList(
                menu1,
                menu2
        );

        when(menuGateway.getAll())
                .thenReturn(menus);

        // Act
        List<Menu> result = useCase.execute();

        // Assert
        assertSame(menus, result);

        verify(menuGateway)
                .getAll();
    }

    @Test
    @DisplayName("Should preserve the order returned by the gateway")
    void shouldPreserveTheOrderReturnedByGateway() {

        // Arrange
        Menu menu1 = mock(Menu.class);
        Menu menu2 = mock(Menu.class);
        Menu menu3 = mock(Menu.class);

        List<Menu> menus = Arrays.asList(
                menu1,
                menu2,
                menu3
        );

        when(menuGateway.getAll())
                .thenReturn(menus);

        // Act
        List<Menu> result = useCase.execute();

        // Assert
        assertEquals(menu1, result.get(0));
        assertEquals(menu2, result.get(1));
        assertEquals(menu3, result.get(2));

        verify(menuGateway)
                .getAll();
    }
}

