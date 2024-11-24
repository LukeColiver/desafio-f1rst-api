package br.com.lucasoliveira.apiendereco.controller;

import br.com.lucasoliveira.apiendereco.entity.LogApi;
import br.com.lucasoliveira.apiendereco.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    // Endpoint para buscar todos os logs
    @GetMapping
    public ResponseEntity<List<LogApi>> getAllLogs() {
        List<LogApi> logs = logService.getAllLogs();
        return ResponseEntity.ok(logs);
    }


    // Endpoint para buscar logs por status
    @GetMapping("/by-status")
    public ResponseEntity<List<LogApi>> getLogsByResponseStatus(@RequestParam String responseStatus) {
        List<LogApi> logs = logService.getLogsByResponseStatus(responseStatus);
        return ResponseEntity.ok(logs);
    }


}
