package br.com.lucasoliveira.apiendereco.controller;

import br.com.lucasoliveira.apiendereco.exception.PostalCodeNotFoundException;
import br.com.lucasoliveira.apiendereco.service.CepService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Api Endereco", description = "Api Endereco management APIs")
@CrossOrigin(origins = "http://localhost:8080")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    private final CepService cepService;

    @GetMapping("/{cep}")
    public ResponseEntity<?> getEndereco(@PathVariable("cep") String postalCode) throws PostalCodeNotFoundException {
        logger.info("Iniciando consulta de endereço para o CEP: {}", postalCode);
        return ResponseEntity.ok(cepService.findAddress(postalCode));


    }

}
