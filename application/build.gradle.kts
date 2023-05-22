plugins {
    id("kotlin-application-conventions")

    kotlin("plugin.spring") version "1.8.20"
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
}

repositories {
    mavenCentral()
}

val jacksonVersion by extra { "2.13.3" }

dependencies {

    /* Kotlin */
    implementation(libs.coroutines.core)

    /* Spring */
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    /* ***** Libs ***** */
    /* Jackson */
    implementation(libs.bundles.jackson) {
        exclude(group = "org.jetbrains.kotlin")
    }

    /* Logging */
    implementation(libs.bundles.logging)

    /* Database */
//    runtimeOnly("org.postgresql:postgresql")
//    implementation("org.liquibase:liquibase-core")

    /* ***** Test ***** */
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(testLibs.bundles.kotest)
    testImplementation(testLibs.bundles.mockito)
    testImplementation(testLibs.bundles.testcontainers.postgres)
}

val artifactName by extra { tasks.bootJar.get().archiveFileName.get() }
