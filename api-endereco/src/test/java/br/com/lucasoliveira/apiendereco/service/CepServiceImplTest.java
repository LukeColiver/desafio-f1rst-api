package br.com.lucasoliveira.apiendereco.service;

import br.com.lucasoliveira.apiendereco.client.BrasilApiClient;
import br.com.lucasoliveira.apiendereco.client.ViaCepClient;
import br.com.lucasoliveira.apiendereco.dto.PostalCodeDTO;
import br.com.lucasoliveira.apiendereco.entity.LogApi;
import br.com.lucasoliveira.apiendereco.service.impl.CepServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CepServiceImplTest {

    @Mock
    private ViaCepClient viaCepClient;  // Mock do ViaCepClient

    @Mock
    private BrasilApiClient brasilApiClient;  // Mock do BrasilApiClient

    @Mock
    private LogService logService;  // Mock do LogService

    @InjectMocks
    private CepServiceImpl cepService;  // A classe que estamos testando

    private PostalCodeDTO postalCodeDTO;

    @BeforeEach
    void setUp() {
        postalCodeDTO = PostalCodeDTO.builder()
                .postalCode("12345-678")
                .street("Rua Exemplo")
                .city("Cidade Exemplo")
                .state("Estado Exemplo")
                .neighborhood("Bairro Exemplo")
                .build();
    }

/*    @Test
    void testFindAddress_Success() {
        // Configura o comportamento do mock para o cliente ViaCep
        when(viaCepClient.findAddress("12345-678")).thenReturn(postalCodeDTO);

        // Chama o método findAddress
        ResponseEntity<?> response = cepService.findAddress("12345-678");

        // Verifica se a resposta está correta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postalCodeDTO, response.getBody());

        // Verifica se o log foi criado corretamente
        verify(logService, times(1)).sendLog(any(LogApi.class));
    }*/

    @Test
    void testFindAddress_Failure_NotFound() {
        // Configura o mock para o cliente ViaCep
        when(viaCepClient.findAddress("12345-678")).thenReturn(null);

        // Chama o método findAddress
        ResponseEntity<?> response = cepService.findAddress("12345-678");

        // Verifica se o retorno é 404
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("CEP não foi encontrado.", response.getBody());

        // Verifica se o log foi criado corretamente
        verify(logService, times(1)).sendLog(any(LogApi.class));
    }

 /*   @Test
    void testFallBackFindAddress_Success() {
        // Configura o mock do BrasilApiClient para o fallback
        Object ResponseEntity;
        when(brasilApiClient.findAddress("12345-678")).thenReturn(postalCodeDTO);

        // Chama o método de fallback
        ResponseEntity<?> response = cepService.fallBackFindAddress("12345-678", new Exception("Fallback"));

        // Verifica se o retorno é 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postalCodeDTO, response.getBody());

        // Verifica se o log foi criado corretamente
        verify(logService, times(1)).sendLog(any(LogApi.class));
    }*/

    @Test
    void testFallBackFindAddress_Failure() {
        // Configura o mock para o BrasilApiClient retornar null
        when(brasilApiClient.findAddress("12345-678")).thenReturn(null);

        // Chama o método de fallback
        ResponseEntity<?> response = cepService.fallBackFindAddress("12345-678", new Exception("Fallback"));

        // Verifica se o retorno é 404
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("CEP não foi encontrado.", response.getBody());

        // Verifica se o log foi criado corretamente
        verify(logService, times(1)).sendLog(any(LogApi.class));
    }

    @Test
    void testCreateLog_Success() {
        // Chama o método de criação de log
        cepService.createLog(postalCodeDTO);

        // Verifica se o log foi enviado corretamente
        verify(logService, times(1)).sendLog(any(LogApi.class));
    }

    @Test
    void testCreateLog_Failure() {
        // Chama o método de criação de log com exceção
        cepService.createLog(null);

        // Verifica se o log foi enviado corretamente com dados de erro
        verify(logService, times(1)).sendLog(any(LogApi.class));
    }
}
