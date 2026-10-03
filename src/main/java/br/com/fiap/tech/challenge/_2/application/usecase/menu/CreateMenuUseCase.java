package br.com.fiap.tech.challenge._2.application.usecase.menu;

import br.com.fiap.tech.challenge._2.domain.Menu;

import br.com.fiap.tech.challenge._2.presentation.controller.request.MenuRequest;



public interface CreateMenuUseCase {
    Menu execute(MenuRequest request);
}
