import org.gradle.api.JavaVersion

internal object Versions {
    object JVM {
        val target: String
            get() = System.getenv("JAVA_VERSION") ?: "17"

        val compatibility: JavaVersion
            get() = JavaVersion.toVersion(target)
    }
}
