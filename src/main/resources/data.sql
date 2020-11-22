insert into role (id, name) values (1, 'USER');
insert into role (id, name) values (2, 'ADMIN');

insert into user (id, username, password, enabled) values (1, 'admin', '$2y$12$XDnXygibdPOhR5w2oFJJjescx683buzIUSGiPEAV128qW//v6RRlS', true);
insert into user (id, username, password, enabled) values (2, 'user', '$2y$12$3ltSMVTCUjOR40t.tZaQ..8MEfV6BC/iA28GmoKB8ABAHPi954nKq', true);

insert into user_role (id, id_user, id_role) values (1, 2, 2);
insert into user_role (id, id_user, id_role) values (2, 1, 1);