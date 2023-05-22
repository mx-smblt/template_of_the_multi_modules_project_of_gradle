
plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
    implementation("com.github.jk1:gradle-license-report:2.2")
    implementation("org.jetbrains.kotlinx:kover:0.6.1")
    implementation("com.palantir.gradle.gitversion:gradle-git-version:3.0.0")
    implementation("com.bmuschko:gradle-docker-plugin:9.3.1")
    implementation("com.gorylenko.gradle-git-properties:gradle-git-properties:2.4.1")
}
