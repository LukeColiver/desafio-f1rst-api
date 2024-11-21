# API Endereço

Este é um projeto de API para buscar endereços a partir de um CEP (Código de Endereçamento Postal). Utilizando o Spring Boot 3.x, o projeto foi desenvolvido para fornecer uma solução simples e eficiente para consultar e manipular endereços de forma automatizada.

## Descrição

A aplicação `api-endereco` é um serviço RESTful que permite consultar endereços utilizando o número do CEP. Além disso, ela integra funcionalidades de resiliência e Feign para chamadas externas, e fornece métricas de monitoramento através do Spring Boot Actuator.

## Tecnologias

- **Spring Boot 3.x**: Framework principal para a construção da API.
- **Spring Cloud**: Para implementar funcionalidades como Feign Client e Circuit Breaker com Resilience4j.
- **MongoDB**: Banco de dados NoSQL para persistência de dados.
- **JUnit 5 e Mockito**: Para testes unitários e de integração.
- **Lombok**: Para reduzir a boilerplate code no código fonte.

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

## Como Executar

### 1. Clonar o Repositório

Clone este repositório para sua máquina local:

```bash
git clone https://github.com/seu-usuario/api-endereco.git
