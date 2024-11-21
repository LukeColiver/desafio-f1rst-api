package br.com.lucasoliveira.apiendereco.service.impl;

import br.com.lucasoliveira.apiendereco.client.BrasilApiClient;
import br.com.lucasoliveira.apiendereco.client.ViaCepClient;
import br.com.lucasoliveira.apiendereco.controller.AddressController;
import br.com.lucasoliveira.apiendereco.dto.PostalCodeDTO;
import br.com.lucasoliveira.apiendereco.entity.LogApi;
import br.com.lucasoliveira.apiendereco.service.CepService;
import br.com.lucasoliveira.apiendereco.service.LogService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CepServiceImpl implements CepService {

    private static final Logger logger = LoggerFactory.getLogger(CepServiceImpl.class);


    private final ViaCepClient viaCepClient;

    private final BrasilApiClient brasilApiClient;

    private final LogService logService;

    private PostalCodeDTO postalCodeDTO;


    public ResponseEntity<?> fallBackFindAddress(String cep, Exception e){

        postalCodeDTO = brasilApiClient.findAddress(cep).to();
        try {
            if (postalCodeDTO == null|| postalCodeDTO.getPostalCode() == null) {
                createLog(postalCodeDTO);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("CEP não foi encontrado.");
            }

            createLog(postalCodeDTO);
            return ResponseEntity.ok(postalCodeDTO);
        } catch (Exception exception) {
            logger.error("Erro ao consultar o endereço para o CEP: {}. Erro: {}", cep, exception.getMessage());
            createLog(exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno no servidor: " + exception.getMessage());
        }

    }



    @CircuitBreaker(name = "endereco", fallbackMethod = "fallBackFindEndereco")
    public ResponseEntity<?> findAddress(String cep) {
        postalCodeDTO = viaCepClient.findAddress(cep).to();

        try {
            if (postalCodeDTO == null|| postalCodeDTO.getPostalCode() == null) {
                createLog(postalCodeDTO);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("CEP não foi encontrado.");
            }

            createLog(postalCodeDTO);
            logger.info("Consulta realizada com sucesso para o CEP: {}. Endereço: {}", cep, postalCodeDTO.toString());
            return ResponseEntity.ok(postalCodeDTO);


        } catch (Exception e) {
            logger.error("Erro ao consultar o endereço para o CEP: {}. Erro: {}", cep, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno no servidor: " + e.getMessage());
        }


    }

    public void createLog(PostalCodeDTO cepDTO){
        LogApi logApi = new LogApi();
        String apiCallDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));

        if (cepDTO != null) {
            logApi.setCallTimestamp(apiCallDate);
            logApi.setCallData(cepDTO.toString());
            logApi.setResponseStatus(String.valueOf(HttpStatus.OK.value()));
        }else{
            logApi.setCallTimestamp(apiCallDate);
            logApi.setCallData("CEP não foi encontrado.");
            logApi.setResponseStatus(String.valueOf(HttpStatus.NOT_FOUND.value()));
        }

        logService.sendLog(logApi);
    }



    private void createLog(Exception exception) {
        LogApi logApi = new LogApi();
        String apiCallDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
        logApi.setCallTimestamp(apiCallDate);
        logApi.setCallData(exception.getMessage());
        logApi.setResponseStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

}