create table user (
    id int not null primary key,
    username varchar_ignorecase(50) not null,
    password varchar_ignorecase(500) not null,
    enabled boolean not null
);

create table role (
    id int not null primary key,
    name varchar_ignorecase(100) not null
);

create table user_role (
    id int not null primary key,
    id_role int not null,
    id_user int not null,
    constraint fk_role_user_role foreign key(id_role) references role(id),
    constraint fk_user_user_role foreign key(id_user) references user(id)
);

create unique index ix_role_user_role on user_role (id_role);
create unique index ix_user_user_role on user_role (id_user);