package br.com.fiap.tech.challenge._2.domain.exception;

public class ValidationFieldsException extends RuntimeException{

    public ValidationFieldsException(String message) {
        super(message);
    }
}
