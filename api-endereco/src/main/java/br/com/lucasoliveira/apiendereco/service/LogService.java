package br.com.lucasoliveira.apiendereco.service;

import br.com.lucasoliveira.apiendereco.entity.LogApi;

import java.util.List;

public interface LogService {
    void sendLog(LogApi log);

    List<LogApi> getAllLogs(); // Busca geral

    List<LogApi> getLogsByCallData(String callData); // Busca por data

    List<LogApi> getLogsByResponseStatus(String responseStatus); // Busca por status

    List<LogApi> getLogsByTimestampRange(String startDate, String endDate);
}