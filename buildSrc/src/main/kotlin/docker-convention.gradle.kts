import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import com.bmuschko.gradle.docker.tasks.image.DockerPushImage
import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import com.palantir.gradle.gitversion.VersionDetails
import org.apache.tools.ant.filters.ReplaceTokens

/**
 * artifactName
 * DOCKER_REGISTRY_ACCOUNT
 * DOCKER_REGISTRY_URL
 * DOCKER_REGISTRY_USERNAME
 * DOCKER_REGISTRY_PASSWORD
 */

plugins {
    kotlin("jvm")
    id("com.palantir.git-version")
    id("com.bmuschko.docker-remote-api")
    id("com.gorylenko.gradle-git-properties")
}

tasks {
    val artifactName: String by extra
    val abbrev = gitHash()
    val dockerRegistry = System.getProperty("DOCKER_REGISTRY_ACCOUNT")?.lowercase() ?: "local"
    val imageName = "$dockerRegistry/${rootProject.name}:${project.version}.${abbrev}"
    val workingDir = "app"

    val dockerFileCopy = register<Copy>("dockerFileCopy") {
        dependsOn(build)
        group = "docker"
        destinationDir = file("$buildDir/docker")

        from("${projectDir}/src/main/resources/logback.xml") {
            into("config")
            filter<ReplaceTokens>(
                "tokens" to mapOf(
                    "appName" to project.name,
                    "appVersion" to "${project.version}.${abbrev}"
                )
            )
        }

        from("${projectDir}/src/main/resources/application.yml") {
            into("config")
            filter<ReplaceTokens>(
                "tokens" to mapOf(
                    "appVersion" to "${project.version}.${abbrev}"
                )
            )
        }

        from("$buildDir/libs/${artifactName}") {
            into("app")
        }
    }

    val createDockerfile = register<Dockerfile>("dockerCreateDockerfile") {
        dependsOn(dockerFileCopy)
        group = "docker"
        destFile.set(project.file("build/docker/Dockerfile"))
        from("azul/zulu-openjdk-alpine:17.0.7-jre")
        runCommand("mkdir $workingDir && mkdir ${workingDir}/config")
        copyFile("config/logback.xml", "${workingDir}/config/")
        copyFile("config/application.yml", "${workingDir}/config/")
        copyFile("${workingDir}/${artifactName}", "${workingDir}/${artifactName}")
        exposePort(8080)
        workingDir(workingDir)
        instruction("CMD java \${JAVA_OPTS} -Dlogging.config=config/logback.xml -Dspring.config.location=file:config/application.yml -jar $artifactName")
    }

    val buildImage = register<DockerBuildImage>("dockerBuildImage") {
        dependsOn(createDockerfile)
        group = "docker"
        inputDir.set(createDockerfile.get().destFile.get().asFile.parentFile)
        images.add(imageName)
    }

    val dockerRegistryUrl = System.getProperty("DOCKER_REGISTRY_URL")
    val dockerRegistryUsername = System.getProperty("DOCKER_REGISTRY_USERNAME")
    val dockerRegistryPassword = System.getProperty("DOCKER_REGISTRY_PASSWORD")

    register<DockerPushImage>("dockerPushImage") {
        dependsOn(buildImage)
        group = "docker"
        images.add(imageName)
        registryCredentials {
            url.set(dockerRegistryUrl)
            username.set(dockerRegistryUsername)
            password.set(dockerRegistryPassword)
        }
    }
}

gitProperties {
    keys = listOf("git.commit.id.abbrev")
}

fun gitHash(): String {
    fun getVersionDetails(): VersionDetails {
        val versionDetails = (extra["versionDetails"] as? groovy.lang.Closure<*>)
            ?: throw IllegalStateException("versionDetails not found.")
        return versionDetails() as VersionDetails
    }

    val versionDetails = getVersionDetails()
    val hash = versionDetails.gitHash
    return hash.substring(0, 7)
}
