package br.com.lucasoliveira.apiendereco.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CepDTO {

    private String cep;
    private String logradouro;
    private String cidade;
    private String estado;
    private String bairro;

    @Override
    public String toString() {
        return "CepDTO(cep=" + cep + ", Logradouro=" + logradouro + ", Cidade=" + cidade + ", Dstado=" + estado + ", Bairro=" + bairro + ")";
    }
}
