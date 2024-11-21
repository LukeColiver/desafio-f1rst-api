package br.com.lucasoliveira.apiendereco.service.impl;

import br.com.lucasoliveira.apiendereco.client.BrasilApiClient;
import br.com.lucasoliveira.apiendereco.client.ViaCepClient;
import br.com.lucasoliveira.apiendereco.dto.CepDTO;
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

    private CepDTO cepDTO;


    public ResponseEntity<?> fallBackFindEndereco(String cep, Exception e){

        cepDTO = brasilApiClient.findEndereco(cep).to();

        if (cepDTO == null|| cepDTO.getCep() == null) {
            createLog(cepDTO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("CEP não foi encontrado.");
        }

        createLog(cepDTO);
        return ResponseEntity.ok(cepDTO);
    }


    @Override
    @CircuitBreaker(name = "endereco", fallbackMethod = "fallBackFindEndereco")
    public ResponseEntity<?> findEndereco(String cep) {
        cepDTO = viaCepClient.findEndereco(cep).to();

        if (cepDTO == null|| cepDTO.getCep() == null) {
            createLog(cepDTO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("CEP não foi encontrado.");
        }

        createLog(cepDTO);
        return ResponseEntity.ok(cepDTO);
    }

    public void createLog(CepDTO cepDTO){
        LogApi logApi = new LogApi();
        String apiCallDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));

        if (cepDTO != null) {
            logApi.setTimestamp(apiCallDate);
            logApi.setDataChamada(cepDTO.toString());
            logApi.setResultado(HttpStatus.OK.toString());
        }else{
            logApi.setTimestamp(apiCallDate);
            logApi.setDataChamada("CEP não foi encontrado.");
            logApi.setResultado(HttpStatus.NOT_FOUND.toString());

        }

        logService.enviarLog(logApi);
    }


}