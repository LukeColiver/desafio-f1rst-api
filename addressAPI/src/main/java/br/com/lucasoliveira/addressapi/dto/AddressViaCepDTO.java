package br.com.lucasoliveira.addressapi.dto;

import br.com.lucasoliveira.addressapi.model.PostalCode;
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

    public PostalCode to(){
        return PostalCode.builder()
                .postalCode(this.cep)
                .street(this.logradouro)
                .state(this.uf)
                .neighborhood(this.bairro)
                .city(this.localidade)
                .build();
    }
}