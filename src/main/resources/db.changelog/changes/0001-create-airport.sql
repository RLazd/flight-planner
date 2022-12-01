--liquibase formatted sql

--changeset ram:1

CREATE TABLE airports
(
    id      VARCHAR PRIMARY KEY,
    country VARCHAR NOT NULL,
    city    VARCHAR NOT NULL
);

CREATE TABLE flights
(
    id              VARCHAR PRIMARY KEY,
    from_airport_id VARCHAR NOT NULL,
    FOREIGN KEY (from_airport_id) REFERENCES airports (id),
    to_airport_id   VARCHAR NOT NULL,
    FOREIGN KEY (to_airport_id) REFERENCES airports (id),
    carrier         VARCHAR NOT NULL,
    departure_time  VARCHAR NOT NULL,
    arrival_time    VARCHAR NOT NULL
);
