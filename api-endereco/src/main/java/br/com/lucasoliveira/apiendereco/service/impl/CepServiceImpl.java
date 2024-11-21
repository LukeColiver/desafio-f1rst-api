package br.com.lucasoliveira.apiendereco.service.impl;

import br.com.lucasoliveira.apiendereco.client.BrasilApiClient;
import br.com.lucasoliveira.apiendereco.client.ViaCepClient;
import br.com.lucasoliveira.apiendereco.dto.PostalCodeDTO;
import br.com.lucasoliveira.apiendereco.entity.LogApi;
import br.com.lucasoliveira.apiendereco.service.CepService;
import br.com.lucasoliveira.apiendereco.service.LogService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CepServiceImpl implements CepService {

    private final ViaCepClient viaCepClient;

    private final BrasilApiClient brasilApiClient;

    private final LogService logService;

    private PostalCodeDTO postalCodeDTO;


    public ResponseEntity<?> fallBackFindAddress(String cep, Exception e){

        postalCodeDTO = brasilApiClient.findAddress(cep).to();

        if (postalCodeDTO == null|| postalCodeDTO.getPostalCode() == null) {
            createLog(postalCodeDTO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("CEP não foi encontrado.");
        }

        createLog(postalCodeDTO);
        return ResponseEntity.ok(postalCodeDTO);
    }


    @CircuitBreaker(name = "endereco", fallbackMethod = "fallBackFindEndereco")
    public ResponseEntity<?> findAddress(String cep) {
        postalCodeDTO = viaCepClient.findAddress(cep).to();

        if (postalCodeDTO == null|| postalCodeDTO.getPostalCode() == null) {
            createLog(postalCodeDTO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("CEP não foi encontrado.");
        }

        createLog(postalCodeDTO);
        return ResponseEntity.ok(postalCodeDTO);
    }

    public void createLog(PostalCodeDTO cepDTO){
        LogApi logApi = new LogApi();
        String apiCallDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));

        if (cepDTO != null) {
            logApi.setCallTimestamp(apiCallDate);
            logApi.setCallData(cepDTO.toString());
            logApi.setResponseStatus(HttpStatus.OK.toString());
        }else{
            logApi.setCallTimestamp(apiCallDate);
            logApi.setCallData("CEP não foi encontrado.");
            logApi.setResponseStatus(HttpStatus.NOT_FOUND.toString());
        }

        logService.sendLog(logApi);
    }


}