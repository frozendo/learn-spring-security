insert into role (id, name) values (1, 'ADMIN');
insert into role (id, name) values (2, 'STUDENT');
insert into role (id, name) values (3, 'TEACHER');

insert into user (id, email) values (1, 'admin@test.com');
insert into user (id, email) values (2, 'jessica.galdes18@gmail.com');
insert into user (id, email) values (3, 'flavio.ap.rozendo@gmail.com');

insert into user_role (id, id_user, id_role) values (1, 1, 1);
insert into user_role (id, id_user, id_role) values (2, 2, 3);
insert into user_role (id, id_user, id_role) values (3, 3, 2);