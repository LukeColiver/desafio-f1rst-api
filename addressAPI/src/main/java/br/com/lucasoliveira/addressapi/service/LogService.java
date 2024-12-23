package br.com.lucasoliveira.addressapi.service;

import br.com.lucasoliveira.addressapi.entity.LogApi;

import java.util.List;

public interface LogService {
    void sendLog(String message, String statusCode);

    List<LogApi> getAllLogs();

    List<LogApi> getLogsByCallData(String callData);

    List<LogApi> getLogsByResponseStatus(String responseStatus);

    List<LogApi> getLogsByTimestampRange(String startDate, String endDate);


}