create table things
(
    id          bigint auto_increment primary key,
    type        varchar(255) null,
    description varchar(255) null,
    updated     datetime(6)  null,
    location_id bigint       null,

    constraint fk_location foreign key (location_id) references locations (id)
);