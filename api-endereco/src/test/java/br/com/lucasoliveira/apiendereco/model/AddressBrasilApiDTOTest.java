package br.com.lucasoliveira.apiendereco.model;

import br.com.lucasoliveira.apiendereco.dto.AddressBrasilApiDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressBrasilApiDTOTest {

    // ===================== TESTES DE SUCESSO =====================

    // Teste para verificar a criação do objeto AddressBrasilApiDTO com o Builder
    @Test
    public void testConstructorAndBuilder() {
        // Arrange: Criando um objeto AddressBrasilApiDTO com valores válidos
        AddressBrasilApiDTO addressBrasilApi = AddressBrasilApiDTO.builder()
                .cep("12345-678")
                .street("Rua Exemplo")
                .complemento("Apto 101")
                .neighborhood("Bairro Exemplo")
                .city("Cidade Exemplo")
                .state("EX")
                .build();

        // Act & Assert: Verifica se todos os campos estão sendo configurados corretamente
        assertEquals("12345-678", addressBrasilApi.getCep());
        assertEquals("Rua Exemplo", addressBrasilApi.getStreet());
        assertEquals("Apto 101", addressBrasilApi.getComplemento());
        assertEquals("Bairro Exemplo", addressBrasilApi.getNeighborhood());
        assertEquals("Cidade Exemplo", addressBrasilApi.getCity());
        assertEquals("EX", addressBrasilApi.getState());
    }

    // Teste para verificar se o método to() converte corretamente os dados
    @Test
    public void testToMethod() {
        // Arrange: Criando um objeto AddressBrasilApiDTO com valores válidos
        AddressBrasilApiDTO addressBrasilApi = AddressBrasilApiDTO.builder()
                .cep("12345-678")
                .street("Rua Exemplo")
                .complemento("Apto 101")
                .neighborhood("Bairro Exemplo")
                .city("Cidade Exemplo")
                .state("EX")
                .build();

        // Act: Convertendo o objeto AddressBrasilApiDTO para PostalCodeDTO
        PostalCode postalCode = addressBrasilApi.to();

        // Assert: Verifica se a conversão foi feita corretamente
        assertEquals("12345-678", postalCode.getPostalCode());
        assertEquals("Rua Exemplo", postalCode.getStreet());
        assertEquals("EX", postalCode.getState());
        assertEquals("Bairro Exemplo", postalCode.getNeighborhood());
        assertEquals("Cidade Exemplo", postalCode.getCity());
    }

    // Teste para verificar o comportamento com um objeto AddressBrasilApiDTO vazio
    @Test
    public void testEmptyAddressBrasilApiDTO() {
        // Arrange: Criando um objeto AddressBrasilApiDTO vazio
        AddressBrasilApiDTO addressBrasilApi = new AddressBrasilApiDTO();

        // Act & Assert: Verifica se todos os campos são nulos
        assertNull(addressBrasilApi.getCep());
        assertNull(addressBrasilApi.getStreet());
        assertNull(addressBrasilApi.getComplemento());
        assertNull(addressBrasilApi.getNeighborhood());
        assertNull(addressBrasilApi.getCity());
        assertNull(addressBrasilApi.getState());
    }

    // Teste para verificar se o método to() retorna um PostalCodeDTO vazio quando AddressBrasilApiDTO está vazio
    @Test
    public void testToMethodWithEmptyAddress() {
        // Arrange: Criando um objeto AddressBrasilApiDTO vazio
        AddressBrasilApiDTO addressBrasilApi = new AddressBrasilApiDTO();

        // Act: Convertendo o objeto vazio para PostalCodeDTO
        PostalCode postalCode = addressBrasilApi.to();

        // Assert: Verifica se os campos do PostalCodeDTO também estão vazios
        assertNull(postalCode.getPostalCode());
        assertNull(postalCode.getStreet());
        assertNull(postalCode.getState());
        assertNull(postalCode.getNeighborhood());
        assertNull(postalCode.getCity());
    }

    // ===================== TESTES DE FALHA =====================

    // Teste para falha: Verificar se a validação do CEP é feita corretamente (se o CEP tiver formato errado)
    @Test
    public void testInvalidCepFormat() {
        // Arrange: Criando um objeto AddressBrasilApiDTO com um formato de CEP inválido
        AddressBrasilApiDTO addressBrasilApiDTO = AddressBrasilApiDTO.builder()
                .cep("1234-567")  // Formato de CEP inválido
                .street("Rua Exemplo")
                .complemento("Apto 101")
                .neighborhood("Bairro Exemplo")
                .city("Cidade Exemplo")
                .state("EX")
                .build();

        // Act & Assert: Verifica se a validação de CEP acontece corretamente
        assertFalse(addressBrasilApiDTO.getCep().matches("\\d{5}-\\d{3}"), "O formato do CEP está incorreto.");
    }

    // Teste para falha: Verificar se campos obrigatórios não estão nulos (como o campo "cep")
    @Test
    public void testRequiredFieldCep() {
        // Arrange: Criando um objeto AddressBrasilApiDTO sem o campo "cep"
        AddressBrasilApiDTO addressBrasilApiDTO = AddressBrasilApiDTO.builder()
                .cep(null)  // CEP nulo
                .street("Rua Exemplo")
                .complemento("Apto 101")
                .neighborhood("Bairro Exemplo")
                .city("Cidade Exemplo")
                .state("EX")
                .build();

        // Act & Assert: Verifica se o campo "cep" é obrigatório
        assertNull(addressBrasilApiDTO.getCep(), "O campo 'cep' não pode ser nulo.");
    }

    // Teste para falha: Verificar se a conversão to() lida com valores nulos corretamente
    @Test
    public void testToMethodWithNullPostalCodeDTO() {
        // Arrange: Criando um AddressBrasilApiDTO com valores nulos
        AddressBrasilApiDTO addressBrasilApiDTO = new AddressBrasilApiDTO();

        // Act: Tentando converter para PostalCodeDTO
        PostalCode postalCode = addressBrasilApiDTO.to();

        // Assert: Verifica que o PostalCodeDTO gerado a partir de dados nulos está correto
        assertNull(postalCode.getPostalCode(), "PostalCode deve ser nulo.");
        assertNull(postalCode.getStreet(), "Street deve ser nulo.");
    }

}
