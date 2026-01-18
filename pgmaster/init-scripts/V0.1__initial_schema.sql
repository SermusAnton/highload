CREATE USER backend WITH PASSWORD 'backend';

CREATE EXTENSION IF NOT EXISTS dblink;

DO $$
BEGIN
    PERFORM dblink_exec('', 'CREATE DATABASE highload');
EXCEPTION
    WHEN duplicate_database THEN
        RAISE NOTICE 'Database highload already exists, skipping creation.';
END
$$;

ALTER DATABASE highload OWNER TO backend;

CREATE SCHEMA backend AUTHORIZATION backend;

GRANT pg_read_server_files TO backend;

CREATE ROLE replicator WITH LOGIN replication PASSWORD 'pass';

GRANT USAGE ON SCHEMA backend TO replicator;

GRANT SELECT ON ALL TABLES IN SCHEMA backend TO replicator;