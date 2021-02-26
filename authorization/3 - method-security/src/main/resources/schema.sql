create table role (
    id int not null primary key,
    name varchar(100) not null
);

create table user (
    id int not null primary key,
    username varchar(50) not null,
    password varchar(500) not null,
    enabled boolean not null
);

create table user_role (
    id int not null primary key,
    id_role int not null,
    id_user int not null,
    constraint fk_role_user_role foreign key(id_role) references role(id),
    constraint fk_user_user_role foreign key(id_user) references user(id)
);

create table student (
    id int not null primary key,
    name varchar(100) not null,
    birthdate date not null,
    id_user int not null
);

create table teacher (
    id int not null primary key,
    name varchar(100) not null,
    subject varchar(100) not null,
    id_user int not null
);

create table class (
    id int not null primary key,
    name varchar(100) not null,
    year int not null
);

create table class_student (
    id int not null primary key,
    id_student int not null,
    id_class int not null,
    constraint fk_student_class_student foreign key(id_student) references student(id),
    constraint fk_class_class_student foreign key(id_class) references class(id)
);

create table class_teacher (
    id int not null primary key,
    id_teacher int not null,
    id_class int not null,
    constraint fk_teacher_class_teacher foreign key(id_teacher) references teacher(id),
    constraint fk_class_class_teacher foreign key(id_class) references class(id)
);

create table grade (
    id int not null primary key,
    grade_value varchar(10) not null,
    id_class_student int not null,
    id_class_teacher int not null,
    constraint fk_class_student_grade foreign key(id_class_student) references class_student(id),
    constraint fk_class_teacher_grade foreign key(id_class_teacher) references class_teacher(id)
);

create index ix_role_user_role on user_role (id_role);
create index ix_user_user_role on user_role (id_user);
create index ix_student_class_student on class_student (id_student);
create index ix_class_class_student on class_student (id_class);
create index ix_teacher_class_teacher on class_teacher (id_teacher);
create index ix_class_class_teacher on class_teacher (id_class);
create index ix_class_student_grade on grade (id_class_student);
create index ix_class_teacher_grade on grade (id_class_teacher);