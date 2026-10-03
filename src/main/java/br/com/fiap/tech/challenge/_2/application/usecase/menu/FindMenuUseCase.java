package br.com.fiap.tech.challenge._2.application.usecase.menu;

import br.com.fiap.tech.challenge._2.domain.Menu;
import br.com.fiap.tech.challenge._2.domain.Restaurant;
import java.util.UUID;


public interface FindMenuUseCase {
    Menu execute(UUID id);
}
