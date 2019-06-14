INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('Thinking In Java', (SELECT id FROM authors WHERE last_name = 'Eckel'),
        (SELECT id FROM genres WHERE name = 'Programming'), 'Prentice Hall', current_timestamp, 20,
        null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('1984', (SELECT id FROM authors WHERE last_name = 'Orwell'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('Harry Potter and the Philosophers Stone',
        (SELECT id FROM authors WHERE last_name = 'Rowling'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '2000-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('The Lord of the Rings', (SELECT id FROM authors WHERE last_name = 'Tolkien'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('The Great Gatsby', (SELECT id FROM authors WHERE last_name = 'Fitzgerald'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('Pride and Prejudice', (SELECT id FROM authors WHERE last_name = 'Austen'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('The Diary Of A Young Girl', (SELECT id FROM authors WHERE last_name = 'Frank'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('The Book Thief', (SELECT id FROM authors WHERE last_name = 'Zusak'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('The Hobbit', (SELECT id FROM authors WHERE last_name = 'Tolkien'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('Little Women', (SELECT id FROM authors WHERE last_name = 'Alcott'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('Fahrenheit 451', (SELECT id FROM authors WHERE last_name = 'Bradbury'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('Jane Eyre', (SELECT id FROM authors WHERE last_name = 'Bronte'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('Animal Farm', (SELECT id FROM authors WHERE last_name = 'Orwell'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('Gone with the Wind', (SELECT id FROM authors WHERE last_name = 'Mitchell'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);

INSERT INTO books(title, author_id, genre_id, publishing_house, date_of_publication, count_in_stock,
                  image)
VALUES ('The Catcher in the Rye', (SELECT id FROM authors WHERE last_name = 'Salinger'),
        (SELECT id FROM genres WHERE name = 'Novel'), 'Prentice Hall', '1984-01-01', 20, null);