plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.der3.navigation"
    compileSdk = BuildVersions.COMPILE_SDK

    defaultConfig {
        minSdk = BuildVersions.MIN_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    compileOptions {
        sourceCompatibility =
            JavaVersion.toVersion(BuildVersions.JAVA_VERSION)
        targetCompatibility =
            JavaVersion.toVersion(BuildVersions.JAVA_VERSION)
    }

    kotlinOptions {
        jvmTarget = BuildVersions.JAVA_VERSION.toString()
    }
}

dependencies {
    // AndroidX & Core Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Compose Core
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.saveable)

    //Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)


    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(project(path = ":screens"))
    implementation(project(path = ":features:splash"))
    implementation(project(path = ":features:on_boarding"))
    implementation(project(path = ":features:home"))
}