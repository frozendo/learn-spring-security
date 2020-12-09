insert into role (id, name) values (1, 'ADMIN');
insert into role (id, name) values (2, 'STUDENT');
insert into role (id, name) values (3, 'TEACHER');
insert into role (id, name) values (4, 'COORDINATOR');
insert into role (id, name) values (5, 'REPRESENTATIVE');

insert into user (id, username, password, enabled) values (1, 'admin', '{bcrypt}$2y$12$S1V19apCtHS7sX.SpZPV/OGndI/3LjkWD.xpZ4qwUpeZrMIB9G9Xu', true);
insert into user (id, username, password, enabled) values (2, 'elliot', '{bcrypt}$2y$12$iVvyY1u8cPCaJvDD0XYDzun6jshPg1hv4b7it.I5wTh9TaiGcyZxm', true);
insert into user (id, username, password, enabled) values (3, 'ellen', '{bcrypt}$2y$12$I6bbtvVgtCZxCiBihZimcOCIHFxleK.4mMMhn0QtNeiM6pqz/CWwq', true);
insert into user (id, username, password, enabled) values (4, 'roger', '{bcrypt}$2y$12$r4gQr4KMGYQ4DpJzf5NGT.wc4HRQL4Veiyl9RX/snVWHyMVEF0TH6', true);
insert into user (id, username, password, enabled) values (5, 'mary', '{bcrypt}$2y$12$V9dc0ui0ujEeZUcj/rCwDOGFXVAT57qui6jWmxuIPJeX/uoDwg8hi', true);
insert into user (id, username, password, enabled) values (6, 'carlos', '{bcrypt}$2y$12$i1ph1ICUp6DEDnapmqvTruDIh3RTO69UUYtjzKuTJ1v.9ayvu6cd.', true);
insert into user (id, username, password, enabled) values (7, 'michelle', '{bcrypt}$2y$12$EpeNP6hWsHV4SxZ4JOfxLOLFz/e8vAk1gYqjlkNa9yHvYzGxGatDi', true);
insert into user (id, username, password, enabled) values (8, 'frodo', '{bcrypt}$2y$12$iVvyY1u8cPCaJvDD0XYDzun6jshPg1hv4b7it.I5wTh9TaiGcyZxm', true);
insert into user (id, username, password, enabled) values (9, 'galadriel', '{bcrypt}$2y$12$I6bbtvVgtCZxCiBihZimcOCIHFxleK.4mMMhn0QtNeiM6pqz/CWwq', true);

insert into user_role (id, id_user, id_role) values (1, 1, 1);
insert into user_role (id, id_user, id_role) values (2, 2, 2);
insert into user_role (id, id_user, id_role) values (3, 2, 5);
insert into user_role (id, id_user, id_role) values (4, 3, 2);
insert into user_role (id, id_user, id_role) values (5, 4, 3);
insert into user_role (id, id_user, id_role) values (6, 5, 3);
insert into user_role (id, id_user, id_role) values (7, 6, 4);
insert into user_role (id, id_user, id_role) values (8, 7, 4);
insert into user_role (id, id_user, id_role) values (9, 8, 2);
insert into user_role (id, id_user, id_role) values (10, 8, 5);
insert into user_role (id, id_user, id_role) values (11, 9, 2);

insert into student (id, name, birthdate) values (1, 'Elliot', parsedatetime('10/10/2012', 'dd/MM/yyyy'));
insert into student (id, name, birthdate) values (2, 'Ellen', parsedatetime('08/08/2012', 'dd/MM/yyyy'));
insert into student (id, name, birthdate) values (3, 'Frodo', parsedatetime('07/07/2012', 'dd/MM/yyyy'));
insert into student (id, name, birthdate) values (4, 'Galadriel', parsedatetime('02/02/2012', 'dd/MM/yyyy'));

insert into teacher (id, name, subject) values (1, 'Roger', 'Math');
insert into teacher (id, name, subject) values (2, 'Mary', 'English');

insert into class (id, name, year) values (1, '8A', 2020);
insert into class (id, name, year) values (2, '9A', 2020);

insert into class_student(id, id_student, id_class) values (1, 1, 1);
insert into class_student(id, id_student, id_class) values (2, 2, 1);
insert into class_student(id, id_student, id_class) values (3, 3, 2);
insert into class_student(id, id_student, id_class) values (4, 4, 2);

insert into class_teacher (id, id_teacher, id_class) values (1, 1, 1);
insert into class_teacher (id, id_teacher, id_class) values (2, 2, 1);
insert into class_teacher (id, id_teacher, id_class) values (3, 1, 2);
insert into class_teacher (id, id_teacher, id_class) values (4, 2, 2);

insert into grade (id, grade_value, id_class_student, id_class_teacher) values (1, 'A', 1, 1);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (2, 'B', 1, 2);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (3, 'C', 2, 1);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (4, 'A', 2, 2);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (5, 'B', 3, 1);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (6, 'C', 3, 2);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (7, 'A', 4, 1);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (8, 'B', 4, 2);