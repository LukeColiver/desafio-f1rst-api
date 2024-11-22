package br.com.lucasoliveira.apiendereco.controller;

import br.com.lucasoliveira.apiendereco.model.PostalCode;
import br.com.lucasoliveira.apiendereco.service.CepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AddressControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CepService cepService;  // Mock do CepService

    @InjectMocks
    private AddressController addressController;  // O controlador que estamos testando

    private PostalCode postalCode;

    @BeforeEach
    void setUp() {
        // Inicializa o MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();

        // Cria um DTO de exemplo para os testes
        postalCode = PostalCode.builder()
                .postalCode("12345-678")
                .street("Rua Exemplo")
                .neighborhood("Bairro Exemplo")
                .city("Cidade Exemplo")
                .state("SP")
                .build();
    }

    @Test
    void testGetEndereco_Success() throws Exception {
        // Configura o mock do serviço para retornar um endereço com sucesso
        ResponseEntity responseEntity = new ResponseEntity<>(postalCode, HttpStatus.OK);
        when(cepService.findAddress("12345-678")).thenReturn(responseEntity);

        // Realiza a requisição GET
        mockMvc.perform(get("/api/{cep}", "12345-678"))
                .andExpect(status().isOk())  // Espera o status 200 OK
                .andExpect(jsonPath("$.postalCode").value("12345-678"))
                .andExpect(jsonPath("$.street").value("Rua Exemplo"))
                .andExpect(jsonPath("$.neighborhood").value("Bairro Exemplo"))
                .andExpect(jsonPath("$.city").value("Cidade Exemplo"))
                .andExpect(jsonPath("$.state").value("SP"));

        // Verifica se o serviço foi chamado com o CEP correto
        verify(cepService, times(1)).findAddress("12345-678");
    }
/*
    @Test
    void testGetEndereco_NotFound() throws Exception {
        ResponseEntity responseEntity = new ResponseEntity<>(postalCodeDTO, HttpStatus.OK);

        // Configura o mock do serviço para retornar um erro de não encontrado
        when(cepService.findAddress("12345-678")).thenReturn(responseEntity)
                .body("CEP não encontrado"));

        // Realiza a requisição GET
        mockMvc.perform(get("/api/{cep}", "12345-678"))
                .andExpect(status().isNotFound())  // Espera o status 404 NOT FOUND
                .andExpect(content().string("CEP não encontrado"));

        // Verifica se o serviço foi chamado com o CEP correto
        verify(cepService, times(1)).findAddress("12345-678");
    }*/

/*    @Test
    void testGetEndereco_InternalServerError() throws Exception {
        // Configura o mock do serviço para retornar um erro de servidor
        when(cepService.findAddress("12345-678")).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno no servidor"));

        // Realiza a requisição GET
        mockMvc.perform(get("/api/{cep}", "12345-678"))
                .andExpect(status().isInternalServerError())  // Espera o status 500 INTERNAL SERVER ERROR
                .andExpect(content().string("Erro interno no servidor"));

        // Verifica se o serviço foi chamado com o CEP correto
        verify(cepService, times(1)).findAddress("12345-678");
    }*/
}
