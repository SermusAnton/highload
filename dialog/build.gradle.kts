plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "com.highload.dialog"
version = "0.0.1-SNAPSHOT"
description = "Highload backend project for Spring Boot"

repositories {
    mavenCentral()
}

var springdocVersion = "2.8.13"
var swaggerVersion = "2.2.40"
var jakartaValidationVersion = "3.1.1"
var springSecurityCryptoVersion = "7.0.0-M3"
var springCloudVersion = "2024.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.security:spring-security-crypto:$springSecurityCryptoVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("io.swagger.core.v3:swagger-annotations:$swaggerVersion")
    implementation("jakarta.validation:jakarta.validation-api:$jakartaValidationVersion")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("io.github.openfeign:feign-micrometer")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")

    implementation("io.micrometer:context-propagation")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        // Версия 2023.0.3 подходит для Spring Boot 3.2.x и 3.3.x
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

