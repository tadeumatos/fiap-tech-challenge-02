package br.com.fiap.tech.challenge._2.application.usecase.menu.impl;

import br.com.fiap.tech.challenge._2.application.gateway.MenuGateway;
import br.com.fiap.tech.challenge._2.domain.Menu;
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
class FindMenuUseCaseImplTest {

    @Mock
    private MenuGateway menuGateway;

    private FindMenuUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new FindMenuUseCaseImpl(
                menuGateway
        );
    }

    @Test
    @DisplayName("Should find menu successfully")
    void shouldFindMenuSuccessfully() {

        // Arrange
        UUID menuId = UUID.randomUUID();

        Menu menu = mock(Menu.class);

        when(menuGateway.findById(menuId))
                .thenReturn(Optional.of(menu));

        // Act
        Menu result = useCase.execute(menuId);

        // Assert
        assertNotNull(result);
        assertSame(menu, result);

        verify(menuGateway)
                .findById(menuId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when menu does not exist")
    void shouldThrowResourceNotFoundExceptionWhenMenuDoesNotExist() {

        // Arrange
        UUID menuId = UUID.randomUUID();

        when(menuGateway.findById(menuId))
                .thenReturn(Optional.empty());

        // Act + Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(menuId)
        );

        assertEquals(
                "The menu not found",
                exception.getMessage()
        );

        verify(menuGateway)
                .findById(menuId);
    }

    @Test
    @DisplayName("Should call findById only once")
    void shouldCallFindByIdOnlyOnce() {

        // Arrange
        UUID menuId = UUID.randomUUID();

        when(menuGateway.findById(menuId))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> useCase.execute(menuId)
        );

        // Assert
        verify(
                menuGateway,
                times(1)
        ).findById(menuId);
    }

    @Test
    @DisplayName("Should return the exact menu returned by the gateway")
    void shouldReturnTheExactMenuReturnedByGateway() {

        // Arrange
        UUID menuId = UUID.randomUUID();

        Menu menu = mock(Menu.class);

        when(menuGateway.findById(menuId))
                .thenReturn(Optional.of(menu));

        // Act
        Menu result = useCase.execute(menuId);

        // Assert
        assertSame(menu, result);

        verify(menuGateway)
                .findById(menuId);
    }
}

