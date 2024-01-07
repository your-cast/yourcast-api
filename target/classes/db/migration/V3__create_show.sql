DROP TABLE IF EXISTS shows;

DROP SEQUENCE IF EXISTS shows_id_seq;

CREATE SEQUENCE shows_id_seq
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1;

CREATE TABLE IF NOT EXISTS shows
(
    id            BIGINT    NOT NULL DEFAULT nextval('shows_id_seq'::regclass),
    title         VARCHAR   NOT NULL,
    description   VARCHAR   NOT NULL,
    artwork       VARCHAR            DEFAULT NULL,
    format        VARCHAR   NOT NULL,
    timezone      VARCHAR            DEFAULT NULL,
    language      VARCHAR            DEFAULT NULL,
    explicit      BOOLEAN            DEFAULT TRUE,
    category      VARCHAR            DEFAULT NULL,
    tags          VARCHAR            DEFAULT NULL,
    author        VARCHAR            DEFAULT NULL,
    podcast_owner VARCHAR            DEFAULT NULL,
    email_owner   VARCHAR            DEFAULT NULL,
    copyright     VARCHAR            DEFAULT NULL,
    token         VARCHAR   NOT NULL,
    status        VARCHAR   NOT NULL DEFAULT 'DRAFT',
    user_id       BIGINT    NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY (id),
    CONSTRAINT fk_show_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);
