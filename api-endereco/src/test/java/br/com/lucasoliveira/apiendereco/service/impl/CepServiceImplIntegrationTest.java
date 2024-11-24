package br.com.lucasoliveira.apiendereco.service.impl;

import br.com.lucasoliveira.apiendereco.client.BrasilApiClient;
import br.com.lucasoliveira.apiendereco.client.ViaCepClient;
import br.com.lucasoliveira.apiendereco.dto.AddressBrasilApiDTO;
import br.com.lucasoliveira.apiendereco.dto.AddressViaCepDTO;
import br.com.lucasoliveira.apiendereco.exception.PostalCodeNotFoundException;
import br.com.lucasoliveira.apiendereco.model.PostalCode;
import br.com.lucasoliveira.apiendereco.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CepServiceImplIntegrationTest {

    @Autowired
    private CepServiceImpl cepServiceImpl;
    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private BrasilApiClient brasilApiClient;

    @Mock
    private LogService logService;

    private PostalCode validPostalCode;

    @BeforeEach
    void setUp() {
        validPostalCode = PostalCode.builder()
                .postalCode("12345-678")
                .street("Rua Fallback")
                .city("Cidade Fallback")
                .state("Estado Fallback")
                .neighborhood("Bairro Fallback")
                .build();
    }

    @Test
    void testFindAddressSuccess() throws PostalCodeNotFoundException {
        AddressViaCepDTO addressViaCepDTO = AddressViaCepDTO.builder()
                .cep("12345-678")
                .logradouro("Rua Teste")
                .bairro("Bairro Teste")
                .localidade("Cidade Teste")
                .uf("Estado Teste")
                .build();
        when(viaCepClient.findAddress("12345-678")).thenReturn(addressViaCepDTO);

        PostalCode result = cepServiceImpl.findAddress("12345-678");

        assertNotNull(result);
        assertEquals("12345-678", result.getPostalCode());
        assertEquals("Rua Fallback", result.getStreet());
        assertEquals("Cidade Fallback", result.getCity());
        assertEquals("Estado Fallback", result.getState());
        assertEquals("Bairro Fallback", result.getNeighborhood());

        verify(logService).sendLog(result.toString(), "200");
    }

    @Test
    void testFindAddressFallback() throws PostalCodeNotFoundException {
        AddressBrasilApiDTO addressBrasilApiDTO = AddressBrasilApiDTO.builder()
                .cep("12345-678")
                .street("Rua Fallback")
                .neighborhood("Bairro Fallback")
                .city("Cidade Fallback")
                .state("Estado Fallback")
                .build();

        when(viaCepClient.findAddress("12345-678")).thenThrow(new RuntimeException("Service Unavailable"));
        when(brasilApiClient.findAddress("12345-678")).thenReturn(addressBrasilApiDTO);

        PostalCode result = cepServiceImpl.findAddress("12345-678");

        assertNotNull(result);
        assertEquals("12345-678", result.getPostalCode());
        assertEquals("Rua Fallback", result.getStreet());
        assertEquals("Cidade Fallback", result.getCity());
        assertEquals("Estado Fallback", result.getState());
        assertEquals("Bairro Fallback", result.getNeighborhood());

        verify(logService).sendLog(result.toString(), "200");
    }

    @Test
    void testFindAddressNotFound() {
        when(viaCepClient.findAddress("12345-678")).thenReturn(null);
        when(brasilApiClient.findAddress("12345-678")).thenReturn(null);

        PostalCodeNotFoundException thrown = assertThrows(PostalCodeNotFoundException.class, () -> {
            cepServiceImpl.findAddress("12345-678");
        });

        assertEquals("Postal code 12345-678 not found.", thrown.getMessage());
    }
}
