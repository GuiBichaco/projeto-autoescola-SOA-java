# Sistema de Gestão para Autoescola 🚗

![Java](https://img.shields.io/badge/Java-21-blue?style=for-the-badge&logo=openjdk)
![Spring](https://img.shields.io/badge/Spring_Boot-3.3-green?style=for-the-badge&logo=spring)
![Security](https://img.shields.io/badge/Spring_Security-6.3-blueviolet?style=for-the-badge&logo=springsecurity)
![JWT](https://img.shields.io/badge/JWT-4.4-black?style=for-the-badge&logo=jsonwebtokens)
![Flyway](https://img.shields.io/badge/Flyway-10.0-orange?style=for-the-badge&logo=flyway)
![H2](https://img.shields.io/badge/H2_Database-2.2-lightgrey?style=for-the-badge&logo=h2)
![Maven](https://img.shields.io/badge/Maven-4.0-red?style=for-the-badge&logo=apache-maven)

## Desenvolvido por:
- Leticia Fontana Baptista - RM 550289
- ⁠Julia Palomari - RM 551910
- ⁠Vinicius Sobreira Borges - RM 97767
- ⁠Julia Ortiz - RM 550204
- Guilherme Catelli Bichaco - RM 97989

## 📝 Descrição

API RESTful **segura** desenvolvida em Java 21 e Spring Boot 3 para o backend de um sistema de gerenciamento de autoescola. O projeto implementa um fluxo de autenticação e autorização completo utilizando **Spring Security e JSON Web Tokens (JWT)**.

Além do CRUD completo para Alunos e Instrutores, a API foi refatorada para aplicar princípios de Domain-Driven Design (DDD) e Clean Code, como:

* **Value Objects (VO):** Os dados de `Endereco` foram extraídos para um objeto `@Embeddable`, promovendo reuso e coesão.
* **Database Migrations:** O versionamento do schema do banco de dados é gerenciado pelo **Flyway**, garantindo um ambiente de banco de dados consistente.
* **Padrão Strategy:** As regras de negócio complexas para agendamento foram separadas em classes de validação individuais (`ValidadorAgendamento`), tornando o `AgendamentoService` limpo e extensível.

## ✨ Funcionalidades Principais

-   **Segurança e Autenticação (JWT):**
    -   Endpoint `POST /login` para autenticação de usuários.
    -   Geração de Token JWT para autorização de requisições.
    -   Todos os endpoints (exceto `/login` e `/h2-console`) são protegidos e exigem um Bearer Token válido.
-   **Gestão de Instrutores:**
    -   Cadastro (Create) com endereço embutido.
    -   Listagem paginada e ordenada (Read).
    -   Atualização de dados (Update).
    -   Exclusão lógica (Soft Delete).
-   **Gestão de Alunos:**
    -   Cadastro (Create) com endereço embutido.
    -   Listagem paginada e ordenada (Read).
    -   Atualização de dados (Update).
    -   Exclusão lógica (Soft Delete).
-   **Agendamento de Aulas (Refatorado):**
    -   **Validação via Strategy Pattern** para:
        -   Horário de funcionamento (Segunda a Sábado, 06:00 - 21:00).
        -   Antecedência mínima de 30 minutos.
        -   Aluno ativo.
        -   Instrutor ativo (se especificado).
        -   Disponibilidade do instrutor.
        -   Limite máximo de duas aulas por dia para o aluno.
    -   Atribuição aleatória de instrutor disponível.
-   **Cancelamento de Aulas:**
    -   Exigência de antecedência mínima de 24 horas.
    -   Obrigatoriedade de informar o motivo do cancelamento.

## 🛠️ Tecnologias Utilizadas

-   **Backend:**
    -   Java 21
    -   Spring Boot 3.3
    -   Spring Web
    -   Spring Data JPA
    -   Hibernate
-   **Segurança:**
    -   Spring Security 6
    -   JWT (Biblioteca `com.auth0:java-jwt`)
-   **Banco de Dados:**
    -   H2 (Banco em memória para desenvolvimento)
    -   Flyway (Para versionamento e migração do schema)
-   **Build & Dependências:**
    -   Apache Maven
    -   Lombok
-   **Testes:**
    -   Postman / Insomnia (para testes manuais da API)

## 🚀 Como Executar o Projeto Localmente

### Pré-requisitos
-   JDK 21
-   Apache Maven
-   Uma IDE (IntelliJ IDEA é recomendado)

### Passos
1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/GuiBichaco/projeto-autoescola-SOA-java.git
    ```
2.  **Abra o projeto** em sua IDE. O Maven irá baixar todas as dependências listadas no `pom.xml`.

3.  **Execute a aplicação:**
    -   Encontre a classe `AutoescolaApplication.java` e execute o método `main`.
    -   **Importante:** Ao iniciar, o **Flyway** será executado automaticamente. Ele rodará as *migrations* da pasta `resources/db/migration`:
        -   `V1`: Cria as tabelas `alunos`, `instrutores` e `agendamentos`.
        -   `V2`: Cria a tabela `usuarios` e **insere um usuário padrão (`admin` / `123456`)** para permitir o teste da API.
    -   A aplicação iniciará na porta `8080`.

4.  **Acesse o banco de dados H2 (Opcional):**
    -   Com a aplicação rodando, acesse `http://localhost:8080/h2-console` no seu navegador.
    -   **JDBC URL:** `jdbc:h2:mem:autoescola_db`
    -   **User Name:** `sa`
    -   **Password:** (deixe em branco)
    -   Clique em "Connect" e você poderá ver as tabelas criadas pelo Flyway, incluindo a tabela `USUARIOS` com o usuário `admin`.

## 🧭 Como Testar a API (Novo Fluxo com JWT)

Com a implementação do Spring Security, **todos os endpoints (exceto `/login`) estão protegidos**. Tentar acessá-los diretamente resultará em um erro `403 Forbidden`.

### Passo 1: Autenticar e Obter o Token

Primeiro, você deve se autenticar para obter um Token JWT.

-   **Método:** `POST`
-   **URL:** `http://localhost:8080/login`
-   **Body (raw/JSON):**
    ```json
    {
        "login": "admin",
        "senha": "123456"
    }
    ```
-   **Resposta (200 OK):**
    ```json
    {
        "token": "eyJh... (um token JWT muito longo) ...G4A"
    }
    ```
**➡️ Copie o valor do token gerado.**

### Passo 2: Acessar Endpoints Protegidos

Agora, para qualquer outra requisição (criar aluno, listar instrutor, etc.), você deve enviar esse token.

-   **Método:** `POST`
-   **URL:** `http://localhost:8080/alunos`
-   **Aba "Authorization" (Postman/Insomnia):**
    -   **Tipo:** `Bearer Token`
    -   **Token:** Cole o token que você copiou do Passo 1.
-   **Aba "Body" (raw/JSON):**
    *Note o novo formato com o `endereco` embutido:*
    ```json
    {
      "nome": "Mariana Costa e Silva",
      "email": "mariana.costa@example.com",
      "telefone": "11987654321",
      "cpf": "48337083074",
      "endereco": {
        "logradouro": "Avenida Paulista",
        "numero": "1578",
        "complemento": "Andar 10",
        "bairro": "Bela Vista",
        "cidade": "São Paulo",
        "uf": "SP",
        "cep": "01310-200"
      }
    }
    ```
-   **Resposta:** `201 Created`. A requisição foi autorizada e o aluno foi criado com sucesso!

## 📜 Endpoints da API

| Método HTTP | Endpoint                        | Descrição                                 | Autorização Necessária |
| :---------- | :------------------------------ | :---------------------------------------- | :--------------------- |
| `POST`      | `/login`                        | Autentica um usuário e retorna um token JWT. | **Não** |
| `POST`      | `/instrutores`                  | Cadastra um novo instrutor.               | **Sim (Bearer Token)** |
| `GET`       | `/instrutores`                  | Lista todos os instrutores ativos.        | **Sim (Bearer Token)** |
| `PUT`       | `/instrutores/{id}`             | Atualiza os dados de um instrutor.        | **Sim (Bearer Token)** |
| `DELETE`    | `/instrutores/{id}`             | Inativa um instrutor.                     | **Sim (Bearer Token)** |
| `POST`      | `/alunos`                       | Cadastra um novo aluno.                   | **Sim (Bearer Token)** |
| `GET`       | `/alunos`                       | Lista todos os alunos ativos.             | **Sim (Bearer Token)** |
| `PUT`       | `/alunos/{id}`                  | Atualiza os dados de um aluno.            | **Sim (Bearer Token)** |
| `DELETE`    | `/alunos/{id}`                  | Inativa um aluno.                         | **Sim (Bearer Token)** |
| `POST`      | `/agendamentos`                 | Agenda uma nova aula de instrução.        | **Sim (Bearer Token)** |
| `POST`      | `/agendamentos/{id}/cancelar`   | Cancela um agendamento existente.         | **Sim (Bearer Token)** |

---