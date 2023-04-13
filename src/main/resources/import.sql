INSERT INTO db_fotoalbum.photos (description, title, url, visible) VALUES('Foto Roma', 'Roma', '', 0);
INSERT INTO db_fotoalbum.photos (description, title, url, visible) VALUES('Foto Parigi', 'Parigi', '', 0);
INSERT INTO db_fotoalbum.photos (description, title, url, visible) VALUES('Foto Amsterdam', 'Amsterdam', '', 0);

INSERT INTO db_fotoalbum.categories (name) VALUES('Eventi');
INSERT INTO db_fotoalbum.categories (name) VALUES('Ritratto');
INSERT INTO db_fotoalbum.categories (name) VALUES('Arte');
INSERT INTO db_fotoalbum.categories (name) VALUES('Moda');
INSERT INTO db_fotoalbum.categories (name) VALUES('Viaggio');

INSERT INTO db_fotoalbum.category_photo (photo_id, category_id) VALUES(1, 5);
INSERT INTO db_fotoalbum.category_photo (photo_id, category_id) VALUES(2, 5);
INSERT INTO db_fotoalbum.category_photo (photo_id, category_id) VALUES(3, 5);

INSERT INTO users (email, first_name, last_name, password) VALUES('mattia@gmail.com', 'Mattia', 'Li Causi', '{noop}mattia');

INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'USER');

INSERT into users_roles(user_id, roles_id) VALUES(1, 1);