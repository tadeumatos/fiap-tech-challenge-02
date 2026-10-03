package br.com.fiap.tech.challenge._2.application.usecase.menu.impl;

import br.com.fiap.tech.challenge._2.application.gateway.MenuGateway;
import br.com.fiap.tech.challenge._2.application.usecase.menu.GetAllMenuUseCase;
import br.com.fiap.tech.challenge._2.domain.Menu;

import java.util.List;

public class GetAllMenuUseCaseImpl implements GetAllMenuUseCase {
    private final MenuGateway menuGateway;

    public GetAllMenuUseCaseImpl(MenuGateway menuGateway) {
        this.menuGateway = menuGateway;
    }

    @Override
    public List<Menu> execute() {

        return menuGateway.getAll();
    }
}
