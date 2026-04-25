plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility =   JavaVersion.toVersion(BuildVersions.JAVA_VERSION)
    targetCompatibility =   JavaVersion.toVersion(BuildVersions.JAVA_VERSION)
}

kotlin {
    compilerOptions {
        jvmTarget.set(
            org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(
                BuildVersions.JAVA_VERSION.toString()
            )
        )
    }

}