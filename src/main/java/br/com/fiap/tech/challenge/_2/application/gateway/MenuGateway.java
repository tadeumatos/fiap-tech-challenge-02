package br.com.fiap.tech.challenge._2.application.gateway;

import br.com.fiap.tech.challenge._2.domain.Menu;
import br.com.fiap.tech.challenge._2.domain.Restaurant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuGateway {
    Menu save(Menu menu);
    Optional<Menu> findById(UUID id);
    List<Menu> getAll();
    void delete(UUID id);
    Menu update(UUID id, Menu menu);
    boolean existRestaurant(UUID id);
}
