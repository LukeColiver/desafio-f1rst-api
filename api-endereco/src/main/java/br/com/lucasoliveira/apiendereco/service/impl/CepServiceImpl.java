package br.com.lucasoliveira.apiendereco.service.impl;

import br.com.lucasoliveira.apiendereco.client.BrasilApiClient;
import br.com.lucasoliveira.apiendereco.client.ViaCepClient;
import br.com.lucasoliveira.apiendereco.controller.EnderecoController;
import br.com.lucasoliveira.apiendereco.dto.CepDTO;
import br.com.lucasoliveira.apiendereco.service.CepService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class CepServiceImpl implements CepService {

    private static final Logger logger =  LoggerFactory.getLogger(CepServiceImpl.class);


    private final ViaCepClient viaCepClient;

    private final BrasilApiClient brasilApiClient;


    public CepDTO fallBackFindEndereco(String cep, Exception e){
        logger.info("Falling back findEndereco");
        return brasilApiClient.findEndereco(cep).to();
    }


    @Override
    @CircuitBreaker(name = "endereco", fallbackMethod = "fallBackFindEndereco")
    public ResponseEntity<?> findEndereco(String cep) {
        logger.info("Busca de endereco por cep via viaCepClient");
        // Call ViaCepClient to get CepDTO
        CepDTO cepDTO = viaCepClient.findEndereco(cep).to();

        if (cepDTO == null|| cepDTO.getCep() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("CEP não foi encontrado.");
        }

        return ResponseEntity.ok(cepDTO);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body("Erro ao consultar o CEP: " + ex.getMessage());
    }
}