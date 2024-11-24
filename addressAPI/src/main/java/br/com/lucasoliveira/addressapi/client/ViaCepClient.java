package br.com.lucasoliveira.addressapi.client;

import br.com.lucasoliveira.addressapi.dto.AddressViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "${feign.client.config.viacep.url}")
@Component
public interface ViaCepClient {

    @GetMapping("/ws/{cep}/json")
    AddressViaCepDTO findAddress(
            @PathVariable() String cep);

}