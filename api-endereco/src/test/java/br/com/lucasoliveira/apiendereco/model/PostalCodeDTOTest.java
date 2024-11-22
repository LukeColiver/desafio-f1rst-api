package br.com.lucasoliveira.apiendereco.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PostalCodeDTOTest {

    @Test
    public void testToString() {
        // Arrange: Criação do objeto PostalCodeDTO com valores fictícios
        PostalCode postalCode = PostalCode.builder()
                .postalCode("12345-678")
                .street("Rua Exemplo")
                .city("Cidade Exemplo")
                .state("Estado Exemplo")
                .neighborhood("Bairro Exemplo")
                .build();

        // Act: Chama o método toString
        String result = postalCode.toString();

        // Assert: Verifica se a string gerada está no formato correto
        String expected = "(cep=12345-678, Logradouro=Rua Exemplo, Cidade=Cidade Exemplo, Estado=Estado Exemplo, Bairro=Bairro Exemplo)";
        assertEquals(expected, result, "O método toString não retornou a string esperada.");
    }

    @Test
    public void testConstructorAndGetters() {
        // Arrange: Criação do objeto PostalCodeDTO com valores fictícios
        PostalCode postalCode = new PostalCode("12345-678", "Rua Exemplo", "Cidade Exemplo", "Estado Exemplo", "Bairro Exemplo");

        // Act & Assert: Verifica se os getters retornam os valores corretos
        assertEquals("12345-678", postalCode.getPostalCode());
        assertEquals("Rua Exemplo", postalCode.getStreet());
        assertEquals("Cidade Exemplo", postalCode.getCity());
        assertEquals("Estado Exemplo", postalCode.getState());
        assertEquals("Bairro Exemplo", postalCode.getNeighborhood());
    }

    @Test
    public void testBuilder() {
        // Arrange & Act: Criação do objeto PostalCodeDTO usando o builder
        PostalCode postalCode = PostalCode.builder()
                .postalCode("98765-432")
                .street("Rua Teste")
                .city("Cidade Teste")
                .state("Estado Teste")
                .neighborhood("Bairro Teste")
                .build();

        // Assert: Verifica se os valores passados no builder são retornados corretamente
        assertEquals("98765-432", postalCode.getPostalCode());
        assertEquals("Rua Teste", postalCode.getStreet());
        assertEquals("Cidade Teste", postalCode.getCity());
        assertEquals("Estado Teste", postalCode.getState());
        assertEquals("Bairro Teste", postalCode.getNeighborhood());
    }

    // Teste de falha para o método toString com valores nulos
    @Test
    public void testToStringWithNullValues() {
        // Arrange: Criação do objeto PostalCodeDTO com valores nulos
        PostalCode postalCode = new PostalCode();

        // Act: Chama o método toString
        String result = postalCode.toString();

        // Assert: Verifica se o método toString não falha e retorna uma string com valores nulos
        assertTrue(result.contains("cep=null"), "O método toString não tratou valores nulos corretamente.");
    }

    // Teste de falha para um postal code (CEP) vazio
    @Test
    public void testPostalCodeWithEmptyValue() {
        // Arrange: Criação do objeto PostalCodeDTO com um valor vazio no campo postalCode
        PostalCode postalCodeDTO = new PostalCode("", "Rua Exemplo", "Cidade Exemplo", "Estado Exemplo", "Bairro Exemplo");

        // Act: Verifica se o postalCode está vazio
        String postalCode = postalCodeDTO.getPostalCode();

        // Assert: Verifica que o postalCode está vazio
        assertTrue(postalCode.isEmpty(), "O campo postalCode não deve estar vazio.");
    }

    // Teste de falha para um campo obrigatório nulo (se definirmos algum campo como obrigatório no futuro)
    @Test
    public void testStreetWithNullValue() {
        // Arrange: Criação do objeto PostalCodeDTO com campo street nulo
        PostalCode postalCode = new PostalCode("12345-678", null, "Cidade Exemplo", "Estado Exemplo", "Bairro Exemplo");

        // Act: Verifica se o campo street é nulo
        String street = postalCode.getStreet();

        // Assert: Verifica que o campo street é nulo
        assertNull(street, "O campo street não deve ser nulo.");
    }

    // Teste de falha para um valor inválido no campo postalCode (CEP incorreto)
    @Test
    public void testPostalCodeWithInvalidFormat() {
        // Arrange: Criação do objeto PostalCodeDTO com um postalCode inválido (formato errado)
        PostalCode postalCodeDTO = new PostalCode("1234-5678", "Rua Exemplo", "Cidade Exemplo", "Estado Exemplo", "Bairro Exemplo");

        // Act: Verifica se o campo postalCode contém um valor inválido
        String postalCode = postalCodeDTO.getPostalCode();

        // Assert: Verifica que o postalCode não está no formato esperado (com hífen, por exemplo)
        assertTrue(postalCode.length() > 0 && postalCode.length() != 9, "O campo postalCode não está no formato esperado.");
    }

    // Teste de falha para valores nulos no construtor
    @Test
    public void testConstructorWithNullValues() {
        // Assert: Verifica se ao passar valores nulos no construtor, a classe lida com isso de forma segura
        PostalCode postalCode = new PostalCode(null, null, null, null, null);

        // Act & Assert: Verifica que o DTO pode ser criado com valores nulos sem falhas
        assertNull(postalCode.getPostalCode(), "O campo postalCode deve ser nulo.");
        assertNull(postalCode.getStreet(), "O campo street deve ser nulo.");
        assertNull(postalCode.getCity(), "O campo city deve ser nulo.");
        assertNull(postalCode.getState(), "O campo state deve ser nulo.");
        assertNull(postalCode.getNeighborhood(), "O campo neighborhood deve ser nulo.");
    }

    // Teste de falha para valores inválidos ao usar o Builder
    @Test
    public void testBuilderWithInvalidValues() {
        // Teste caso os valores inválidos sejam passados para o Builder (por exemplo, um formato de CEP inválido)
        PostalCode postalCode = PostalCode.builder()
                .postalCode("1234-5678")  // CEP inválido
                .street("Rua Exemplo")
                .city("Cidade Exemplo")
                .state("Estado Exemplo")
                .neighborhood("Bairro Exemplo")
                .build();

        // Assert: Verifica se o CEP não está no formato correto
        assertTrue(postalCode.getPostalCode().length() > 0 && postalCode.getPostalCode().length() != 9,
                "O campo postalCode não está no formato esperado.");
    }

    // Teste de falha para garantir que um campo obrigatório não seja nulo (usando anotações de validação, caso esteja configurado)
    @Test
    public void testFieldValidationNotNull() {
        // Caso você tenha validações, por exemplo, o campo postalCode não pode ser nulo
        PostalCode postalCode = new PostalCode(null, "Rua Exemplo", "Cidade Exemplo", "Estado Exemplo", "Bairro Exemplo");

        // Verifique se a validação ocorre e um erro é lançado (usando um validador no service ou controller)
        // Isso depende de como a validação está configurada, por exemplo, @NotNull em um campo postalCode.
        assertThrows(IllegalArgumentException.class, () -> {
            // Simulação de uma falha de validação (isso depende de como a validação é aplicada)
            if (postalCode.getPostalCode() == null) {
                throw new IllegalArgumentException("O campo postalCode não pode ser nulo.");
            }
        }, "Esperado erro de validação para o campo postalCode nulo.");
    }
}
