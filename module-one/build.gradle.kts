plugins {
    id("kotlin-library-conventions")
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {

    /* Test */
    testImplementation(testLibs.bundles.kotest)
    testImplementation(testLibs.bundles.mockito)
}
