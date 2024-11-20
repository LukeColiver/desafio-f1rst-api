package br.com.lucasoliveira.apiendereco.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoViaCepDTO {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;

    public CepDTO to(){
        return CepDTO.builder()
                .cep(this.cep)
                .logradouro(this.logradouro)
                .estado(this.uf)
                .bairro(this.bairro)
                .cidade(this.localidade)
                .build();
    }
}