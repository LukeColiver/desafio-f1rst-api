package br.com.lucasoliveira.apiendereco.service.impl;

import br.com.lucasoliveira.apiendereco.client.BrasilApiClient;
import br.com.lucasoliveira.apiendereco.client.ViaCepClient;
import br.com.lucasoliveira.apiendereco.dto.CepDTO;
import br.com.lucasoliveira.apiendereco.entity.LogApiEndereco;
import br.com.lucasoliveira.apiendereco.repository.LogRepository;
import br.com.lucasoliveira.apiendereco.service.CepService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CepServiceImpl implements CepService {

    private final ViaCepClient viaCepClient;

    private final BrasilApiClient brasilApiClient;

    private final LogRepository logRepository;


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
            String dataChamada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
            LogApiEndereco logApiEndereco = new LogApiEndereco();
            logApiEndereco.setTimestamp(dataChamada);
            logApiEndereco.setDataChamada("CEP não foi encontrado.");
            logApiEndereco.setResultado(HttpStatus.NOT_FOUND.toString());
            logRepository.save(logApiEndereco);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("CEP não foi encontrado.");
        }

        String dataChamada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
        LogApiEndereco logApiEndereco = new LogApiEndereco();
        logApiEndereco.setTimestamp(dataChamada);
        logApiEndereco.setDataChamada(cepDTO.toString());
        logApiEndereco.setResultado(HttpStatus.OK.toString());
        logRepository.save(logApiEndereco);
        // If valid CepDTO, return it (OK response)
        return ResponseEntity.ok(cepDTO);
    }


}