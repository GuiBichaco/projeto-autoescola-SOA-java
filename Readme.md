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

Esta versão finaliza o sistema com a implementação de **Controlo de Acesso Baseado em Papéis (RBAC)**, distinguindo utilizadores `ADMIN` (que gerenciam o sistema) de utilizadores `USER` (que consomem os dados da autoescola).

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

## 🧭 Como Testar a API (Plano de Teste Completo)

Testaremos a API em três perspetivas: Visitante, Administrador e Usuário Comum.

### Fluxo de Teste 1: O Visitante (Não Autenticado)
**Objetivo:** Garantir que a API está "trancada".

1.  **Ação:** Tentar listar alunos.
2.  **Requisição:** `GET http://localhost:8080/alunos`
3.  **Verificação:**
    * **Resultado Esperado:** 🛑 Status `403 Forbidden`.
    * **Conclusão:** A segurança está ativa e a barrar requisições sem token.

### Fluxo de Teste 2: O Administrador (`ROLE_ADMIN`)
**Objetivo:** Provar que o `ADMIN` tem controlo total (gerir utilizadores + gerir autoescola).

#### 2.1. Obter Token de ADMIN
1.  **Ação:** Fazer login como o utilizador `admin`.
2.  **Requisição:** `POST http://localhost:8080/login`
3.  **Corpo (Body) da Requisição:**
    ```json
    {
        "login": "admin",
        "senha": "123456"
    }
    ```
4.  **Verificação:**
    * **Resultado Esperado:** ✅ Status `200 OK` e um JSON com o token.
    * **Ação:** Copie este token. Vamos chamá-lo de `ADMIN_TOKEN`.

#### 2.2. Testar o CRUD de Utilizadores (Permissão de Admin)
Use o `ADMIN_TOKEN` em todas as requisições abaixo (na aba `Authorization` -> `Bearer Token`).

1.  **Ação: Criar um Utilizador `USER`**
    * **Requisição:** `POST http://localhost:8080/usuarios`
    * **Corpo (Body):**
        ```json
        {
            "login": "user_comum",
            "senha": "123",
            "role": "ROLE_USER"
        }
        ```
    * **Verificação:** ✅ Status `201 Created`.

2.  **Ação: Listar Utilizadores**
    * **Requisição:** `GET http://localhost:8080/usuarios`
    * **Verificação:** ✅ Status `200 OK`. A lista deve mostrar os utilizadores `admin` e o novo `user_comum`.

3.  **Ação: Excluir um Utilizador**
    * *(Assumindo que `user_comum` é o ID 2)*
    * **Requisição:** `DELETE http://localhost:8080/usuarios/2`
    * **Verificação:** ✅ Status `204 No Content`.

#### 2.3. Testar a Troca de Senha (do Próprio Admin)
1.  **Ação:** O Admin troca a sua própria senha.
2.  **Requisição:** `PUT http://localhost:8080/usuarios/minha-senha`
3.  **Authorization:** `Bearer ADMIN_TOKEN`
4.  **Corpo (Body):**
    ```json
    {
        "senhaAtual": "123456",
        "novaSenha": "nova_senha_admin"
    }
    ```
5.  **Verificação:**
    * **Resultado Esperado:** ✅ Status `200 OK` ("Senha alterada com sucesso.").
    * **Teste de Confirmação:** Tente fazer login com a senha antiga (`123456`). Deve falhar (🛑 `401 Unauthorized`).

### Fluxo de Teste 3: O Utilizador Comum (`ROLE_USER`)
**Objetivo:** Provar que o `USER` pode gerir a autoescola, mas NÃO pode gerir outros utilizadores.

#### 3.1. Obter Token de USER
* *(Pré-requisito: Use o seu `ADMIN_TOKEN` para criar o `user_comum` (login: `user_comum`, senha: `123`, role: `ROLE_USER`)).*
1.  **Ação:** Fazer login como `user_comum`.
2.  **Requisição:** `POST http://localhost:8080/login`
3.  **Corpo (Body):**
    ```json
    {
        "login": "user_comum",
        "senha": "123"
    }
    ```
4.  **Verificação:**
    * **Resultado Esperado:** ✅ Status `200 OK` e um novo token.
    * **Ação:** Copie este token. Vamos chamá-lo de `USER_TOKEN`.

#### 3.2. Testar Permissões de Admin (Falha Esperada)
Use o `USER_TOKEN` em todas as requisições abaixo.

1.  **Ação: Tentar Listar Utilizadores**
    * **Requisição:** `GET http://localhost:8080/usuarios`
    * **Verificação:** 🛑 Status `403 Forbidden`. **Correto!**

2.  **Ação: Tentar Criar um Utilizador**
    * **Requisição:** `POST http://localhost:8080/usuarios`
    * **Verificação:** 🛑 Status `403 Forbidden`. **Correto!**

#### 3.3. Testar Permissões da Autoescola (Sucesso Esperado)
Use o `USER_TOKEN` nesta requisição.

1.  **Ação: Criar um Aluno**
    * **Requisição:** `POST http://localhost:8080/alunos`
    * **Authorization:** `Bearer USER_TOKEN`
    * **Corpo (Body):**
        ```json
        {
          "nome": "Mariana Costa e Silva",
          "email": "mariana.costa@example.com",
          "telefone": "11987654321",
          "cpf": "48337083074",
          "endereco": {
            "logradouro": "Avenida Paulista",
            "numero": "1578",
            "bairro": "Bela Vista",
            "cidade": "São Paulo",
            "uf": "SP",
            "cep": "01310-200"
          }
        }
        ```
    * **Verificação:** ✅ Status `201 Created`. **Correto!**

2.  **Ação: Listar Instrutores**
    * **Requisição:** `GET http://localhost:8080/instrutores`
    * **Verificação:** ✅ Status `200 OK`. **Correto!**

#### 3.4. Testar a Troca de Senha (do Próprio Utilizador)
1.  **Ação:** O `user_comum` troca a sua própria senha.
2.  **Requisição:** `PUT http://localhost:8080/usuarios/minha-senha`
3.  **Authorization:** `Bearer USER_TOKEN`
4.  **Corpo (Body):**
    ```json
    {
        "senhaAtual": "123",
        "novaSenha": "nova_senha_user"
    }
    ```
5.  **Verificação:** ✅ Status `200 OK`.

### Fluxo de Teste 4: Regras de Negócio (Agendamento)
**Objetivo:** Confirmar que as validações de agendamento ainda funcionam.

*(Use qualquer token válido, `ADMIN_TOKEN` ou `USER_TOKEN`. Crie um Aluno (ID 1) antes de começar).*

1.  **Ação (Falha - Horário de Funcionamento):**
    * `POST /agendamentos` com uma data que seja um Domingo.
    * **Verificação:** 🛑 `400 Bad Request` + mensagem "Agendamentos só podem ser feitos de Segunda a Sábado...".

2.  **Ação (Falha - Antecedência):**
    * `POST /agendamentos` com uma data/hora para 10 minutos a partir de agora.
    * **Verificação:** 🛑 `400 Bad Request` + mensagem "antecedência mínima de 30 minutos...".

3.  **Ação (Sucesso - Aula 1 e 2):**
    * `POST /agendamentos` com Aluno 1 para uma data válida às 10:00. (✅ `201 Created`)
    * `POST /agendamentos` com Aluno 1 para a mesma data às 14:00. (✅ `201 Created`)

4.  **Ação (Falha - Limite Diário):**
    * `POST /agendamentos` com Aluno 1, para a mesma data, às 16:00.
    * **Verificação:** 🛑 `400 Bad Request` + mensagem "Um aluno não pode ter mais de duas instruções no mesmo dia.".

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