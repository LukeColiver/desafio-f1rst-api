package br.com.lucasoliveira.addressapi.entity;

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
    private String callData;
    private String responseStatus;
    private String callTimestamp;


}
