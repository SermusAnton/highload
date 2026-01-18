#!/usr/bin/env sh

# Ожидаем запуск тестируемого сервиса (в секундах)
/usr/local/bin/wait-for-it.sh $TEST_SERVICE --timeout=60 --strict -- echo "$TEST_SERVICE is up!"

jmeter -n -t $PATH_TO_TEST_FILE

exec "$@"