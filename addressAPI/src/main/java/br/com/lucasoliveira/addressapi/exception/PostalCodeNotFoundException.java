package br.com.lucasoliveira.addressapi.exception;

public class PostalCodeNotFoundException extends Exception {

    public PostalCodeNotFoundException(String message) {
        super(message);
    }

    public PostalCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
