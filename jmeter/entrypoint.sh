#!/usr/bin/env sh

# Ожидаем запуск тестируемого сервиса 10 секунд
/usr/local/bin/wait-for-it.sh $TEST_SERVICE --timeout=20 --strict -- echo "$TEST_SERVICE is up!"

jmeter -n -t $PATH_TO_TEST_FILE

exec "$@"