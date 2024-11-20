package br.com.lucasoliveira.apiendereco.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoBrasilApiDTO {

    private String cep;
    private String street;
    private String complemento;
    private String neighborhood;
    private String city;
    private String state;

    public CepDTO to(){
        return CepDTO.builder()
                .cep(this.cep)
                .logradouro(this.street)
                .estado(this.state)
                .bairro(this.neighborhood)
                .cidade(this.city)
                .build();
    }
}
