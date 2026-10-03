package br.com.fiap.tech.challenge._2.presentation.controller.response;

import br.com.fiap.tech.challenge._2.presentation.presenter.RestaurantPresenter;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuResponse(UUID id, String name, String description, BigDecimal price,boolean onlyLocal,String foodPhoto, RestaurantResponse restaurantResponse, boolean active) {
}
