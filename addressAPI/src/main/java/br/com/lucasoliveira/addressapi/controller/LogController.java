package br.com.lucasoliveira.addressapi.controller;

import br.com.lucasoliveira.addressapi.entity.LogApi;
import br.com.lucasoliveira.addressapi.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @Operation(
            summary = "Obtém todos os logs da API",
            description = "Retorna uma lista de todos os registros de log armazenados no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de logs retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao obter os logs")
    })
    @GetMapping
    public ResponseEntity<List<LogApi>> getAllLogs() {
        List<LogApi> logs = logService.getAllLogs();
        return ResponseEntity.ok(logs);
    }


    @Operation(
            summary = "Filtra logs pelo status de resposta",
            description = "Retorna uma lista de logs filtrados com base no status de resposta especificado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de logs filtrada retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetro de status de resposta inválido"),
            @ApiResponse(responseCode = "500", description = "Erro ao filtrar os logs")
    })
    @GetMapping("/by-status")
    public ResponseEntity<List<LogApi>> getLogsByResponseStatus(@RequestParam String responseStatus) {
        List<LogApi> logs = logService.getLogsByResponseStatus(responseStatus);
        return ResponseEntity.ok(logs);
    }


}
