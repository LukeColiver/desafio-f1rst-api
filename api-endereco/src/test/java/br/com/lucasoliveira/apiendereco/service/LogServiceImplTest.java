package br.com.lucasoliveira.apiendereco.service;

import br.com.lucasoliveira.apiendereco.entity.LogApi;
import br.com.lucasoliveira.apiendereco.repository.LogRepository;
import br.com.lucasoliveira.apiendereco.service.impl.LogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogServiceImplTest {

    @Mock
    private LogRepository logRepository; // Mock do LogRepository

    @InjectMocks
    private LogServiceImpl logService; // A classe que estamos testando

    private LogApi logApi;

    @BeforeEach
    public void setup() {
        // Configuração do objeto LogApi que será usado nos testes
        logApi = LogApi.builder()
                .id("1")
                .callData("2024-11-21")
                .responseStatus("200 OK")
                .callTimestamp("2024-11-21T15:30:00")
                .build();
    }

    @Test
    public void testSendLog_Success() {
        // Configuração do comportamento esperado do mock (não é necessário retorno)
        doNothing().when(logRepository).save(logApi);

        // Chama o método sendLog
        logService.sendLog(logApi);

        // Verifica se o método save foi chamado uma vez
        verify(logRepository, times(1)).save(logApi);
    }

    @Test
    public void testGetAllLogs_Success() {
        // Configuração do comportamento do mock
        List<LogApi> logs = Collections.singletonList(logApi);
        when(logRepository.findAll()).thenReturn(logs);

        // Chama o método getAllLogs
        List<LogApi> result = logService.getAllLogs();

        // Verifica se o retorno está correto
        assertEquals(1, result.size());
        assertEquals(logApi, result.get(0));
    }

    @Test
    public void testGetLogsByCallData_Success() {
        // Configura o mock
        List<LogApi> logs = Collections.singletonList(logApi);
        when(logRepository.findByCallData("2024-11-21")).thenReturn(logs);

        // Chama o método getLogsByCallData
        List<LogApi> result = logService.getLogsByCallData("2024-11-21");

        // Verifica se o retorno está correto
        assertEquals(1, result.size());
        assertEquals(logApi, result.get(0));
    }

    @Test
    public void testGetLogsByResponseStatus_Success() {
        // Configura o mock
        List<LogApi> logs = Collections.singletonList(logApi);
        when(logRepository.findByResponseStatus("200 OK")).thenReturn(logs);

        // Chama o método getLogsByResponseStatus
        List<LogApi> result = logService.getLogsByResponseStatus("200 OK");

        // Verifica se o retorno está correto
        assertEquals(1, result.size());
        assertEquals(logApi, result.get(0));
    }

    @Test
    public void testGetLogsByTimestampRange_Success() {
        // Configura o mock
        List<LogApi> logs = Collections.singletonList(logApi);
        when(logRepository.findByCallTimestampBetween("2024-11-21T00:00:00", "2024-11-21T23:59:59")).thenReturn(logs);

        // Chama o método getLogsByTimestampRange
        List<LogApi> result = logService.getLogsByTimestampRange("2024-11-21T00:00:00", "2024-11-21T23:59:59");

        // Verifica se o retorno está correto
        assertEquals(1, result.size());
        assertEquals(logApi, result.get(0));
    }

    @Test
    public void testSendLog_Failure() {
        // Configuração para simular erro ao salvar no banco de dados
        doThrow(new RuntimeException("Erro ao salvar log")).when(logRepository).save(logApi);

        // Chama o método sendLog e espera uma exceção
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            logService.sendLog(logApi);
        });

        // Verifica se a exceção foi a esperada
        assertEquals("Erro ao salvar log", exception.getMessage());
    }
}
