package br.com.lucasoliveira.apiendereco.controller;

import br.com.lucasoliveira.apiendereco.dto.CepDTO;
import br.com.lucasoliveira.apiendereco.service.CepService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {

    @Autowired
    private  CepService cepService;

    public EnderecoController(CepService cepService) {
        this.cepService = cepService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<?> getEndereco(@PathVariable("cep") String cep) {

        return cepService.findEndereco(cep);
    }

}
