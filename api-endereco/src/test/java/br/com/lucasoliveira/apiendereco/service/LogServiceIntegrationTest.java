package br.com.lucasoliveira.apiendereco.service;

import br.com.lucasoliveira.apiendereco.entity.LogApi;
import br.com.lucasoliveira.apiendereco.repository.LogRepository;
import br.com.lucasoliveira.apiendereco.service.impl.LogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
public class LogServiceIntegrationTest {

    @Autowired
    private LogServiceImpl logService;

    @Autowired
    private LogRepository logRepository;

    private LogApi logApi;

    @BeforeEach
    public void setup() {
        // Configuração do objeto LogApi para os testes
        logApi = LogApi.builder()
                .id("1")
                .callData("2024-11-21")
                .responseStatus("200 OK")
                .callTimestamp("2024-11-21T15:30:00")
                .build();
    }

    @Test
    public void testSendLog_Integration() {
        // Chama o método de envio de log
        logService.sendLog(logApi);

        // Verifica se o log foi realmente salvo no banco de dados
        List<LogApi> logs = logRepository.findByResponseStatus("200 OK");
        assertFalse(logs.isEmpty());
        assertEquals("200 OK", logs.get(0).getResponseStatus());
    }

    @Test
    public void testGetLogsByCallData_Integration() {
        // Salva o log antes de buscar
        logService.sendLog(logApi);

        // Testa a busca por data
        List<LogApi> logs = logService.getLogsByCallData("2024-11-21");
        assertFalse(logs.isEmpty());
        assertEquals("2024-11-21", logs.get(0).getCallData());
    }
}
