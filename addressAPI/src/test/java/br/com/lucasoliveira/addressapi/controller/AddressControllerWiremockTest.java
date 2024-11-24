package br.com.lucasoliveira.addressapi.controller;

import br.com.lucasoliveira.addressapi.exception.PostalCodeNotFoundException;
import br.com.lucasoliveira.addressapi.model.PostalCode;
import br.com.lucasoliveira.addressapi.service.CepService;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AddressControllerWiremockTest {

    private static final String POSTAL_CODE = "09080301";
    private static final int WIREMOCK_PORT = 9000; // Porta definida como 9000

    @InjectMocks
    private AddressController addressController;

    @Mock
    private CepService cepService;

    private MockMvc mockMvc;

    // Configuração do WireMockExtension com a porta 9000
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();

        // Configuração para WireMock na porta 9000
        WireMock.configureFor(WIREMOCK_PORT); // Define a porta 9000 para o WireMock
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
        // Simula o comportamento do serviço para retornar a resposta do WireMock
        when(cepService.findAddress(POSTAL_CODE)).thenReturn(mockPostalCode);

        // Configuração do WireMock para simular resposta da API externa
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

        mockMvc.perform(get("http://localhost:" + WIREMOCK_PORT + "/addressAPI/" + POSTAL_CODE))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEndereco_NotFound() throws Exception {
        // Simula o comportamento de um CEP não encontrado
        when(cepService.findAddress("invalidPostalCode")).thenThrow(new PostalCodeNotFoundException("CEP não encontrado"));

        mockMvc.perform(get("http://localhost:" + WIREMOCK_PORT + "/addressAPI/invalidPostalCode"))
                .andExpect(status().isNotFound());
    }
}
