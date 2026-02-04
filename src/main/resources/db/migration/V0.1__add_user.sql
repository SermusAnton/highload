CREATE TABLE users (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    first_name character varying(100) NOT NULL,
    second_name character varying(100) NOT NULL,
    birthdate date,
    biography character varying(1000),
    city character varying(100) NOT NULL,
    password_hash character varying(100)
);

ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (id);