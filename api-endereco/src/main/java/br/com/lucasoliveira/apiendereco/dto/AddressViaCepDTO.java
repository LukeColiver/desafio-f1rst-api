package br.com.lucasoliveira.apiendereco.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressViaCepDTO {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;

    public PostalCodeDTO to(){
        return PostalCodeDTO.builder()
                .postalCode(this.cep)
                .street(this.logradouro)
                .state(this.uf)
                .neighborhood(this.bairro)
                .city(this.localidade)
                .build();
    }
}