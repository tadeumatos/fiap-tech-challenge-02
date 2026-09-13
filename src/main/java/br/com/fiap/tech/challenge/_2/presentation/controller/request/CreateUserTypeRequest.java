package br.com.fiap.tech.challenge._2.presentation.controller.request;

public record CreateUserTypeRequest(
        String name,
        boolean owner
) {
}
