package br.com.lucasoliveira.apiendereco.dto;

import br.com.lucasoliveira.apiendereco.model.PostalCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressBrasilApiDTO {

    private String cep;
    private String street;
    private String complemento;
    private String neighborhood;
    private String city;
    private String state;

    public PostalCode to(){
        return PostalCode.builder()
                .postalCode(this.cep)
                .street(this.street)
                .state(this.state)
                .neighborhood(this.neighborhood)
                .city(this.city)
                .build();
    }
}
