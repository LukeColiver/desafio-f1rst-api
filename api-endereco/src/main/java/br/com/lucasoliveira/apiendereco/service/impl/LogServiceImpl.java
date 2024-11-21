package br.com.lucasoliveira.apiendereco.service.impl;

import br.com.lucasoliveira.apiendereco.entity.LogApi;
import br.com.lucasoliveira.apiendereco.repository.LogRepository;
import br.com.lucasoliveira.apiendereco.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;


    @Override
    public void enviarLog(LogApi log) {
        // Aqui o log é salvo no repositório (banco de dados)
        logRepository.save(log);
    }


}
