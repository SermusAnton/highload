CREATE TABLE users (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    firstName character varying(100) NOT NULL,
    secondName character varying(100) NOT NULL,
    birthdate date,
    biography character varying(1000) NOT NULL,
    city character varying(100) NOT NULL,
    passwordHash character varying(100) NOT NULL
);

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (id);