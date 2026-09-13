package br.com.fiap.tech.challenge._2.application.exceptions;

public class AlreadyExistEmailException extends RuntimeException{
    public AlreadyExistEmailException(String message) {
        super(message);
    }
}
