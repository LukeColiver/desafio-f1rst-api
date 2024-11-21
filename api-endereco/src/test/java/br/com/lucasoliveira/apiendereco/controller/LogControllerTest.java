package br.com.lucasoliveira.apiendereco.controller;

import br.com.lucasoliveira.apiendereco.entity.LogApi;
import br.com.lucasoliveira.apiendereco.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LogControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LogService logService;  // Mock da LogService

    @InjectMocks
    private LogController logController;  // O controlador que estamos testando

    private List<LogApi> logApiList;

    @BeforeEach
    void setUp() {
        // Inicializa a lista de logs de exemplo
        logApiList = Arrays.asList(
                new LogApi("1", "2024-11-21 - 10:00:00", "Test log data", "200"),
                new LogApi("2", "2024-11-21 - 10:30:00", "Another log data", "404")
        );

        // Configura o MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(logController).build();
    }

    @Test
    void testGetAllLogs() throws Exception {
        // Configura o mock para retornar os logs
        when(logService.getAllLogs()).thenReturn(logApiList);

        // Realiza a requisição GET e verifica o status e conteúdo da resposta
        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].callTimestamp").value("2024-11-21 - 10:00:00"))
                .andExpect(jsonPath("$[1].responseStatus").value("404"));

        // Verifica se o método do serviço foi chamado corretamente
        verify(logService, times(1)).getAllLogs();
    }

    @Test
    void testGetLogsByCallData() throws Exception {
        // Configura o mock para retornar logs filtrados por data
        when(logService.getLogsByCallData("2024-11-21")).thenReturn(logApiList);

        // Realiza a requisição GET com o parâmetro de data
        mockMvc.perform(get("/api/logs/by-date")
                        .param("callData", "2024-11-21"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].callTimestamp").value("2024-11-21 - 10:00:00"));

        // Verifica se o método foi chamado corretamente
        verify(logService, times(1)).getLogsByCallData("2024-11-21");
    }

    @Test
    void testGetLogsByResponseStatus() throws Exception {
        // Configura o mock para retornar logs filtrados por status
        when(logService.getLogsByResponseStatus("200")).thenReturn(logApiList);

        // Realiza a requisição GET com o parâmetro de status
        mockMvc.perform(get("/api/logs/by-status")
                        .param("responseStatus", "200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].responseStatus").value("200"));

        // Verifica se o método foi chamado corretamente
        verify(logService, times(1)).getLogsByResponseStatus("200");
    }

    @Test
    void testGetLogsByTimestampRange() throws Exception {
        // Configura o mock para retornar logs filtrados por intervalo de timestamps
        when(logService.getLogsByTimestampRange("2024-11-21 - 10:00:00", "2024-11-21 - 11:00:00"))
                .thenReturn(logApiList);

        // Realiza a requisição GET com o intervalo de timestamps
        mockMvc.perform(get("/api/logs/by-timestamp")
                        .param("startDate", "2024-11-21 - 10:00:00")
                        .param("endDate", "2024-11-21 - 11:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].callTimestamp").value("2024-11-21 - 10:00:00"));

        // Verifica se o método foi chamado corretamente
        verify(logService, times(1)).getLogsByTimestampRange("2024-11-21 - 10:00:00", "2024-11-21 - 11:00:00");
    }

    @Test
    void testGetAllLogs_NoLogsFound() throws Exception {
        // Configura o mock para retornar uma lista vazia
        when(logService.getAllLogs()).thenReturn(List.of());

        // Realiza a requisição GET para buscar logs
        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        // Verifica se o método do serviço foi chamado corretamente
        verify(logService, times(1)).getAllLogs();
    }
}
