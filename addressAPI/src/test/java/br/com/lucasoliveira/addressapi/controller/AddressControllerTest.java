package br.com.lucasoliveira.addressapi.controller;

import br.com.lucasoliveira.addressapi.model.PostalCode;
import br.com.lucasoliveira.addressapi.service.CepService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    private static final String POSTAL_CODE = "09080301";
    private static final int WIREMOCK_PORT = 8080;

    @Mock
    private CepService cepService;

    @InjectMocks
    private AddressController addressController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
        WireMock.configureFor(WIREMOCK_PORT);
    }

    @Test
    void whenValidPostalCode_thenReturnsAddress() throws Exception {
        PostalCode mockPostalCode = PostalCode.builder()
                .postalCode("12345-678")
                .street("Rua Exemplo")
                .city("Cidade Exemplo")
                .state("Estado Exemplo")
                .neighborhood("Bairro Exemplo")
                .build();

        when(cepService.findAddress("12345-678")).thenReturn(mockPostalCode);

        mockMvc.perform(get("/12345-678"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(mockPostalCode)));
    }

    @Test
    void testGetEndereco_Success() throws Exception {

        PostalCode mockPostalCode = PostalCode.builder()
                .postalCode("12345-678")
                .street("Rua Exemplo")
                .city("Cidade Exemplo")
                .state("Estado Exemplo")
                .neighborhood("Bairro Exemplo")
                .build();

        when(cepService.findAddress(POSTAL_CODE)).thenReturn(mockPostalCode);



        WireMock.stubFor(WireMock.get(urlEqualTo("/addressAPI/" + POSTAL_CODE))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "    \"Cep\": \"09080-301\",\n" +
                                "    \"Logradouro\": \"Rua das Figueiras\",\n" +
                                "    \"Cidade\": \"Santo André\",\n" +
                                "    \"Estado\": \"SP\",\n" +
                                "    \"Bairro\": \"Campestre\"\n" +
                                "}")));


        // Realizando a requisição para o controller
        mockMvc.perform(get("http://localhost:" + WIREMOCK_PORT + "/addressAPI/" + POSTAL_CODE))
                .andExpect(status().isOk());
    }


}
