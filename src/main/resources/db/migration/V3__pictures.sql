create table pictures
(
    thing_id     bigint       not null,
    data         mediumblob   not null,
    content_type varchar(255) not null,

    constraint fk_thing foreign key (thing_id) references things (id)
);