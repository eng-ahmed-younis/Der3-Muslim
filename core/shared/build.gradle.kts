plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    namespace = "com.der3.shared"
    compileSdk = BuildVersions.COMPILE_SDK

    defaultConfig {
        minSdk = BuildVersions.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }

    buildFeatures {
        buildConfig = true
    }

    val aladhanBaseUrl =
        (
            project.findProperty("ALADHAN_BASE_URL")
                ?: project.findProperty("BASE_URL")
                ?: "https://api.aladhan.com/v1/"
        ).toString()

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )

            buildConfigField(
                "String",
                "ALADHAN_BASE_URL",
                "\"$aladhanBaseUrl\"",
            )

            buildConfigField(
                "String",
                "NETWORK_DEBUGGING",
                "\"false\"",
            )
        }

        create("staging") {
            isDefault = true
            //   initWith(getByName("release"))
            // “If this build type (or flavor) doesn’t exist in a dependency, fall back to using release instead.”
            matchingFallbacks += listOf("release")
            // signingConfigs: a container holding all signing configurations
            // assigning the debug signing configuration to something (usually a build type like release or a custom one).
            //    signingConfig = signingConfigs.getByName("debug")

            buildConfigField(
                "String",
                "ALADHAN_BASE_URL",
                "\"$aladhanBaseUrl\"",
            )

            buildConfigField(
                "String",
                "NETWORK_DEBUGGING",
                "\"true\"",
            )
        }

        debug {
            buildConfigField(
                "String",
                "ALADHAN_BASE_URL",
                "\"$aladhanBaseUrl\"",
            )

            buildConfigField(
                "String",
                "NETWORK_DEBUGGING",
                "\"true\"",
            )
        }
    }

    compileOptions {
        sourceCompatibility =
            JavaVersion.toVersion(BuildVersions.JAVA_VERSION)
        targetCompatibility =
            JavaVersion.toVersion(BuildVersions.JAVA_VERSION)
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(
                org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(
                    BuildVersions.JAVA_VERSION.toString(),
                ),
            )
        }
    }
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)

    // Firebase BoM
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(libs.firebase.messaging)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // KotlinX Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.play.services)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio) // CIO engine
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)

    // chucker
    debugImplementation(libs.library)
    add("stagingImplementation", libs.library)
    releaseImplementation(libs.library.no.op)

    // Image loading
    implementation(libs.coil.compose)

    implementation(project(":core:data_store"))
    implementation(project(":core:ui"))
    implementation(project(":core:ui-model"))
}
