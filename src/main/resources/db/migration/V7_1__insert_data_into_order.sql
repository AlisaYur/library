INSERT INTO orders (book_id, user_login, type_issue, start_date, date_of_delivery, penalty, status,
                    pay_id)
VALUES (1, 'john', 'SUBSCRIPTION', '2019-02-04', '2019-03-04', 0, 'APPROVED',
        floor(random() * 1000000 + 1)::text);

INSERT INTO orders (book_id, user_login, type_issue, start_date, date_of_delivery, penalty, status,
                    pay_id)
VALUES (1, 'statham', 'SUBSCRIPTION', '2019-02-04', '2019-03-04', 0, 'APPROVED',
        floor(random() * 1000000 + 1)::text);