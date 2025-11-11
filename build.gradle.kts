plugins {
    java
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
    id ("org.flywaydb.flyway") version "11.7.2"
    id("org.jooq.jooq-codegen-gradle") version "3.19.27"
}

group = "com.highload"
version = "0.0.1-SNAPSHOT"
description = "Highload backend project for Spring Boot"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

var postgresqlDriverVersion = "42.7.8"
var flywayDatabasePostgresqlVersion = "11.7.2"
var springdocVersion = "2.8.13"
var swaggerVersion = "2.2.40"
var jakartaValidationVersion = "3.1.1"
var springSecurityCryptoVersion = "7.0.0-M3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.security:spring-security-crypto:$springSecurityCryptoVersion")
    implementation("org.postgresql:postgresql:$postgresqlDriverVersion")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("io.swagger.core.v3:swagger-annotations:$swaggerVersion")
    implementation("jakarta.validation:jakarta.validation-api:$jakartaValidationVersion")
    jooqCodegen("org.jooq:jooq-codegen")
    jooqCodegen("org.jooq:jooq")
    jooqCodegen("org.postgresql:postgresql:$postgresqlDriverVersion")
    implementation("org.bitbucket.b_c:jose4j:0.9.6")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

fun propValue(propName: String, defValue: String): String {
    val envPropValue = System.getenv(propName)
    if (envPropValue != null) {
        return envPropValue
    } else if (project.hasProperty(propName)) {
        return project.properties[propName].toString()
    }
    return defValue
}

val dbHost = propValue ("DB_HOST","localhost")
val dbPort = propValue ("DB_PORT","5432")
val dbUser = propValue ("DB_USER","backend")
val dbPass = propValue ("DB_PASSWORD","backend")
val dbName = propValue ("DB_NAME","highload")
val dbSchema = propValue ("DB_SCHEMA","backend")

val postgresDb = propValue ("POSTGRES_DB", dbName)
val postgresConString = propValue ("POSTGRES_CONNECTION_STRING",
    "jdbc:postgresql://$dbHost:$dbPort/$postgresDb")
val postgresUser = propValue ("POSTGRES_USER", dbUser)
val postgresPassword = propValue ("POSTGRES_PASSWORD", dbPass)

tasks.register ("printProperties") {
    println("DB_HOST -> $dbHost")
    println("DB_PORT -> $dbPort")
    println("DB_USER -> $dbUser")
    println("DB_PASSWORD -> $dbPass")
    println("DB_NAME -> $dbName")
    println("DB_SCHEMA -> $dbSchema")
    println("VERSION -> " + getRootProject().version)
    println("----------------------------------------------")
    println("POSTGRES_CONNECTION_STRING -> $postgresConString")
    println("POSTGRES_USER -> $postgresUser")
    println("POSTGRES_PASSWORD -> $postgresPassword")
    println("POSTGRES_DB -> $postgresDb")
}

// Без этого не работает flyway
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:11.7.2")
    }
}

flyway {
    url = "jdbc:postgresql://localhost:5432/highload"
    user = "backend"
    password = "backend"
    schemas = arrayOf("backend")
    locations = arrayOf("filesystem:src/main/resources/db/migration")
    cleanDisabled = false
}

// Конфигурация jOOQ
jooq {
    configuration {
        jdbc {
            driver = "org.postgresql.Driver"
            url = postgresConString
            user = postgresUser
            password = postgresPassword
        }
        generator {
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = dbSchema
                includes = ".*" // Include all tables
                excludes = "flyway_schema_history|pg_stat_statements"
            }
            target {
                packageName = "com.highload.backend.model.generated"
                directory = "src/main/java"
            }
        }
    }
}


tasks.named("jooqCodegen") {
    dependsOn("flywayMigrate")
}