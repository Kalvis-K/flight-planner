--liquibase formatted sql

--changeset kalvis:2

CREATE TABLE flight
(
    id             serial primary key,
    from_id        int not null,
    to_id          int not null,
    carrier        varchar not null,
    departure_time timestamp not null,
    arrival_time   timestamp not null,
    CONSTRAINT flight_from_id_fk FOREIGN KEY (from_id) references airport (id),
    CONSTRAINT flight_to_id_fk FOREIGN KEY (to_id) references airport (id)
);