FROM postgres:15.14-alpine

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
ENV TEST_SERVICE=pgmaster:5432
ENV WAIT_FOR_IT_DOWNLOAD_URL https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh

# Копируем скрипт для задержки запуска контейнера
RUN apk --no-cache add curl && \
    curl -L --silent ${WAIT_FOR_IT_DOWNLOAD_URL} > /usr/local/bin/wait-for-it.sh && \
    chmod +x /usr/local/bin/wait-for-it.sh

COPY --chown=postgres:postgres ./config/postgresql.conf /etc/postgresql/postgresql.conf

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

USER postgres

ENTRYPOINT ["/entrypoint.sh"]