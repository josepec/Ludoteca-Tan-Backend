INSERT INTO CATEGORY(id, name) VALUES (1, 'Eurogames');
INSERT INTO CATEGORY(id, name) VALUES (2, 'Ameritrash');
INSERT INTO CATEGORY(id, name) VALUES (3, 'Familiar');

INSERT INTO AUTHOR(id, name, nationality) VALUES (1, 'Alan R. Moon', 'US');
INSERT INTO AUTHOR(id, name, nationality) VALUES (2, 'Vital Lacerda', 'PT');
INSERT INTO AUTHOR(id, name, nationality) VALUES (3, 'Simone Luciani', 'IT');
INSERT INTO AUTHOR(id, name, nationality) VALUES (4, 'Perepau Llistosella', 'ES');
INSERT INTO AUTHOR(id, name, nationality) VALUES (5, 'Michael Kiesling', 'DE');
INSERT INTO AUTHOR(id, name, nationality) VALUES (6, 'Phil Walker-Harding', 'US');


INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (1, 'On Mars', '14', 1, 2);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (2, 'Aventureros al tren', '8', 3, 1);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (3, '1920: Wall Street', '12', 1, 4);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (4, 'Barrage', '14', 1, 3);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (5, 'Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO GAME(id, title, age, category_id, author_id) VALUES (6, 'Azul', '8', 3, 5);

INSERT INTO CLIENT(id, name) VALUES (1, 'Gugulethu Orla');
INSERT INTO CLIENT(id, name) VALUES (2, 'Vaishnavi Ngải');
INSERT INTO CLIENT(id, name) VALUES (3, 'Oscar Yakim');
INSERT INTO CLIENT(id, name) VALUES (4, 'Ferkó Siegward');
INSERT INTO CLIENT(id, name) VALUES (5, 'Vyacheslav Deeann');

INSERT INTO LOAN(id, client_id, game_id, start_date, end_date) VALUES (1, 1, 2, '2022-01-01', '2022-01-13');
INSERT INTO LOAN(id, client_id, game_id, start_date, end_date) VALUES (2, 1, 3, '2022-10-01', '2022-10-13');
INSERT INTO LOAN(id, client_id, game_id, start_date, end_date) VALUES (3, 2, 4, '2022-02-01', '2022-02-13');
INSERT INTO LOAN(id, client_id, game_id, start_date, end_date) VALUES (4, 3, 1, '2022-03-01', '2022-03-13');
INSERT INTO LOAN(id, client_id, game_id, start_date, end_date) VALUES (5, 4, 2, '2022-04-02', '2022-04-05');
INSERT INTO LOAN(id, client_id, game_id, start_date, end_date) VALUES (6, 1, 5, '2022-05-01', '2022-05-13');
INSERT INTO LOAN(id, client_id, game_id, start_date, end_date) VALUES (7, 4, 6, '2022-06-01', '2022-06-13');
INSERT INTO LOAN(id, client_id, game_id, start_date, end_date) VALUES (8, 1, 3, '2022-07-01', '2022-07-13');
INSERT INTO LOAN(id, client_id, game_id, start_date, end_date) VALUES (9, 2, 5, '2022-08-01', '2022-08-13');
INSERT INTO LOAN(id, client_id, game_id, start_date, end_date) VALUES (10, 3, 2, '2022-09-01', '2022-09-13');