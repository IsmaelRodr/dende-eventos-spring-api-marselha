# 🌴 Dendê Eventos API

## 📌 Sobre a Dendê Softhouse

A **Dendê Softhouse** é uma empresa baiana de desenvolvimento de software, situada na **Avenida Tancredo Neves, nº 1186, Edifício Catuca, bairro Caminho das Árvores, Salvador – BA, CEP 41820-020**.

Com foco em **soluções digitais de impacto local**, a empresa tem como seu principal produto o **Dendê Eventos**, um aplicativo voltado à **divulgação, organização e gestão de eventos culturais, acadêmicos e sociais**, conectando organizadores e público de forma simples e eficiente.

Neste ano, a Dendê Softhouse inicia o seu **programa de trainee Dendezeiros**.  
Parabéns por terem sido aprovados! 🌱

Vocês serão integrados aos times de:

- 📊 Dados
- 📱 Desenvolvimento Mobile Nativo
- 🌐 Desenvolvimento Web

E farão parte do time técnico responsável pelo desenvolvimento da **nova versão do Dendê Eventos**.

---

## 🚀 Sobre o Projeto

A **`dende-eventos-api`** é uma API REST responsável por gerenciar usuários, eventos e ingressos da plataforma Dendê Eventos.

Este projeto utiliza como dependência o **`springframework`**, um **framework web desenvolvido em Java**, criado para apoiar o aprendizado de boas práticas no desenvolvimento de **APIs REST**, como:

- Organização de código
- Padronização de endpoints
- Uso de HTTP Methods
- Aplicação de regras de negócio
- Estruturação de projetos backend

---

## 🧩 Dependências e Tecnologias

Este projeto foi desenvolvido utilizando o ecossistema **Spring Boot**, uma das principais plataformas para desenvolvimento de aplicações Java modernas, com foco em APIs REST, persistência de dados, validação e documentação automática.

### Dependências Utilizadas

| Dependência | Descrição |
|------------|------------|
| **org.springframework.boot:spring-boot-starter-web** | Starter para desenvolvimento de APIs REST utilizando Spring MVC. |
| **org.springframework.boot:spring-boot-starter-data-jpa** | Implementação da camada de persistência utilizando JPA (Java Persistence API). |
| **org.springframework.boot:spring-boot-starter-validation** | Suporte à validação de dados utilizando Jakarta Bean Validation. |
| **org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0** | Geração automática da documentação da API utilizando OpenAPI e Swagger UI. |
| **org.mapstruct:mapstruct** | Biblioteca para mapeamento automático entre entidades, DTOs e objetos de transferência de dados. |
| **org.projectlombok:lombok** | Redução de código repetitivo por meio de anotações para geração automática de getters, setters, construtores e outros recursos. |
| **com.mysql:mysql-connector-j** | Driver JDBC responsável pela conexão da aplicação com o banco de dados MySQL. |

### Dependências de Teste

| Dependência | Descrição |
|------------|------------|
| **org.springframework.boot:spring-boot-starter-test** | Conjunto de ferramentas para testes unitários, de integração e testes da aplicação Spring Boot. |
| **org.junit.platform:junit-platform-launcher** | Executor da plataforma JUnit para execução dos testes automatizados. |

### Plugins e Configurações Principais

- **org.springframework.boot** `3.4.0`
- **io.spring.dependency-management** `1.1.7`
- **Java** `21`
- **Banco de Dados:** MySQL
- **MapStruct:** `1.6.3`
---

## 👥 Equipe

**Nome da Equipe:**
Marselha

**Integrantes do Time:**

1. André Almeida Gomes Neto
2. Deivid Souza dos Santos Oliveira
3. Hellen Verena da Conceição Magalhães
4. Ismael Rodrigues de Oliveira Neto
5. João Vitor da Conceição de Almeida

---

## 📚 Histórias do Usuário Atendidas

A API foi construída com base nas seguintes **User Stories**:

### 👤 Usuários
- Cadastro de usuário comum
- Cadastro de usuário organizador
- Alteração de perfil
- Visualização de perfil
- Desativação de usuário
- Reativação de usuário

### 📅 Eventos
- Cadastro de evento
- Alteração de evento
- Ativação de evento
- Desativação de evento
- Listagem de eventos do organizador
- Feed de eventos ativos

### 🎟️ Ingressos
- Compra de ingresso
- Cancelamento de ingresso
- Listagem de ingressos do usuário

---

## 🔗 Resumo dos Endpoints da API

### 👤 Usuários

| Método | Endpoint | Descrição |
|------|---------|----------|
| POST | `/usuarios` | Cadastro de usuário comum |
| PUT | `/usuarios/{usuarioId}` | Alterar dados do usuário |
| GET | `/usuarios/{usuarioId}` | Visualizar perfil do usuário |
| PATCH | `/usuarios/{usuarioId}/{status}` | Ativar ou desativar usuário |

---

### 🧑‍💼 Organizadores

| Método | Endpoint | Descrição |
|------|---------|----------|
| POST | `/organizadores` | Cadastro de usuário organizador |
| PUT | `/organizadores/{organizadorId}` | Alterar dados do organizador |
| GET | `/organizadores/{organizadorId}` | Visualizar perfil do organizador |
| PATCH | `/organizadores/{organizadorId}/{status}` | Ativar ou desativar organizador |

---

### 📅 Eventos

| Método | Endpoint | Descrição |
|------|---------|----------|
| POST | `/organizadores/{organizadorId}/eventos` | Cadastrar evento |
| PUT | `/organizadores/{organizadorId}/eventos/{eventoId}` | Alterar evento |
| PATCH | `/organizadores/{organizadorId}/eventos/{status}` | Ativar ou desativar evento |
| GET | `/organizadores/{organizadorId}/eventos` | Listar eventos do organizador |
| GET | `/eventos` | Feed de eventos ativos |

---

### 🎟️ Ingressos

| Método | Endpoint | Descrição |
|------|---------|----------|
| POST | `/organizadores/{organizadorId}/eventos/{eventoId}/ingressos` | Comprar ingresso |
| POST | `/usuarios/{usuarioId}/ingressos/{ingressoId}` | Cancelar ingresso |
| GET | `/usuarios/{usuarioId}/ingressos` | Listar ingressos do usuário |

---

## 🧪 Testes e Documentação

Os endpoints da API podem ser testados utilizando a **collection do Postman** disponibilizada junto ao projeto, organizada de acordo com os domínios funcionais e histórias do usuário.

---

## 🌱 Considerações Finais

Este projeto possui **caráter educacional**, sendo parte do programa de formação da **Dendê Softhouse**.  
O objetivo é aplicar conceitos de **desenvolvimento backend**, **boas práticas de APIs REST** e **trabalho em equipe**, preparando os participantes para desafios reais do mercado.

Bom desenvolvimento e sejam bem-vindos à Dendê! 🌴🚀

---

Projeto desenvolvido para a OAT 4 - Introdução ao Spring Boot Framework
