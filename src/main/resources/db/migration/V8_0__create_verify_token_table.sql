DROP TABLE IF EXISTS verify_token CASCADE;

CREATE TABLE verify_token
(
  id          BIGSERIAL PRIMARY KEY,
  token       VARCHAR(255) NOT NULL UNIQUE,
  user_login  VARCHAR(255) NOT NULL UNIQUE,
  expiry_date TIMESTAMP    NOT NULL,
  FOREIGN KEY (user_login) REFERENCES reader (login)
);
