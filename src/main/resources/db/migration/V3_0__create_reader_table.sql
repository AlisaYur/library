DROP TABLE IF EXISTS reader CASCADE;

CREATE TABLE reader
(
  id       BIGSERIAL PRIMARY KEY,
  name     VARCHAR(255) NOT NULL,
  login    VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email    VARCHAR(255) NOT NULL UNIQUE,
  isActive boolean      NOT NULL,
  role_id  BIGINT       NOT NULL,
  FOREIGN KEY (role_id) REFERENCES role (id)
);
