create table things
(
    id          bigint auto_increment
        primary key,
    description varchar(255) null,
    picture     mediumblob   null,
    type        varchar(255) null,
    updated     datetime(6)  null,
    room_id     bigint       null,
    constraint fk_room
        foreign key (room_id) references rooms (id)
);