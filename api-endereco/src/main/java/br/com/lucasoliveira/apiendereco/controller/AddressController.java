package br.com.lucasoliveira.apiendereco.controller;

import br.com.lucasoliveira.apiendereco.exception.PostalCodeNotFoundException;
import br.com.lucasoliveira.apiendereco.service.CepService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    private final CepService cepService;

    @GetMapping("/{cep}")
    public ResponseEntity<?> getEndereco(@PathVariable("cep") String postalCode) throws PostalCodeNotFoundException {
        logger.info("Iniciando consulta de endereço para o CEP: {}", postalCode);
        return ResponseEntity.ok(cepService.findAddress(postalCode));


    }

}
