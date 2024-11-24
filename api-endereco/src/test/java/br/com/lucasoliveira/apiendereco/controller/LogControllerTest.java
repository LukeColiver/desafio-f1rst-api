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

    // Teste para o endpoint GET /api/logs
    @Test
    public void testGetAllLogs() throws Exception {
        // Mockando o retorno do serviço
        when(logService.getAllLogs()).thenReturn(logs);

        // Simulando a requisição GET e verificando o retorno
        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isOk()) // Verifica se o status é 200 OK
                .andExpect(jsonPath("$[0].id").value("1")) // Verifica o id do primeiro log
                .andExpect(jsonPath("$[1].responseStatus").value("404")); // Verifica o status da resposta do segundo log
    }

    // Teste para o endpoint GET /api/logs quando não houver logs
    @Test
    public void testGetAllLogsNoContent() throws Exception {
        // Mockando o retorno vazio do serviço
        when(logService.getAllLogs()).thenReturn(Arrays.asList());

        // Simulando a requisição GET e verificando se a resposta está vazia
        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isOk()) // Verifica se o status é 200 OK
                .andExpect(jsonPath("$").isEmpty()); // Verifica se a resposta está vazia
    }

    // Teste para o endpoint GET /api/logs/by-status
    @Test
    public void testGetLogsByResponseStatus() throws Exception {
        String responseStatus = "200";
        // Mockando o retorno filtrado por status
        List<LogApi> filteredLogs = Arrays.asList(
                LogApi.builder().id("1").callData("data1").responseStatus("200").callTimestamp("2024-11-22T10:00:00").build()
        );

        when(logService.getLogsByResponseStatus(responseStatus)).thenReturn(filteredLogs);

        // Simulando a requisição GET com o parâmetro responseStatus e verificando a resposta
        mockMvc.perform(get("/api/logs/by-status")
                        .param("responseStatus", responseStatus))
                .andExpect(status().isOk()) // Verifica se o status é 200 OK
                .andExpect(jsonPath("$[0].responseStatus").value("200")); // Verifica o status da resposta
    }

    // Teste para o endpoint GET /api/logs/by-status quando não houver logs para o status informado
    @Test
    public void testGetLogsByResponseStatusNoContent() throws Exception {
        String responseStatus = "404";

        // Mockando o retorno vazio para o status 404
        when(logService.getLogsByResponseStatus(responseStatus)).thenReturn(Arrays.asList());

        // Simulando a requisição GET com o parâmetro responseStatus e verificando se a resposta está vazia
        mockMvc.perform(get("/api/logs/by-status")
                        .param("responseStatus", responseStatus))
                .andExpect(status().isOk()) // Verifica se o status é 200 OK
                .andExpect(jsonPath("$").isEmpty()); // Verifica se a resposta está vazia
    }

    // Teste para o endpoint GET /api/logs/by-status sem o parâmetro responseStatus
    @Test
    public void testGetLogsByResponseStatusMissingParam() throws Exception {
        // Simulando a requisição GET sem o parâmetro obrigatório responseStatus
        mockMvc.perform(get("/api/logs/by-status"))
                .andExpect(status().isBadRequest()); // Verifica se o status da resposta é 400 Bad Request
    }

    // Teste para o comportamento do serviço com erro
    @Test
    public void testGetAllLogsServiceError() throws Exception {
        // Simulando um erro no serviço
        when(logService.getAllLogs()).thenThrow(new RuntimeException("Erro no serviço"));

        // Simulando a requisição GET e verificando se o status de erro 500 é retornado
        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isInternalServerError()); // Verifica se o status é 500 Internal Server Error
    }

    // Teste para o endpoint GET /api/logs com status de sucesso
    @Test
    public void testGetLogsSuccess() throws Exception {
        // Mockando o retorno do serviço
        when(logService.getAllLogs()).thenReturn(logs);

        // Simulando a requisição GET e verificando se o status é 200 OK
        mockMvc.perform(get("/api/logs"))
                .andExpect(status().isOk()) // Verifica se o status é 200 OK
                .andExpect(jsonPath("$[0].id").value("1")); // Verifica se o id do primeiro log é correto
    }
}
