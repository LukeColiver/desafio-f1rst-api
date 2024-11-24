package br.com.lucasoliveira.apiendereco.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import br.com.lucasoliveira.apiendereco.entity.LogApi;
import br.com.lucasoliveira.apiendereco.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private LogService logService;

    @InjectMocks
    private LogController logController;

    private List<LogApi> logs;

    @BeforeEach
    public void setup() {
        logs = Arrays.asList(
                LogApi.builder().id("1").callData("data1").responseStatus("200").callTimestamp("2024-11-22T10:00:00").build(),
                LogApi.builder().id("2").callData("data2").responseStatus("404").callTimestamp("2024-11-22T10:05:00").build()
        );
    }

    @Test
    public void testGetAllLogs() throws Exception {
        when(logService.getAllLogs()).thenReturn(logs);

        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].responseStatus").value("404"));
    }

    @Test
    public void testGetAllLogsNoContent() throws Exception {
        when(logService.getAllLogs()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetLogsByResponseStatus() throws Exception {
        String responseStatus = "200";
        List<LogApi> filteredLogs = Arrays.asList(
                LogApi.builder().id("1").callData("data1").responseStatus("200").callTimestamp("2024-11-22T10:00:00").build()
        );

        when(logService.getLogsByResponseStatus(responseStatus)).thenReturn(filteredLogs);

        mockMvc.perform(get("/api/logs/by-status")
                        .param("responseStatus", responseStatus))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].responseStatus").value("200"));
    }

    @Test
    public void testGetLogsByResponseStatusNoContent() throws Exception {
        String responseStatus = "404";

        when(logService.getLogsByResponseStatus(responseStatus)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/logs/by-status")
                        .param("responseStatus", responseStatus))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetLogsByResponseStatusMissingParam() throws Exception {
        mockMvc.perform(get("/api/logs/by-status"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllLogsServiceError() throws Exception {
        when(logService.getAllLogs()).thenThrow(new RuntimeException("Erro no serviço"));

        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetLogsSuccess() throws Exception {
        when(logService.getAllLogs()).thenReturn(logs);

        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"));
    }
}
