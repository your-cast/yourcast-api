DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS users_id_seq;

CREATE SEQUENCE users_id_seq
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

CREATE TABLE IF NOT EXISTS users
(
    id                BIGINT    NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    name              VARCHAR   NOT NULL,
    email             VARCHAR   NOT NULL,
    password          VARCHAR   NOT NULL,
    system_id         VARCHAR   NOT NULL,
    email_verified_at TIMESTAMP          DEFAULT NULL,
    created_at        TIMESTAMP NOT NULL DEFAULT now(),
    updated_at        TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE INDEX idx_users_email ON users (email);