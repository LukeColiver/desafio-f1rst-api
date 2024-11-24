package br.com.lucasoliveira.apiendereco.controller;

import br.com.lucasoliveira.apiendereco.exception.PostalCodeNotFoundException;
import br.com.lucasoliveira.apiendereco.model.ErrorResponse;
import br.com.lucasoliveira.apiendereco.model.PostalCode;
import br.com.lucasoliveira.apiendereco.service.CepService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private CepService cepService;

    @InjectMocks
    private AddressController addressController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    }

    @Test
    void whenValidPostalCode_thenReturnsAddress() throws Exception {
        // Prepare the mock response from the CepService
        PostalCode mockPostalCode = PostalCode.builder()
                .postalCode("12345-678")
                .street("Rua Exemplo")
                .city("Cidade Exemplo")
                .state("Estado Exemplo")
                .neighborhood("Bairro Exemplo")
                .build();

        when(cepService.findAddress("12345-678")).thenReturn(mockPostalCode);

        // Perform the request and assert the result
        mockMvc.perform(get("/12345-678"))
                .andExpect(status().isOk())  // Verifica o status 200 OK
                .andExpect(content().json(new ObjectMapper().writeValueAsString(mockPostalCode)));  // Verifica a resposta JSON
    }


}
