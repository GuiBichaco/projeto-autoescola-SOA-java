-- Tabela de Instrutores
CREATE TABLE instrutores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    cnh VARCHAR(20) NOT NULL UNIQUE,
    especialidade VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL,
    -- Campos do Endereco Embutido
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    uf CHAR(2),
    cep VARCHAR(9)
);

-- Tabela de Alunos
CREATE TABLE alunos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    cpf VARCHAR(14) NOT NULL UNIQUE,
    ativo BOOLEAN NOT NULL,
    -- Campos do Endereco Embutido
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    uf CHAR(2),
    cep VARCHAR(9)
);

-- Tabela de Agendamentos
CREATE TABLE agendamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    aluno_id BIGINT NOT NULL,
    instrutor_id BIGINT NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    cancelado BOOLEAN NOT NULL,
    motivo_cancelamento VARCHAR(50),

    FOREIGN KEY (aluno_id) REFERENCES alunos(id),
    FOREIGN KEY (instrutor_id) REFERENCES instrutores(id)
);