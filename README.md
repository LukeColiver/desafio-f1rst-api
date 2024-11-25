# addressAPI

Este é um projeto de API para buscar endereços a partir de um CEP (Código de Endereçamento Postal). Utilizando o Spring Boot 3.x, o projeto foi desenvolvido para fornecer uma solução simples e eficiente para consultar e manipular endereços de forma automatizada.

## Descrição

A aplicação `addressAPI` é um serviço RESTful que permite consultar endereços utilizando o número do CEP. Além disso, ela integra funcionalidades de resiliência e Feign para chamadas externas, e fornece métricas de monitoramento através do Spring Boot Actuator.

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação usada no projeto.
- **Spring Boot 3.3.5**: Framework principal utilizado para a construção da aplicação.
- **Spring Cloud**: Utilizado para implementar recursos de microserviços e resiliência, incluindo **Resilience4j** e **OpenFeign**.
- **MongoDB**: Banco de dados NoSQL utilizado para armazenar os dados.
- **WireMock**: Ferramenta de mocking utilizada para simulação de respostas de APIs externas em testes.
- **JUnit 5**: Framework de testes utilizado para testes unitários e de integração.

## Dependências Principais

- `spring-boot-starter-web`: Para construir a API RESTful.
- `spring-boot-starter-data-mongodb`: Para integração com MongoDB.
- `spring-boot-starter-actuator`: Para monitoramento e métricas.
- `spring-cloud-starter-circuitbreaker-resilience4j`: Para implementar o padrão de circuito breaker.
- `spring-cloud-starter-openfeign`: Para comunicação entre microserviços via HTTP.
- `springdoc-openapi-starter-webmvc-ui`: Para geração automática de documentação OpenAPI e Swagger.
- `lombok`: Biblioteca para reduzir o boilerplate de código.

## Funcionalidades

- Consultar endereço a partir do CEP.
- Integração com Feign Client para chamadas externas.
- Monitoramento e métricas com o Spring Boot Actuator.
- Implementação de Resilience4j para Circuit Breaker.
- Testes automatizados com JUnit 5 e Mockito.

## Pré-requisitos

Certifique-se de ter os seguintes programas instalados antes de rodar o projeto:

- **Java 17** ou superior
- **Maven** para gerenciamento de dependências
- **MongoDB** rodando localmente ou configurado para conexão.

# Projeto addressAPI

Este projeto é uma API construída com **Spring Boot** que oferece funcionalidades relacionadas ao gerenciamento de endereços. Ele segue o padrão recomendado para aplicações Spring Boot.

## Estrutura do Projeto

A estrutura do projeto é a seguinte:

### Descrição dos Componentes

- **AddressApplication.java**: Classe principal do Spring Boot, responsável por inicializar a aplicação.
- **AddressController.java**: Controller que gerencia os endpoints da API relacionados aos endereços.
- **CepService.java**: Serviço que contém a lógica de negócios para manipulação dos dados de endereço.
- **PostalCode.java**: Modelo de dados que representa um endereço.
- **application.yml**: Arquivo de configuração da aplicação, onde são definidas propriedades como portas e configurações específicas do Spring Boot.

## Contribuições

Se você deseja contribuir com este projeto, fique à vontade para fazer um **fork** e enviar um **pull request**. Algumas formas de contribuir incluem:

- Corrigir bugs encontrados.
- Melhorar a documentação.
- Adicionar novos recursos ou endpoints.
- Melhorar os testes e a cobertura de testes.

Agradecemos qualquer contribuição para melhorar o projeto!

## Como Executar

### 1. Clonar o Repositório

Clone este repositório para sua máquina local:

```bash
https://github.com/LukeColiver/desafio-f1rst-api.git
```


### 2. Executar Docker Compose 

```bash
docker-compose down
```

### 3. Testar a API

```bash
GET http://localhost:8080/endereco/{cep}

```

#### Executar aplicacao  **Via Maven**

Para rodar o projeto com Maven, navegue até a pasta do projeto e execute o seguinte comando:

```bash
mvn spring-boot:run
```
### Desenho da Aplicação

![addressAPI drawio](https://github.com/user-attachments/assets/2130ddc5-de33-4db2-b469-c44de31ac389)

