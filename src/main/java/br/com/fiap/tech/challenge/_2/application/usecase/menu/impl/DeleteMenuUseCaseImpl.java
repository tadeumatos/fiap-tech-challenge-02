package br.com.fiap.tech.challenge._2.application.usecase.menu.impl;

import br.com.fiap.tech.challenge._2.application.gateway.MenuGateway;
import br.com.fiap.tech.challenge._2.application.usecase.menu.DeleteMenuUseCase;
import java.util.UUID;

public class DeleteMenuUseCaseImpl implements DeleteMenuUseCase {
    private final MenuGateway menuGateway;

    public DeleteMenuUseCaseImpl(MenuGateway menuGateway) {

        this.menuGateway = menuGateway;
    }

    @Override
    public void execute(UUID id) {
        menuGateway.delete(id);
    }
}
