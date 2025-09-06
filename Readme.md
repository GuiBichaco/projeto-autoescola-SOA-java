# Sistema de Gestão para Autoescola 🚗

![Java](https://img.shields.io/badge/Java-21-blue?style=for-the-badge&logo=openjdk)
![Spring](https://img.shields.io/badge/Spring_Boot-3.3-green?style=for-the-badge&logo=spring)
![Maven](https://img.shields.io/badge/Maven-4.0-red?style=for-the-badge&logo=apache-maven)

## Desenvolvido por:
- Leticia Fontana Baptista - RM 550289
- ⁠Julia Palomari - RM 551910
- ⁠Vinicius Sobreira Borges - RM 97767
- ⁠Julia Ortiz - RM 550204
- Guilherme Catelli Bichaco - RM 97989

## 📝 Descrição

API RESTful desenvolvida em Java e Spring Boot para simular o backend de um sistema de gerenciamento de autoescola. O projeto implementa funcionalidades de CRUD para instrutores e alunos, além de um sistema robusto para agendamento e cancelamento de aulas de instrução, validando múltiplas regras de negócio.

Este projeto foi criado para demonstrar habilidades em desenvolvimento backend com o ecossistema Spring, aplicando boas práticas como o uso de DTOs (Data Transfer Objects), validações, tratamento de exceções e uma clara separação de responsabilidades entre as camadas (Controller, Service, Repository).

## ✨ Funcionalidades Principais

-   **Gestão de Instrutores:**
    -   Cadastro (Create)
    -   Listagem paginada e ordenada (Read)
    -   Atualização de dados (Update)
    -   Exclusão lógica (Soft Delete)
-   **Gestão de Alunos:**
    -   Cadastro (Create)
    -   Listagem paginada e ordenada (Read)
    -   Atualização de dados (Update)
    -   Exclusão lógica (Soft Delete)
-   **Agendamento de Aulas:**
    -   Validação do horário de funcionamento (Segunda a Sábado, 06:00 - 21:00).
    -   Validação de antecedência mínima de 30 minutos.
    -   Restrição de no máximo duas aulas por dia para o mesmo aluno.
    -   Validação de disponibilidade do instrutor.
    -   Atribuição aleatória de instrutor disponível, caso não seja especificado.
-   **Cancelamento de Aulas:**
    -   Exigência de antecedência mínima de 24 horas.
    -   Obrigatoriedade de informar o motivo do cancelamento.

## 🛠️ Tecnologias Utilizadas

-   **Backend:**
    -   Java 21
    -   Spring Boot 3
    -   Spring Web
    -   Spring Data JPA
    -   Hibernate
-   **Banco de Dados:**
    -   H2 (para ambiente de desenvolvimento)
    -   MySQL (suporte original)
-   **Build & Dependências:**
    -   Apache Maven
-   **Outras Ferramentas:**
    -   Lombok
    -   Postman (para testes manuais da API)

## 🚀 Como Executar o Projeto Localmente

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)
    ```
2.  **Navegue até a pasta do projeto e abra-o** em sua IDE de preferência (IntelliJ IDEA é recomendado).

3.  **Execute a aplicação:**
    -   Encontre a classe `AutoescolaApplication.java` e execute o método `main`.
    -   A aplicação iniciará na porta `8080`.

4.  **Acesse o banco de dados H2 (opcional):**
    -   Com a aplicação rodando, acesse `http://localhost:8080/h2-console` no seu navegador.
    -   Utilize a URL JDBC: `jdbc:h2:mem:autoescola_db` e o usuário `sa`.

##  Endpoints da API

Abaixo estão alguns dos principais endpoints disponíveis:

| Método HTTP | Endpoint                        | Descrição                                 |
| ----------- | ------------------------------- | ----------------------------------------- |
| `POST`      | `/instrutores`                  | Cadastra um novo instrutor.               |
| `GET`       | `/instrutores`                  | Lista todos os instrutores ativos.        |
| `PUT`       | `/instrutores/{id}`             | Atualiza os dados de um instrutor.        |
| `DELETE`    | `/instrutores/{id}`             | Inativa um instrutor.                     |
| `POST`      | `/alunos`                       | Cadastra um novo aluno.                   |
| `GET`       | `/alunos`                       | Lista todos os alunos ativos.             |
| `POST`      | `/agendamentos`                 | Agenda uma nova aula de instrução.        |
| `POST`      | `/agendamentos/{id}/cancelar`   | Cancela um agendamento existente.         |

---

