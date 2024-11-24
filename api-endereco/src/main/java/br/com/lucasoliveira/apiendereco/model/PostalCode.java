package br.com.lucasoliveira.apiendereco.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostalCode {

    @JsonProperty("Cep")
    private String postalCode;

    @JsonProperty("Logradouro")
    private String street;

    @JsonProperty("Cidade")
    private String city;

    @JsonProperty("Estado")
    private String state;

    @JsonProperty("Bairro")
    private String neighborhood;

    @Override
    public String toString() {
        return "(cep=" + postalCode + ", Logradouro=" + street + ", Cidade=" + city + ", Estado=" + state + ", Bairro=" + neighborhood + ")";
    }
}
