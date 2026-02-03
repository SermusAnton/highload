CREATE TABLE friend (
    user_id uuid,
    friend_id uuid NOT NULL,
    CONSTRAINT fk_users_id_friend_user_id
        FOREIGN KEY (user_id)
        REFERENCES users(id),
    CONSTRAINT  fk_users_id_friend_friend_id
        FOREIGN KEY (friend_id)
        REFERENCES users(id)
);

ALTER TABLE ONLY friend
    ADD CONSTRAINT pk_friend PRIMARY KEY (user_id, friend_id);