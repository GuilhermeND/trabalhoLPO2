CREATE TABLE IF NOT EXISTS clientes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    rg VARCHAR(30) NOT NULL UNIQUE,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    endereco VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS contas (
    id BIGSERIAL PRIMARY KEY,
    numero INTEGER NOT NULL UNIQUE,
    cliente_id BIGINT NOT NULL UNIQUE,
    tipo VARCHAR(20) NOT NULL,
    saldo NUMERIC(12, 2) NOT NULL DEFAULT 0,
    limite NUMERIC(12, 2),
    montante_minimo NUMERIC(12, 2),
    deposito_minimo NUMERIC(12, 2),
    CONSTRAINT fk_contas_clientes
        FOREIGN KEY (cliente_id)
        REFERENCES clientes (id)
        ON DELETE CASCADE,
    CONSTRAINT ck_contas_tipo
        CHECK (tipo IN ('CORRENTE', 'INVESTIMENTO')),
    CONSTRAINT ck_conta_corrente_campos
        CHECK (
            tipo <> 'CORRENTE'
            OR (
                limite IS NOT NULL
                AND montante_minimo IS NULL
                AND deposito_minimo IS NULL
            )
        ),
    CONSTRAINT ck_conta_investimento_campos
        CHECK (
            tipo <> 'INVESTIMENTO'
            OR (
                limite IS NULL
                AND montante_minimo IS NOT NULL
                AND deposito_minimo IS NOT NULL
            )
        )
);

CREATE SEQUENCE IF NOT EXISTS numero_conta_seq START WITH 1000 INCREMENT BY 1;

CREATE INDEX IF NOT EXISTS idx_clientes_nome ON clientes (nome);
CREATE INDEX IF NOT EXISTS idx_clientes_sobrenome ON clientes (sobrenome);
CREATE INDEX IF NOT EXISTS idx_clientes_cpf ON clientes (cpf);
CREATE INDEX IF NOT EXISTS idx_contas_cliente_id ON contas (cliente_id);

