package br.com.lucasoliveira.apiendereco.service;

import br.com.lucasoliveira.apiendereco.dto.CepDTO;
import org.springframework.http.ResponseEntity;

public interface CepService {
    ResponseEntity<?> findEndereco(String cep);
}
