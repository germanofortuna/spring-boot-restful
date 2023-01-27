package io.github.germanofortuna.exception;

public class SenhaInvalidaException extends RuntimeException{
    public SenhaInvalidaException(String message) {
        super(message);
    }

    public SenhaInvalidaException() {
        super("Senha inválida");
    }
}
