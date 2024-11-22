package br.com.lucasoliveira.apiendereco.service;

import br.com.lucasoliveira.apiendereco.model.PostalCode;
import br.com.lucasoliveira.apiendereco.exception.PostalCodeNotFoundException;

public interface CepService {
    PostalCode findAddress(String postalCode) throws PostalCodeNotFoundException;
}
