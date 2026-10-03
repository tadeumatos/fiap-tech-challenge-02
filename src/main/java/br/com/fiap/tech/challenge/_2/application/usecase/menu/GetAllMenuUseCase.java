package br.com.fiap.tech.challenge._2.application.usecase.menu;

import br.com.fiap.tech.challenge._2.domain.Menu;
import br.com.fiap.tech.challenge._2.domain.Restaurant;
import java.util.List;


public interface GetAllMenuUseCase {
    List<Menu> execute();
}
