FROM alpine:3.23.2

ARG JMETER_VERSION="5.6.3"
ENV JMETER_HOME /opt/apache-jmeter-${JMETER_VERSION}
ENV JMETER_BIN ${JMETER_HOME}/bin
ENV JMETER_DOWNLOAD_URL https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-${JMETER_VERSION}.tgz
ARG JMETER_PROMETHEUS_VERSION="0.6.0"
ARG TEST_FILE="test2.jmx"
ENV WAIT_FOR_IT_DOWNLOAD_URL https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh

# Install necessary packages: openjdk for Java, curl to download, and bash to run scripts
RUN apk update && apk upgrade && \
    apk add openjdk17-jre-headless bash curl unzip && \
    rm -rf /var/cache/apk/* && \
    mkdir -p /tmp/dependencies && \
    apk add unzip

# Download and extract JMeter
RUN curl -L --silent ${JMETER_DOWNLOAD_URL} > /tmp/dependencies/apache-jmeter-${JMETER_VERSION}.tgz && \
    tar -xzf /tmp/dependencies/apache-jmeter-${JMETER_VERSION}.tgz -C /opt && \
    rm -rf /tmp/dependencies

COPY config plugin scripts ${JMETER_HOME}

RUN unzip ${JMETER_HOME}/jmeter-prometheus-${JMETER_PROMETHEUS_VERSION}.zip -d ${JMETER_HOME} && \
    rm -rf ${JMETER_HOME}/jmeter-prometheus-${JMETER_PROMETHEUS_VERSION}.zip && \
    cp ${JMETER_HOME}/${TEST_FILE} ${JMETER_BIN} && \
    cp -f ${JMETER_HOME}/jmeter.properties ${JMETER_BIN}

# Set global PATH such that "jmeter" command is found
ENV PATH $PATH:$JMETER_BIN

ENV PATH_TO_TEST_FILE=${JMETER_HOME}/${TEST_FILE}

ENV TEST_SERVICE=backend:8080

# Копируем скрипт для задержки запуска контейнера
RUN curl -L --silent ${WAIT_FOR_IT_DOWNLOAD_URL} > /usr/local/bin/wait-for-it.sh && \
    chmod +x /usr/local/bin/wait-for-it.sh

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Define the entry point for the container to run a JMeter test in non-GUI mode
ENTRYPOINT ["/entrypoint.sh"]