package br.com.fiap.tech.challenge._2.presentation.controller.response;

import java.util.UUID;

public record UserResponse(UUID id, String name, String email, UserTypeResponse userTypeResponse) {
}
