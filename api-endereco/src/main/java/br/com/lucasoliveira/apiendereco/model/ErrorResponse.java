package br.com.lucasoliveira.apiendereco.model;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ErrorResponse {

    private String message;
    private String errorCode;
    private String timestamp;
    private String details;


    public ErrorResponse(String message, String errorCode, String details) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
        this.details = details;
    }

    public ErrorResponse() {

    }
}

