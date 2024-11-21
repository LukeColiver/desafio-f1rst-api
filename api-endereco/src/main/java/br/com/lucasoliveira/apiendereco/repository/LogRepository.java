package br.com.lucasoliveira.apiendereco.repository;

import br.com.lucasoliveira.apiendereco.entity.LogApi;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogRepository extends MongoRepository<LogApi, String> {


    // Busca logs por callData (data da chamada)
    List<LogApi> findByCallData(String callData);

    // Busca logs por responseStatus (status da resposta)
    List<LogApi> findByResponseStatus(String responseStatus);

    // Busca logs por intervalo de callTimestamp (timestamp da chamada)
    List<LogApi> findByCallTimestampBetween(String startDate, String endDate);
}
