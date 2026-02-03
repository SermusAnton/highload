CREATE TABLE post (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    user_id uuid,
    text  character varying(1000),
    is_deleted boolean DEFAULT FALSE,
    CONSTRAINT fk_users_id_post_user_id
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

ALTER TABLE ONLY post
    ADD CONSTRAINT pk_post PRIMARY KEY (id);