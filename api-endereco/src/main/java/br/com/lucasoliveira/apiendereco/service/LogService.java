package br.com.lucasoliveira.apiendereco.service;

import br.com.lucasoliveira.apiendereco.entity.LogApi;

public interface LogService {
    void enviarLog(LogApi log);
}