DROP TABLE IF EXISTS tokens;

DROP SEQUENCE IF EXISTS tokens_id_seq;

CREATE SEQUENCE tokens_id_seq
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1;

CREATE TABLE IF NOT EXISTS tokens
(
    id         BIGINT       NOT NULL DEFAULT nextval('tokens_id_seq'::regclass),
    user_id    BIGINT       NOT NULL,
    token      VARCHAR(255) NOT NULL,
    token_type VARCHAR(255) NOT NULL,
    revoked    BOOLEAN               DEFAULT FALSE,
    expired    BOOLEAN               DEFAULT FALSE,
    created_at TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at TIMESTAMP    NOT NULL DEFAULT now(),
    PRIMARY KEY (id),
    CONSTRAINT fk_token_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);