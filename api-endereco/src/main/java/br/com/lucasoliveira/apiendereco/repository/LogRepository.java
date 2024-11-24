package br.com.lucasoliveira.apiendereco.repository;

import br.com.lucasoliveira.apiendereco.entity.LogApi;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogRepository extends MongoRepository<LogApi, String> {


    List<LogApi> findByCallData(String callData);

    List<LogApi> findByResponseStatus(String responseStatus);

    List<LogApi> findByCallTimestampBetween(String startDate, String endDate);
}
