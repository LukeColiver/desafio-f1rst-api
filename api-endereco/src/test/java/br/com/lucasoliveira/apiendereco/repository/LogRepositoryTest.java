package br.com.lucasoliveira.apiendereco.repository;

import br.com.lucasoliveira.apiendereco.entity.LogApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")  // Usar o perfil de teste para configurar o MongoDB de teste, se necessário.
public class LogRepositoryTest {

    @Autowired
    private LogRepository logRepository;

    // Criando dados de teste para as operações
    @BeforeEach
    public void setUp() {
        logRepository.deleteAll();  // Limpa os logs antes de cada teste

        LogApi log1 = LogApi.builder()
                .callData("2024-11-21")
                .responseStatus("200 OK")
                .callTimestamp("2024-11-21T10:00:00Z")
                .build();

        LogApi log2 = LogApi.builder()
                .callData("2024-11-21")
                .responseStatus("500 Internal Server Error")
                .callTimestamp("2024-11-21T11:00:00Z")
                .build();

        LogApi log3 = LogApi.builder()
                .callData("2024-11-20")
                .responseStatus("404 Not Found")
                .callTimestamp("2024-11-20T09:00:00Z")
                .build();

        // Salvando logs no MongoDB para os testes
        logRepository.save(log1);
        logRepository.save(log2);
        logRepository.save(log3);
    }

    @Test
    public void testFindByCallData() {
        // Buscando logs por callData
        List<LogApi> logs = logRepository.findByCallData("2024-11-21");

        // Verificando que os logs encontrados têm o callData correto
        assertThat(logs).hasSize(2);
        assertThat(logs.get(0).getCallData()).isEqualTo("2024-11-21");
        assertThat(logs.get(1).getCallData()).isEqualTo("2024-11-21");
    }

    @Test
    public void testFindByResponseStatus() {
        // Buscando logs por responseStatus
        List<LogApi> logs = logRepository.findByResponseStatus("200 OK");

        // Verificando que o log encontrado tem o responseStatus correto
        assertThat(logs).hasSize(1);
        assertThat(logs.get(0).getResponseStatus()).isEqualTo("200 OK");
    }

    @Test
    public void testFindByCallTimestampBetween() {
        // Buscando logs por intervalo de callTimestamp
        List<LogApi> logs = logRepository.findByCallTimestampBetween("2024-11-21T00:00:00Z", "2024-11-21T12:00:00Z");

        // Verificando que os logs encontrados estão dentro do intervalo
        assertThat(logs).hasSize(2);
        assertThat(logs.get(0).getCallTimestamp()).isBetween("2024-11-21T00:00:00Z", "2024-11-21T12:00:00Z");
        assertThat(logs.get(1).getCallTimestamp()).isBetween("2024-11-21T00:00:00Z", "2024-11-21T12:00:00Z");
    }
}
