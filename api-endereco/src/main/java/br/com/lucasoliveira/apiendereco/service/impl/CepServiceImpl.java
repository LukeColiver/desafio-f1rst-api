package br.com.lucasoliveira.apiendereco.service.impl;

import br.com.lucasoliveira.apiendereco.client.BrasilApiClient;
import br.com.lucasoliveira.apiendereco.client.ViaCepClient;
import br.com.lucasoliveira.apiendereco.model.PostalCode;
import br.com.lucasoliveira.apiendereco.exception.PostalCodeNotFoundException;
import br.com.lucasoliveira.apiendereco.service.CepService;
import br.com.lucasoliveira.apiendereco.service.LogService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CepServiceImpl implements CepService {

    private static final Logger logger = LoggerFactory.getLogger(CepServiceImpl.class);


    private final ViaCepClient viaCepClient;

    private final BrasilApiClient brasilApiClient;

    private final LogService logService;

    private PostalCode postalCode;


    public PostalCode fallbackMethod(String postalCode, Exception e) throws PostalCodeNotFoundException {
        logger.error("Falha ao buscar CEP {} via ViaCep, tentaremos via BrasilApi. Erro: {}", postalCode, e.getMessage());

        this.postalCode = brasilApiClient.findAddress(postalCode).to();

        if (this.postalCode == null || this.postalCode.getPostalCode() == null) {
            throw new PostalCodeNotFoundException("Postal code " + postalCode + " not found.");
        }

        createLog(this.postalCode);
        logger.info("Consulta realizada com sucesso para o CEP: {}. Endereço: {}", postalCode, this.postalCode.toString());


        return this.postalCode;

    }


    @CircuitBreaker(name = "endereco", fallbackMethod = "fallbackMethod")
    public PostalCode findAddress(String postalCode) throws PostalCodeNotFoundException {
        this.postalCode = viaCepClient.findAddress(postalCode).to();


        if (this.postalCode == null || this.postalCode.getPostalCode() == null) {
            throw new PostalCodeNotFoundException("Postal code " + postalCode + " not found.");

        }

        createLog(this.postalCode);
        logger.info("Consulta realizada com sucesso para o CEP: {}. Endereço: {}", postalCode, this.postalCode.toString());


        return this.postalCode;

    }

    public void createLog(PostalCode cepDTO) {
            logService.sendLog(cepDTO.toString(),"200");
    }
}



