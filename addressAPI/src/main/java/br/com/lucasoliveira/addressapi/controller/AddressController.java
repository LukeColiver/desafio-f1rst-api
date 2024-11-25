package br.com.lucasoliveira.addressapi.controller;

import br.com.lucasoliveira.addressapi.exception.PostalCodeNotFoundException;
import br.com.lucasoliveira.addressapi.service.CepService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Busca informações de um CEP", description = "Retorna os detalhes do endereço para o CEP fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso na consulta"),
            @ApiResponse(responseCode = "400", description = "CEP inválido ou não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{cep}")
    public ResponseEntity<?> getEndereco(@PathVariable("cep") String postalCode) throws PostalCodeNotFoundException {
        logger.info("Iniciando consulta de endereço para o CEP: {}", postalCode);
        return ResponseEntity.ok(cepService.findAddress(postalCode));


    }

}
