# highload
Highload Architect

CREATE USER backend WITH PASSWORD 'backend';
CREATE DATABASE highload OWNER = backend ENCODING = 'UTF8';
CREATE SCHEMA backend AUTHORIZATION backend;
