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
4. Загрузить тестовые данные src/main/resources/db/migration/data/people.v2.csv 
в бд highload, схема backend, таблица users
5. Выполнить из каталога проекта 
docker compose up
6. Зайти в grafana (admin:admin) Dashboards -> JMeter

Postman коллекция \json\OTUS Highload Architect.postman_collection.json
