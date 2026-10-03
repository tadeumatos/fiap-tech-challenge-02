package br.com.fiap.tech.challenge._2.presentation.controller.response;

import java.util.UUID;

public record UserTypeResponse(UUID id,String name,boolean owner) {
}
