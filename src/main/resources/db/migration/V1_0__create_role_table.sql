DROP TABLE IF EXISTS role CASCADE;

CREATE TABLE role
(
  id   BIGSERIAL    NOT NULL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE
);