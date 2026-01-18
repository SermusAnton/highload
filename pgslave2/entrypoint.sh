#!/bin/bash

# Ожидаем запуск тестируемого сервиса (в секундах)
/usr/local/bin/wait-for-it.sh $TEST_SERVICE --timeout=30 --strict -- echo "$TEST_SERVICE is up!"

export PGPASSWORD="pass"
pg_basebackup -h pgmaster -U replicator -D /var/lib/postgresql/data -v -P --wal-method=stream
unset PGPASSWORD
touch /var/lib/postgresql/data/standby.signal

chmod -R 750 /var/lib/postgresql/data
postgres -c config_file=/etc/postgresql/postgresql.conf