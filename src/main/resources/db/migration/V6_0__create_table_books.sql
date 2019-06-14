DROP TABLE IF EXISTS books CASCADE;

CREATE TABLE books
(
  id                  BIGSERIAL PRIMARY KEY,
  title               CHARACTER VARYING(255) UNIQUE,
  author_id           BIGINT NOT NULL,
  genre_id            BIGINT NOT NULL,
  publishing_house    CHARACTER VARYING(255),
  date_of_publication TIMESTAMP,
  count_in_stock      BIGINT,
  image               BYTEA,

  FOREIGN KEY (author_id) REFERENCES authors (id)
    ON DELETE RESTRICT,

  FOREIGN KEY (genre_id) REFERENCES genres (id)
    ON DELETE RESTRICT
)
