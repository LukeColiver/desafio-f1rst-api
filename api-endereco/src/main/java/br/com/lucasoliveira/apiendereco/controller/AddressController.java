package br.com.lucasoliveira.apiendereco.controller;

import br.com.lucasoliveira.apiendereco.exception.PostalCodeNotFoundException;
import br.com.lucasoliveira.apiendereco.service.CepService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);


    @Autowired
    private CepService cepService;

    public AddressController(CepService cepService) {
        this.cepService = cepService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<?> getEndereco(@PathVariable("cep") String postalCode) throws PostalCodeNotFoundException {
        logger.info("Iniciando consulta de endereço para o CEP: {}", postalCode);
        return ResponseEntity.ok(cepService.findAddress(postalCode));


    }

}
