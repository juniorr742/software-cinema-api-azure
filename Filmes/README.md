# 🎬 Software Cinema API - Azure

Esta é uma API robusta para gerenciamento de cinemas, desenvolvida com **Java** e **Spring Boot**. O sistema permite desde o controle administrativo (salas, filmes e sessões) até a experiência do cliente final (escolha de assentos e reservas em tempo real).

## 🚀 Funcionalidades Principais

- **Autenticação Segura:** Login e Cadastro de usuários com diferentes níveis de acesso (Admin/Cliente) utilizando **JWT (JSON Web Token)**.
- **Gestão de Catálogo:** Cadastro, edição e remoção de filmes com detalhes como gênero, duração e poster.
- **Infraestrutura de Salas:** 
  - Criação de salas com geração automática de mapa de assentos (A1, A2, B1...).
  - Suporte a múltiplas fileiras e colunas.
- **Grade de Programação (Sessões):** 
  - Controle inteligente de horários para evitar conflitos (duas sessões na mesma sala ao mesmo tempo).
  - Gestão de preços diferenciados por sessão.
- **Sistema de Reservas:**
  - Validação em tempo real de assentos ocupados.
  - Prevenção de "Double Booking" (venda duplicada do mesmo lugar).
  - Mapa de disponibilidade de assentos por sessão.
  - Cancelamento de reservas com liberação automática de assentos.

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3
- **Segurança:** Spring Security + JWT
- **Persistência:** Spring Data JPA (Hibernate)
- **Banco de Dados:** MySQL/PostgreSQL (configurável via profiles)
- **Documentação:** Swagger/OpenAPI (em desenvolvimento)
- **Gestão de Dependências:** Maven

## 📂 Estrutura do Projeto

O projeto segue a arquitetura **MVC (Model-View-Controller)** adaptada para APIs REST:
- `Model`: Entidades do banco de dados e Enums.
- `DTO (Request/Response)`: Camada de transferência de dados para segurança e performance.
- `Repository`: Interfaces de comunicação com o banco de dados (Spring Data JPA).
- `Service`: Camada de lógica de negócio (onde a "mágica" acontece).
- `Controller`: Endpoints da API.
- `Security`: Configurações de filtros, utilitários JWT e regras de acesso.

## ⚙️ Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/software-cinema-api-azure.git
   ```
2. Configure o banco de dados no arquivo `src/main/resources/application.properties`.
3. Execute o projeto via Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

## 📝 Licença
Este projeto foi desenvolvido para fins educacionais e de portfólio.
