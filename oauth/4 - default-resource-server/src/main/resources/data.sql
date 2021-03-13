insert into role (id, name) values (1, 'ADMIN');
insert into role (id, name) values (2, 'STUDENT');
insert into role (id, name) values (3, 'TEACHER');

insert into user (id, email) values (1, 'admin@test.com');
insert into user (id, email) values (2, 'galadriel@elf.com');
insert into user (id, email) values (3, 'gandalf@wizard.com');
insert into user (id, email) values (4, 'saruman@wizard.com');
insert into user (id, email) values (5, 'elrold@elf.com');
insert into user (id, email) values (6, 'mary@condado.com');
insert into user (id, email) values (7, 'pippen@condado.com');
insert into user (id, email) values (8, 'frodo@condado.com');
insert into user (id, email) values (9, 'sam@condado.com');

insert into user_role (id, id_user, id_role) values (1, 1, 1);
insert into user_role (id, id_user, id_role) values (2, 2, 3);
insert into user_role (id, id_user, id_role) values (3, 3, 3);
insert into user_role (id, id_user, id_role) values (4, 4, 3);
insert into user_role (id, id_user, id_role) values (5, 5, 3);
insert into user_role (id, id_user, id_role) values (6, 6, 2);
insert into user_role (id, id_user, id_role) values (7, 7, 2);
insert into user_role (id, id_user, id_role) values (8, 8, 2);
insert into user_role (id, id_user, id_role) values (9, 9, 2);

insert into student (id, name, birthdate, id_user) values (1, 'Mary', parsedatetime('10/10/2012', 'dd/MM/yyyy'), 6);
insert into student (id, name, birthdate, id_user) values (2, 'Pippen', parsedatetime('08/08/2012', 'dd/MM/yyyy'), 7);
insert into student (id, name, birthdate, id_user) values (3, 'Frodo', parsedatetime('07/07/2012', 'dd/MM/yyyy'), 8);
insert into student (id, name, birthdate, id_user) values (4, 'Sam', parsedatetime('02/02/2012', 'dd/MM/yyyy'), 9);

insert into teacher (id, name, subject, id_user) values (1, 'Galadriel', 'Math', 2);
insert into teacher (id, name, subject, id_user) values (2, 'Gandalf', 'English', 3);
insert into teacher (id, name, subject, id_user) values (3, 'Saruman', 'Math', 4);
insert into teacher (id, name, subject, id_user) values (4, 'Elrond', 'English', 5);

insert into class (id, name, year) values (1, '8A', 2020);
insert into class (id, name, year) values (2, '9A', 2020);

insert into class_student(id, id_student, id_class) values (1, 1, 1);
insert into class_student(id, id_student, id_class) values (2, 2, 1);
insert into class_student(id, id_student, id_class) values (3, 3, 2);
insert into class_student(id, id_student, id_class) values (4, 4, 2);

insert into class_teacher (id, id_teacher, id_class) values (1, 1, 1);
insert into class_teacher (id, id_teacher, id_class) values (2, 2, 1);
insert into class_teacher (id, id_teacher, id_class) values (3, 3, 2);
insert into class_teacher (id, id_teacher, id_class) values (4, 4, 2);

insert into grade (id, grade_value, id_class_student, id_class_teacher) values (1, 'A', 1, 1);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (2, 'C', 1, 2);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (3, 'B', 2, 1);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (4, 'B', 2, 2);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (5, 'C', 3, 3);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (6, 'A', 3, 4);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (7, 'D', 4, 3);
insert into grade (id, grade_value, id_class_student, id_class_teacher) values (8, 'C', 4, 4);