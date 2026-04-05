plugins {
    java
    id("org.springframework.boot") version "3.5.7" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("org.flywaydb.flyway") version "11.20.3" apply false
    id("org.jooq.jooq-codegen-gradle") version "3.19.27" apply false
}

// Общие настройки для всех подмодулей
subprojects {
    repositories {
        mavenCentral()
    }
}

group = "com.highload"
version = "0.0.1-SNAPSHOT"
description = "Highload backend project for Spring Boot"