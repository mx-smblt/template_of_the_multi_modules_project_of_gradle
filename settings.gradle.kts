rootProject.name = "project-template"

include(":module-one")
project(":module-one").projectDir = file("./module-one")

include(":application")
project(":application").projectDir = file("./application")


dependencyResolutionManagement {
    versionCatalogs {
        create("testLibs") {
            from(files("gradle/test-libs.versions.toml"))
        }
    }
}
