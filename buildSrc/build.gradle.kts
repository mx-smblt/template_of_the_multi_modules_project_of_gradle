
plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")

    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.0")
    implementation("org.jetbrains.kotlinx:kover-gradle-plugin:0.7.2")

    implementation("com.bmuschko:gradle-docker-plugin:9.3.1")

    implementation("com.github.jk1:gradle-license-report:2.2")
    implementation("com.palantir.gradle.gitversion:gradle-git-version:3.0.0")
    implementation("com.gorylenko.gradle-git-properties:gradle-git-properties:2.4.1")
}
