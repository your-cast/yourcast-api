DROP TABLE IF EXISTS followers;

DROP SEQUENCE IF EXISTS followers_id_seq;

CREATE SEQUENCE followers_id_seq
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1;

CREATE TABLE IF NOT EXISTS followers
(
    id                BIGINT       NOT NULL DEFAULT nextval('followers_id_seq'::regclass),
    following_user_id BIGINT       NOT NULL,
    followed_user_id  BIGINT       NOT NULL,
    notifications     VARCHAR(255) NOT NULL,
    created_at        TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at        TIMESTAMP    NOT NULL DEFAULT now(),
    PRIMARY KEY (id),
    CONSTRAINT fk_following_user_id FOREIGN KEY (following_user_id) REFERENCES users (id),
    CONSTRAINT fk_followed_user_id FOREIGN KEY (followed_user_id) REFERENCES users (id)
);