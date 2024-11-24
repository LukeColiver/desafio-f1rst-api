package br.com.lucasoliveira.addressapi.exception;


import br.com.lucasoliveira.addressapi.model.ErrorResponse;
import br.com.lucasoliveira.addressapi.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;



@ControllerAdvice
public class GlobalExceptionHandler {

    private final LogService logService;

    public GlobalExceptionHandler(LogService logService) {
        this.logService = logService;
    }

    @ExceptionHandler(PostalCodeNotFoundException.class)
    public ResponseEntity<?> handlePostalCodeNotFound(PostalCodeNotFoundException e) {
        logError("Postal code not found: " + e.getMessage(), "400");
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                "400",
                "The provided postal code was not found in the database."
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);


    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception e) {
        logError("Internal server error: " + e.getMessage(), "500");
        ErrorResponse errorResponse = new ErrorResponse(
                "An unexpected error occurred",
                "500",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private void logError(String message, String errorCode) {

        logService.sendLog(message,errorCode);
    }



}

