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

Esta versão finaliza o sistema com a implementação de **Controle de Acesso Baseado em Papéis (RBAC)**, distinguindo usuários `ADMIN` (que gerenciam o sistema) de usuários `USER` (que consomem os dados da autoescola).

A API foi refatorada para aplicar princípios de Domain-Driven Design (DDD) e Clean Code, como:

* **Value Objects (VO):** Os dados de `Endereco` foram extraídos para um objeto `@Embeddable`, promovendo reuso e coesão.
* **Database Migrations:** O versionamento do schema do banco de dados é gerenciado pelo **Flyway**, garantindo um ambiente de banco de dados consistente.
* **Padrão Strategy:** As regras de negócio complexas para agendamento foram separadas em classes de validação individuais (`ValidadorAgendamento`), tornando o `AgendamentoService` limpo e extensível.

## ✨ Funcionalidades Principais

-   **Segurança e Autenticação (JWT):**
    -   Endpoint `POST /login` para autenticação de usuários.
    -   Geração de Token JWT para autorização de requisições.
    -   Controle de Acesso (RBAC) para todos os endpoints.
    -   Encriptação de senhas no banco de dados (BCrypt).

-   **Gestão de Usuários (Exclusivo - `ROLE_ADMIN`):**
    -   Cadastro de novos usuários (com `login`, `senha` e `role`).
    -   Listagem paginada de todos os usuários do sistema.
    -   Atualização de perfil de usuários (`login` e `role`).
    -   Exclusão de usuários (com proteção para o usuário 'admin' principal).

-   **Perfil do Usuário (Autenticado - `ADMIN` ou `USER`):**
    -   Endpoint `PUT /usuarios/minha-senha` permite que qualquer usuário logado altere a *própria* senha com segurança (validando a senha atual).

-   **Gestão de Instrutores (Autenticado):**
    -   Cadastro (Create) com endereço embutido.
    -   Listagem paginada e ordenada (Read).
    -   Atualização de dados (Update).
    -   Exclusão lógica (Soft Delete).

-   **Gestão de Alunos (Autenticado):**
    -   Cadastro (Create) com endereço embutido.
    -   Listagem paginada e ordenada (Read).
    -   Atualização de dados (Update).
    -   Exclusão lógica (Soft Delete).

-   **Agendamento de Aulas (Autenticado):**
    -   **Validação via Strategy Pattern** para:
        -   Horário de funcionamento (Segunda a Sábado, 06:00 - 21:00).
        -   Antecedência mínima de 30 minutos.
        -   Aluno ativo.
        -   Instrutor ativo (se especificado).
        -   Disponibilidade do instrutor.
        -   Limite máximo de duas aulas por dia para o aluno.
    -   Atribuição aleatória de instrutor disponível.

-   **Cancelamento de Aulas (Autenticado):**
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
    -   BCrypt Password Encoder
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
        -   `V2`: Cria a tabela `usuarios` e insere o usuário padrão (`admin` / `123456`).
        -   `V3`: Adiciona a coluna `role` à tabela `usuarios` e promove o usuário `admin` para `ROLE_ADMIN`.
    -   A aplicação iniciará na porta `8080`.

4.  **Acesse o banco de dados H2 (Opcional):**
    -   Com a aplicação rodando, acesse `http://localhost:8080/h2-console` no seu navegador.
    -   **JDBC URL:** `jdbc:h2:mem:autoescola_db`
    -   **User Name:** `sa`
    -   **Password:** (deixe em branco)
    -   Clique em "Connect" e você poderá ver as tabelas criadas pelo Flyway.

## 🧭 Como Testar a API (Fluxo Completo com RBAC)

Com a implementação do Spring Security, **todos os endpoints (exceto `/login` e `/h2-console`) estão protegidos**.

### Passo 1: Autenticar como ADMIN

Primeiro, obtenha um token com privilégios de Administrador.

-   **Método:** `POST`
-   **URL:** `http://localhost:8080/login`
-   **Body (raw/JSON):**
    ```json
    {
        "login": "admin",
        "senha": "123456"
    }
    ```
-   **Resposta (200 OK):** Você receberá um **Token de Admin**. Copie-o.

### Passo 2: Testar um Endpoint de ADMIN (Ex: Criar um novo Usuário)

-   **Método:** `POST`
-   **URL:** `http://localhost:8080/usuarios`
-   **Aba "Authorization"**:
    -   **Tipo:** `Bearer Token`
    -   **Token:** Cole o **Token de Admin**.
-   **Aba "Body" (raw/JSON):**
    ```json
    {
        "login": "user_comum",
        "senha": "123",
        "role": "ROLE_USER"
    }
    ```
-   **Resposta:** `201 Created`. (O usuário `user_comum` foi criado).

### Passo 3: Autenticar como USER

Agora, obtenha um token com privilégios de Usuário comum.

-   **Método:** `POST`
-   **URL:** `http://localhost:8080/login`
-   **Body (raw/JSON):**
    ```json
    {
        "login": "user_comum",
        "senha": "123"
    }
    ```
-   **Resposta (200 OK):** Você receberá um **Token de User**. Copie-o.

### Passo 4: Testar Restrições de Permissão (Falha Esperada)

-   **Método:** `GET`
-   **URL:** `http://localhost:8080/usuarios`
-   **Aba "Authorization"**:
    -   **Tipo:** `Bearer Token`
    -   **Token:** Cole o **Token de User** (do Passo 3).
-   **Resposta:** `403 Forbidden`. **Correto!** Um `ROLE_USER` não pode listar os usuários do sistema.

### Passo 5: Testar Endpoint de Usuário Comum (Sucesso)

-   **Método:** `GET`
-   **URL:** `http://localhost:8080/alunos`
-   **Aba "Authorization"**:
    -   **Tipo:** `Bearer Token`
    -   **Token:** Cole o **Token de User**.
-   **Resposta:** `200 OK`. **Correto!** Um `ROLE_USER` pode acessar os endpoints da autoescola.

### Passo 6: Testar Troca de Senha (Qualquer Usuário)

-   **Método:** `PUT`
-   **URL:** `http://localhost:8080/usuarios/minha-senha`
-   **Aba "Authorization"**:
    -   **Tipo:** `Bearer Token`
    -   **Token:** Cole o **Token de User**.
-   **Aba "Body" (raw/JSON):**
    ```json
    {
        "senhaAtual": "123",
        "novaSenha": "nova_senha_456"
    }
    ```
-   **Resposta:** `200 OK` (com a mensagem "Senha alterada com sucesso.").

## 📜 Endpoints da API

| Método HTTP | Endpoint                        | Descrição                                 | Autorização Necessária |
| :---------- | :------------------------------ | :---------------------------------------- | :--------------------- |
| `POST`      | `/login`                        | Autentica um usuário e retorna um token JWT. | **Público** |
| `POST`      | `/usuarios`                     | Cadastra um novo usuário (Admin).         | **ADMIN** |
| `GET`       | `/usuarios`                     | Lista todos os usuários do sistema (Admin). | **ADMIN** |
| `PUT`       | `/usuarios/{id}`                | Atualiza o perfil de um usuário (Admin).  | **ADMIN** |
| `DELETE`    | `/usuarios/{id}`                | Exclui um usuário (Admin).                | **ADMIN** |
| `PUT`       | `/usuarios/minha-senha`         | Usuário logado altera a própria senha.    | **Autenticado (User ou Admin)** |
| `POST`      | `/instrutores`                  | Cadastra um novo instrutor.               | **Autenticado (User ou Admin)** |
| `GET`       | `/instrutores`                  | Lista todos os instrutores ativos.        | **Autenticado (User ou Admin)** |
| `PUT`       | `/instrutores/{id}`             | Atualiza os dados de um instrutor.        | **Autenticado (User ou Admin)** |
| `DELETE`    | `/instrutores/{id}`             | Inativa um instrutor.                     | **Autenticado (User ou Admin)** |
| `POST`      | `/alunos`                       | Cadastra um novo aluno.                   | **Autenticado (User ou Admin)** |
| `GET`       | `/alunos`                       | Lista todos os alunos ativos.             | **Autenticado (User ou Admin)** |
| `PUT`       | `/alunos/{id}`                  | Atualiza os dados de um aluno.            | **Autenticado (User ou Admin)** |
| `DELETE`    | `/alunos/{id}`                  | Inativa um aluno.                         | **Autenticado (User ou Admin)** |
| `POST`      | `/agendamentos`                 | Agenda uma nova aula de instrução.        | **Autenticado (User ou Admin)** |
| `POST`      | `/agendamentos/{id}/cancelar`   | Cancela um agendamento existente.         | **Autenticado (User ou Admin)** |
| `GET`       | `/h2-console/**`                | Acesso ao console do banco de dados H2.   | **Público (Apenas Dev)** |

---