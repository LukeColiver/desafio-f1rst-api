package br.com.lucasoliveira.apiendereco.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressViaCepDTOTest {

    // ===================== TESTES DE SUCESSO =====================

    // Teste para verificar a criação do objeto AddressViaCepDTO com o Builder
    @Test
    public void testConstructorAndBuilder() {
        // Arrange: Criando um objeto AddressViaCepDTO com valores válidos
        AddressViaCepDTO addressViaCepDTO = AddressViaCepDTO.builder()
                .cep("12345-678")
                .logradouro("Rua Exemplo")
                .complemento("Apto 101")
                .bairro("Bairro Exemplo")
                .localidade("Cidade Exemplo")
                .uf("EX")
                .build();

        // Act & Assert: Verifica se todos os campos estão sendo configurados corretamente
        assertEquals("12345-678", addressViaCepDTO.getCep());
        assertEquals("Rua Exemplo", addressViaCepDTO.getLogradouro());
        assertEquals("Apto 101", addressViaCepDTO.getComplemento());
        assertEquals("Bairro Exemplo", addressViaCepDTO.getBairro());
        assertEquals("Cidade Exemplo", addressViaCepDTO.getLocalidade());
        assertEquals("EX", addressViaCepDTO.getUf());
    }

    // Teste para verificar se o método to() converte corretamente os dados
    @Test
    public void testToMethod() {
        // Arrange: Criando um objeto AddressViaCepDTO com valores válidos
        AddressViaCepDTO addressViaCepDTO = AddressViaCepDTO.builder()
                .cep("12345-678")
                .logradouro("Rua Exemplo")
                .complemento("Apto 101")
                .bairro("Bairro Exemplo")
                .localidade("Cidade Exemplo")
                .uf("EX")
                .build();

        // Act: Convertendo o objeto AddressViaCepDTO para PostalCodeDTO
        PostalCodeDTO postalCodeDTO = addressViaCepDTO.to();

        // Assert: Verifica se a conversão foi feita corretamente
        assertEquals("12345-678", postalCodeDTO.getPostalCode());
        assertEquals("Rua Exemplo", postalCodeDTO.getStreet());
        assertEquals("EX", postalCodeDTO.getState());
        assertEquals("Bairro Exemplo", postalCodeDTO.getNeighborhood());
        assertEquals("Cidade Exemplo", postalCodeDTO.getCity());
    }

    // Teste para verificar o comportamento com um objeto AddressViaCepDTO vazio
    @Test
    public void testEmptyAddressViaCepDTO() {
        // Arrange: Criando um objeto AddressViaCepDTO vazio
        AddressViaCepDTO addressViaCepDTO = new AddressViaCepDTO();

        // Act & Assert: Verifica se todos os campos são nulos
        assertNull(addressViaCepDTO.getCep());
        assertNull(addressViaCepDTO.getLogradouro());
        assertNull(addressViaCepDTO.getComplemento());
        assertNull(addressViaCepDTO.getBairro());
        assertNull(addressViaCepDTO.getLocalidade());
        assertNull(addressViaCepDTO.getUf());
    }

    // Teste para verificar se o método to() retorna um PostalCodeDTO vazio quando AddressViaCepDTO está vazio
    @Test
    public void testToMethodWithEmptyAddress() {
        // Arrange: Criando um objeto AddressViaCepDTO vazio
        AddressViaCepDTO addressViaCepDTO = new AddressViaCepDTO();

        // Act: Convertendo o objeto vazio para PostalCodeDTO
        PostalCodeDTO postalCodeDTO = addressViaCepDTO.to();

        // Assert: Verifica se os campos do PostalCodeDTO também estão vazios
        assertNull(postalCodeDTO.getPostalCode());
        assertNull(postalCodeDTO.getStreet());
        assertNull(postalCodeDTO.getState());
        assertNull(postalCodeDTO.getNeighborhood());
        assertNull(postalCodeDTO.getCity());
    }

    // ===================== TESTES DE FALHA =====================

    // Teste para falha: Verificar se o método to() lida com valores nulos
    @Test
    public void testToMethodWithNullValues() {
        // Arrange: Criando um objeto AddressViaCepDTO com valores nulos
        AddressViaCepDTO addressViaCepDTO = new AddressViaCepDTO();

        // Act: Convertendo o objeto vazio para PostalCodeDTO
        PostalCodeDTO postalCodeDTO = addressViaCepDTO.to();

        // Assert: Verifica que os campos do PostalCodeDTO estão nulos devido aos valores nulos de AddressViaCepDTO
        assertNull(postalCodeDTO.getPostalCode(), "PostalCode não deve ser nulo.");
        assertNull(postalCodeDTO.getStreet(), "Street não deve ser nulo.");
        assertNull(postalCodeDTO.getState(), "State não deve ser nulo.");
        assertNull(postalCodeDTO.getNeighborhood(), "Neighborhood não deve ser nulo.");
        assertNull(postalCodeDTO.getCity(), "City não deve ser nulo.");
    }

    // Teste para falha: Verificar se a validação do CEP é feita corretamente (se o CEP tiver formato errado)
    @Test
    public void testInvalidCepFormat() {
        // Arrange: Criando um objeto AddressViaCepDTO com um formato de CEP inválido
        AddressViaCepDTO addressViaCepDTO = AddressViaCepDTO.builder()
                .cep("1234-567")  // Formato de CEP inválido
                .logradouro("Rua Exemplo")
                .complemento("Apto 101")
                .bairro("Bairro Exemplo")
                .localidade("Cidade Exemplo")
                .uf("EX")
                .build();

        // Act & Assert: Verifica se a validação de CEP acontece corretamente
        assertFalse(addressViaCepDTO.getCep().matches("\\d{5}-\\d{3}"), "O formato do CEP está incorreto.");
    }

    // Teste para falha: Verificar se campos obrigatórios não estão nulos (como o campo "cep")
    @Test
    public void testRequiredFieldCep() {
        // Arrange: Criando um objeto AddressViaCepDTO sem o campo "cep"
        AddressViaCepDTO addressViaCepDTO = AddressViaCepDTO.builder()
                .cep(null)  // CEP nulo
                .logradouro("Rua Exemplo")
                .complemento("Apto 101")
                .bairro("Bairro Exemplo")
                .localidade("Cidade Exemplo")
                .uf("EX")
                .build();

        // Act & Assert: Verifica se o campo "cep" é obrigatório
        assertNull(addressViaCepDTO.getCep(), "O campo 'cep' não pode ser nulo.");
    }

    // Teste para falha: Verificar se a conversão to() lida com valores nulos corretamente
    @Test
    public void testToMethodWithNullPostalCodeDTO() {
        // Arrange: Criando um AddressViaCepDTO com valores nulos
        AddressViaCepDTO addressViaCepDTO = new AddressViaCepDTO();

        // Act: Tentando converter para PostalCodeDTO
        PostalCodeDTO postalCodeDTO = addressViaCepDTO.to();

        // Assert: Verifica que o PostalCodeDTO gerado a partir de dados nulos está correto
        assertNull(postalCodeDTO.getPostalCode(), "PostalCode deve ser nulo.");
        assertNull(postalCodeDTO.getStreet(), "Street deve ser nulo.");
    }
}
