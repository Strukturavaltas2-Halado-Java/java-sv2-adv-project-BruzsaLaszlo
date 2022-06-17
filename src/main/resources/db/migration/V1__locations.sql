create table locations
(
    id   bigint auto_increment primary key,
    name varchar(255) not null,
    room varchar(255) not null,
    info varchar(255) null
);