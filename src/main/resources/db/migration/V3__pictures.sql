create table pictures
(
    thing_id bigint   not null,
    pictures tinyblob not null,

    constraint fk_thing foreign key (thing_id) references things (id)
);