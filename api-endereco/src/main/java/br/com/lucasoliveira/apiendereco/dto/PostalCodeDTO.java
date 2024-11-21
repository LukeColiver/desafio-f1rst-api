package br.com.lucasoliveira.apiendereco.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostalCodeDTO {

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
        return "CepDTO(cep=" + postalCode + ", Logradouro=" + street + ", Cidade=" + city + ", Estado=" + state + ", Bairro=" + neighborhood + ")";
    }
}
