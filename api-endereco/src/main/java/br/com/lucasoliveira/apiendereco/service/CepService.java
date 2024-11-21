package br.com.lucasoliveira.apiendereco.service;

import org.springframework.http.ResponseEntity;

public interface CepService {
    ResponseEntity<?> findAddress(String cep);
}
