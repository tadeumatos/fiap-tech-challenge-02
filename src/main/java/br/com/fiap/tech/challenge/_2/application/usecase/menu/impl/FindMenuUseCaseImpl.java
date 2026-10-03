package br.com.fiap.tech.challenge._2.application.usecase.menu.impl;

import br.com.fiap.tech.challenge._2.application.gateway.MenuGateway;
import br.com.fiap.tech.challenge._2.application.usecase.menu.FindMenuUseCase;
import br.com.fiap.tech.challenge._2.domain.Menu;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;

import java.util.UUID;

public class FindMenuUseCaseImpl implements FindMenuUseCase {
    private final MenuGateway menuGateway;

    public FindMenuUseCaseImpl(MenuGateway menuGateway) {
        this.menuGateway = menuGateway;

    }

    @Override
    public Menu execute(UUID id) {

        return menuGateway.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("The menu not found"));
    }
}
