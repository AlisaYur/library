DROP TABLE IF EXISTS orders CASCADE;

CREATE TABLE orders
(
  id               BIGSERIAL    NOT NULL PRIMARY KEY,
  book_id          BIGINT       NOT NULL,
  user_login       VARCHAR(255) NOT NULL,
  type_issue       VARCHAR(255) NOT NULL,
  start_date       TIMESTAMP    NOT NULL,
  date_of_delivery TIMESTAMP    NOT NULL,
  penalty          BIGINT       NOT NULL,
  status           VARCHAR(255) NOT NULL,
  pay_id           VARCHAR(255) NOT NULL UNIQUE,
  FOREIGN KEY (book_id) REFERENCES books (id)
);
