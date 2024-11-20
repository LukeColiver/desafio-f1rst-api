package br.com.lucasoliveira.apiendereco.service.impl;

import br.com.lucasoliveira.apiendereco.client.BrasilApiClient;
import br.com.lucasoliveira.apiendereco.client.ViaCepClient;
import br.com.lucasoliveira.apiendereco.dto.CepDTO;
import br.com.lucasoliveira.apiendereco.service.CepService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class CepServiceImpl implements CepService {

    private final ViaCepClient viaCepClient;

    private final BrasilApiClient brasilApiClient;


    public CepDTO fallBackFindEndereco(String cep, Exception e){
        return brasilApiClient.findEndereco(cep).to();
    }


    @Override
    @CircuitBreaker(name = "endereco", fallbackMethod = "fallBackFindEndereco")
    public ResponseEntity<?> findEndereco(String cep) {
        // Call ViaCepClient to get CepDTO
        CepDTO cepDTO = viaCepClient.findEndereco(cep).to();

        // Check if the result is null or the relevant fields are empty
        if (cepDTO == null|| cepDTO.getCep() == null) {
            // Return an error response (HTTP 404 - Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("CEP não foi encontrado.");
        }

        // If valid CepDTO, return it (OK response)
        return ResponseEntity.ok(cepDTO);
    }

    // Exception handler for custom exceptions
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException ex) {
        // Handle any specific client errors here (optional)
        return ResponseEntity.status(ex.getStatusCode())
                .body("Erro ao consultar o CEP: " + ex.getMessage());
    }
}