package br.com.lucasoliveira.addressapi.client;

import br.com.lucasoliveira.addressapi.dto.AddressBrasilApiDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "brasilApi", url = "${feign.client.config.brasilApi.url}")
@Component
public interface BrasilApiClient {

    @GetMapping("/api/cep/v1/{cep}")
    AddressBrasilApiDTO findAddress(
            @PathVariable("cep") String cep);

}