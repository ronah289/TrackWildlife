CREATE DATABASE wildlife_tracker;
\connect wildlife_tracker
CREATE TABLE animals (
    id integer NOT NULL,
    name character varying,
    type character varying,
    health character varying,
    age character varying
);
CREATE TABLE locations (
    id integer NOT NULL,
    name character varying
);
CREATE TABLE locations_sightings (
    id integer NOT NULL,
    location_id integer,
    sighting_id integer
);
CREATE TABLE rangers (
    id integer NOT NULL,
    name character varying,
    staff_number integer
);
CREATE TABLE rangers_sightings (
    id integer NOT NULL,
    ranger_id integer,
    sighting_id integer
);
CREATE TABLE sightings (
    id integer NOT NULL,
    animal_id integer,
    ranger_id integer,
    location_id integer,
    time timestamp without time zone
);

