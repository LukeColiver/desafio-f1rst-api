package br.com.lucasoliveira.addressapi.service.impl;

import br.com.lucasoliveira.addressapi.client.BrasilApiClient;
import br.com.lucasoliveira.addressapi.client.ViaCepClient;
import br.com.lucasoliveira.addressapi.dto.AddressBrasilApiDTO;
import br.com.lucasoliveira.addressapi.dto.AddressViaCepDTO;
import br.com.lucasoliveira.addressapi.model.PostalCode;
import br.com.lucasoliveira.addressapi.exception.PostalCodeNotFoundException;
import br.com.lucasoliveira.addressapi.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CepServiceImplTest {

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private BrasilApiClient brasilApiClient;

    @Mock
    private LogService logService;

    @InjectMocks
    private CepServiceImpl cepService;

    private PostalCode postalCode;

    @BeforeEach
    public void setUp() {
        postalCode = PostalCode.builder()
                .postalCode("12345-678")
                .street("Rua Teste")
                .city("Cidade Teste")
                .state("Estado Teste")
                .neighborhood("Bairro Teste")
                .build();
    }

    AddressBrasilApiDTO addressBrasilApiDTO = AddressBrasilApiDTO.builder()
            .cep("12345-678")
            .street("Rua Fallback")
            .city("Cidade Fallback")
            .state("Estado Fallback")
            .neighborhood("Bairro Fallback")
            .build();

    @Test
    public void testFindAddressSuccessViaCep() throws PostalCodeNotFoundException {
        AddressViaCepDTO addressViaCepDTO = AddressViaCepDTO.builder()
                .cep("12345-678")
                .logradouro("Rua Teste")
                .bairro("Bairro Teste")
                .localidade("Cidade Teste")
                .uf("Estado Teste")
                .build();

        when(viaCepClient.findAddress("12345-678")).thenReturn(addressViaCepDTO);

        PostalCode result = cepService.findAddress("12345-678");

        assertNotNull(result);
        assertEquals("12345-678", result.getPostalCode());
        assertEquals("Rua Teste", result.getStreet());
        assertEquals("Bairro Teste", result.getNeighborhood());
        assertEquals("Cidade Teste", result.getCity());
        assertEquals("Estado Teste", result.getState());

        verify(logService).sendLog(result.toString(), "200");
    }

    @Test
    public void testFindAddressSuccess() throws PostalCodeNotFoundException {
        AddressViaCepDTO addressViaCepDTO = AddressViaCepDTO.builder()
                .cep("12345-678")
                .logradouro("Rua Teste")
                .bairro("Bairro Teste")
                .localidade("Cidade Teste")
                .uf("Estado Teste")
                .build();

        when(viaCepClient.findAddress("12345-678")).thenReturn(addressViaCepDTO);

        PostalCode result = cepService.findAddress("12345-678");

        assertNotNull(result);
        assertEquals("12345-678", result.getPostalCode());
        assertEquals("Rua Teste", result.getStreet());
        assertEquals("Cidade Teste", result.getCity());
        assertEquals("Estado Teste", result.getState());
        assertEquals("Bairro Teste", result.getNeighborhood());

        verify(logService).sendLog(result.toString(), "200");
    }

    @Test
    public void testFindAddressViaFallback() throws PostalCodeNotFoundException {
        lenient().when(viaCepClient.findAddress("12345-678")).thenThrow(new RuntimeException("Service Unavailable"));

        AddressBrasilApiDTO addressBrasilApiDTO = AddressBrasilApiDTO.builder()
                .cep("12345-678")
                .street("Rua Fallback")
                .neighborhood("Bairro Fallback")
                .city("Cidade Fallback")
                .state("Estado Fallback")
                .build();

        lenient().when(brasilApiClient.findAddress("12345-678")).thenReturn(addressBrasilApiDTO);

        PostalCode result = cepService.fallbackMethod("12345-678", new RuntimeException("Service Unavailable"));

        assertNotNull(result);
        assertEquals("12345-678", result.getPostalCode());
        assertEquals("Rua Fallback", result.getStreet());
        assertEquals("Cidade Fallback", result.getCity());
        assertEquals("Estado Fallback", result.getState());
        assertEquals("Bairro Fallback", result.getNeighborhood());

        verify(logService).sendLog(result.toString(), "200");
    }

    @Test
    public void testFallbackMethod() throws PostalCodeNotFoundException {
        PostalCode fallbackPostalCode = PostalCode.builder()
                .postalCode("12345-678")
                .street("Rua Fallback")
                .city("Cidade Fallback")
                .state("Estado Fallback")
                .neighborhood("Bairro Fallback")
                .build();

        lenient().when(viaCepClient.findAddress("12345-678")).thenThrow(new RuntimeException("Service Unavailable"));

        when(brasilApiClient.findAddress("12345-678")).thenReturn(AddressBrasilApiDTO.builder()
                .cep("12345-678")
                .street("Rua Fallback")
                .neighborhood("Bairro Fallback")
                .city("Cidade Fallback")
                .state("Estado Fallback")
                .build());

        PostalCode result = cepService.fallbackMethod("12345-678", new RuntimeException("Service Unavailable"));

        assertNotNull(result);
        assertEquals("12345-678", result.getPostalCode());
        assertEquals("Rua Fallback", result.getStreet());
        assertEquals("Cidade Fallback", result.getCity());
        assertEquals("Estado Fallback", result.getState());
        assertEquals("Bairro Fallback", result.getNeighborhood());

        verify(logService).sendLog(result.toString(), "200");
    }

    @Test
    public void testCreateLog() {
        cepService.createLog(postalCode);

        verify(logService).sendLog(postalCode.toString(), "200");
    }
}
