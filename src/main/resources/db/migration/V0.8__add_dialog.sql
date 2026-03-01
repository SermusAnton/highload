CREATE TABLE dialog (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    from_user_id uuid NOT NULL,
    to_user_id uuid NOT NULL,
    sum_user_hash bigint NOT NULL,
    text  character varying(2000),
    create_time timestamp NOT NULL DEFAULT now()
);