-- 1. Adiciona a coluna 'role', permitindo nulos temporariamente
ALTER TABLE usuarios ADD COLUMN role VARCHAR(50);

-- 2. Define o usuário 'admin' existente como ROLE_ADMIN
UPDATE usuarios SET role = 'ROLE_ADMIN' WHERE login = 'admin';

-- 3. Define todos os outros usuários que possam existir (se houver) como ROLE_USER
UPDATE usuarios SET role = 'ROLE_USER' WHERE role IS NULL;

-- 4. Altera a coluna para não permitir nulos
ALTER TABLE usuarios ALTER COLUMN role VARCHAR(50) NOT NULL;