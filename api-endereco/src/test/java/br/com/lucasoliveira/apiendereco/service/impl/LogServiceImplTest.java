package br.com.lucasoliveira.apiendereco.service.impl;

import br.com.lucasoliveira.apiendereco.entity.LogApi;
import br.com.lucasoliveira.apiendereco.repository.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LogServiceImplTest {

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private LogServiceImpl logService;

    private LogApi logApi;

    @BeforeEach
    public void setUp() {
        logApi = new LogApi();
        logApi.setCallTimestamp("22/11/2024 - 15:30:00");
        logApi.setCallData("Test message");
        logApi.setResponseStatus("200");
    }

    @Test
    public void testSendLog() {
        logService.sendLog("Test message", "200");
        verify(logRepository, times(1)).save(any(LogApi.class));
    }

    @Test
    public void testGetAllLogs() {
        List<LogApi> logs = new ArrayList<>();
        logs.add(logApi);
        when(logRepository.findAll()).thenReturn(logs);
        List<LogApi> result = logService.getAllLogs();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test message", result.get(0).getCallData());
    }

    @Test
    public void testGetLogsByCallData() {
        List<LogApi> logs = new ArrayList<>();
        logs.add(logApi);
        when(logRepository.findByCallData("Test message")).thenReturn(logs);
        List<LogApi> result = logService.getLogsByCallData("Test message");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test message", result.get(0).getCallData());
    }

    @Test
    public void testGetLogsByResponseStatus() {
        List<LogApi> logs = new ArrayList<>();
        logs.add(logApi);
        when(logRepository.findByResponseStatus("200")).thenReturn(logs);
        List<LogApi> result = logService.getLogsByResponseStatus("200");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("200", result.get(0).getResponseStatus());
    }

    @Test
    public void testGetLogsByTimestampRange() {
        List<LogApi> logs = new ArrayList<>();
        logs.add(logApi);
        when(logRepository.findByCallTimestampBetween("22/11/2024 - 00:00:00", "22/11/2024 - 23:59:59")).thenReturn(logs);
        List<LogApi> result = logService.getLogsByTimestampRange("22/11/2024 - 00:00:00", "22/11/2024 - 23:59:59");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("22/11/2024 - 15:30:00", result.get(0).getCallTimestamp());
    }



    @Test
    public void testGetAllLogs_Failure_WhenRepositoryReturnsEmptyList() {
        when(logRepository.findAll()).thenReturn(new ArrayList<>());
        List<LogApi> result = logService.getAllLogs();
        assertNotNull(result);
        assertTrue(result.isEmpty(), "A lista de logs deveria estar vazia");
    }





}
