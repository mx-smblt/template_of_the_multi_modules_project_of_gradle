import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")

    id("detekt-convention")
    id("org.jetbrains.kotlinx.kover")
}

tasks {

    java {
        sourceCompatibility = Versions.JVM.compatibility
        targetCompatibility = Versions.JVM.compatibility
    }

    withType<KotlinCompile>()
        .configureEach {
            kotlinOptions {
                jvmTarget = Versions.JVM.target
                suppressWarnings = false
                freeCompilerArgs = listOf(
                    "-Xjsr305=strict",
                    "-Xjvm-default=all"
                )
            }
        }

    withType<Test> {
        useJUnitPlatform()
    }
}

configure<DetektExtension> {
    source.setFrom(project.files("src/main/kotlin", "src/test/kotlin"))
}
