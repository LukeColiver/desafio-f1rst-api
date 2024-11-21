package br.com.lucasoliveira.apiendereco.repository;

import br.com.lucasoliveira.apiendereco.entity.LogApi;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<LogApi, String> {
}
