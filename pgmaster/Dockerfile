FROM postgres:15.14-alpine

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB highload

COPY --chown=postgres:postgres ./config/postgresql.conf /etc/postgresql/postgresql.conf
COPY --chown=postgres:postgres ./config/pg_hba.conf /etc/postgresql/pg_hba.conf

COPY ./init-scripts/ /docker-entrypoint-initdb.d/

USER postgres

CMD ["postgres", \
     "-c", "config_file=/etc/postgresql/postgresql.conf", \
     "-c", "hba_file=/etc/postgresql/pg_hba.conf"]
