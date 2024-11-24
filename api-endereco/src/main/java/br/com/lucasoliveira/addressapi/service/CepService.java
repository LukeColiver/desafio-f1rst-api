package br.com.lucasoliveira.addressapi.service;

import br.com.lucasoliveira.addressapi.model.PostalCode;
import br.com.lucasoliveira.addressapi.exception.PostalCodeNotFoundException;

public interface CepService {
    PostalCode findAddress(String postalCode) throws PostalCodeNotFoundException;
}
