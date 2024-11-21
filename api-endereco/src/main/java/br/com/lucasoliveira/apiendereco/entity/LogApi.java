package br.com.lucasoliveira.apiendereco.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogApi {

    @Id
    private String id;
    private String dataChamada; // Data da chamada
    private String resultado;   // Resultado da chamada
    private String timestamp;
}
