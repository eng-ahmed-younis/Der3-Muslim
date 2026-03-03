plugins {
    id("java-library")
   // alias(libs.plugins.ksp)
   // alias(libs.plugins.jetbrains.kotlin.jvm) // uncomment this
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}