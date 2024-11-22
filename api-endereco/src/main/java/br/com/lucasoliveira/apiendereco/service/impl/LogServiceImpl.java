package br.com.lucasoliveira.apiendereco.service.impl;

import br.com.lucasoliveira.apiendereco.entity.LogApi;
import br.com.lucasoliveira.apiendereco.repository.LogRepository;
import br.com.lucasoliveira.apiendereco.service.LogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);


    private final LogRepository logRepository;


    public void sendLog(String message, String statusCode) {
        LogApi log = new LogApi();
        String apiCallDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
        log.setCallTimestamp(apiCallDate);
        log.setCallData(message);
        log.setResponseStatus(statusCode);
        logger.info("Registrando Log da chamada da api na base de dados : Chamada da api retornou : {}", log.getResponseStatus());
        logRepository.save(log);
    }




    @Override
    public List<LogApi> getAllLogs() {
        logger.info("Buscando todos os logs da API.");
        return logRepository.findAll();
    }

    @Override
    public List<LogApi> getLogsByCallData(String callData) {
        logger.info("Buscando logs da API por data: {}", callData);
        return logRepository.findByCallData(callData);
    }

    @Override
    public List<LogApi> getLogsByResponseStatus(String responseStatus) {
        logger.info("Buscando logs da API por status de resposta: {}", responseStatus);
        return logRepository.findByResponseStatus(responseStatus);
    }

    @Override
    public List<LogApi> getLogsByTimestampRange(String startDate, String endDate) {
        logger.info("Buscando logs da API entre as datas: {} e {}", startDate, endDate);
        return logRepository.findByCallTimestampBetween(startDate, endDate);
    }

}
