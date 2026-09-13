package br.com.fiap.tech.challenge._2.adapters.controller.request;

public record CreateUserTypeRequest(
        String name,
        boolean owner
) {
}
