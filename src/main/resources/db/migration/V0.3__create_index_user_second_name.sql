CREATE EXTENSION pg_trgm;

CREATE INDEX backend_users_second_name_idx ON users USING gin(second_name gin_trgm_ops);