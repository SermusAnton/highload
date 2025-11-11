# highload
Highload Architect

БД PostgresQL, по-умолчанию: localhost:5432
Для запуска необходимо подготовить БД:
1. Создать пользователя с паролем
CREATE USER backend WITH PASSWORD 'backend';
2. Создать БД
CREATE DATABASE highload OWNER = backend ENCODING = 'UTF8';
3. Создать схему 
CREATE SCHEMA backend AUTHORIZATION backend;

Postman коллекция \json\OTUS Highload Architect.postman_collection.json
