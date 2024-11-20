package br.com.lucasoliveira.apiendereco.client;

import br.com.lucasoliveira.apiendereco.dto.EnderecoBrasilApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "brasilApi", url = "https://brasilapi.com.br/")
@Component
public interface BrasilApiClient {

    @GetMapping("/api/cep/v1/{cep}")
    EnderecoBrasilApiDTO findEndereco(
            @PathVariable("cep") String cep);

}