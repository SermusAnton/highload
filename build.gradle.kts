plugins {
	java
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
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

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.flywaydb:flyway-core")
	implementation("org.postgresql:postgresql:$postgresqlDriverVersion")
	runtimeOnly("org.flywaydb:flyway-database-postgresql:$flywayDatabasePostgresqlVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
