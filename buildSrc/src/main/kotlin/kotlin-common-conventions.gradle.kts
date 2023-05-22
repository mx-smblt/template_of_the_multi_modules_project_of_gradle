import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import kotlinx.kover.api.KoverTaskExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")

    id("detekt-convention")
    id("org.jetbrains.kotlinx.kover")
}

tasks {
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
        extensions.configure(KoverTaskExtension::class) {
            includes.addAll("io.github.dream.*")
        }
    }
}

configure<DetektExtension> {
    source = project.files("src/main/kotlin", "src/test/kotlin")
}
