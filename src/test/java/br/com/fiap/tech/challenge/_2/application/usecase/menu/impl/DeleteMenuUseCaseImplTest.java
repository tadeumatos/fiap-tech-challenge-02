package br.com.fiap.tech.challenge._2.application.usecase.menu.impl;

import br.com.fiap.tech.challenge._2.application.gateway.MenuGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteMenuUseCaseImplTest {

    @Mock
    private MenuGateway menuGateway;

    private DeleteMenuUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteMenuUseCaseImpl(
                menuGateway
        );
    }

    @Test
    @DisplayName("Should delete menu successfully")
    void shouldDeleteMenuSuccessfully() {

        // Arrange
        UUID menuId = UUID.randomUUID();

        // Act
        assertDoesNotThrow(
                () -> useCase.execute(menuId)
        );

        // Assert
        verify(menuGateway)
                .delete(menuId);
    }

    @Test
    @DisplayName("Should call delete only once")
    void shouldCallDeleteOnlyOnce() {

        // Arrange
        UUID menuId = UUID.randomUUID();

        // Act
        useCase.execute(menuId);

        // Assert
        verify(
                menuGateway,
                times(1)
        ).delete(menuId);
    }

    @Test
    @DisplayName("Should delete the menu using the provided ID")
    void shouldDeleteMenuUsingProvidedId() {

        // Arrange
        UUID menuId = UUID.randomUUID();

        // Act
        useCase.execute(menuId);

        // Assert
        verify(
                menuGateway,
                times(1)
        ).delete(menuId);
    }

    @Test
    @DisplayName("Should not delete another menu")
    void shouldNotDeleteAnotherMenu() {

        // Arrange
        UUID menuId = UUID.randomUUID();
        UUID anotherMenuId = UUID.randomUUID();

        // Act
        useCase.execute(menuId);

        // Assert
        verify(menuGateway)
                .delete(menuId);

        verify(
                menuGateway,
                never()
        ).delete(anotherMenuId);
    }
}

