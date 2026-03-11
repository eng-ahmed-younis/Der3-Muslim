plugins {
    alias(libs.plugins.android.application)
 //   alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)

 //   alias(libs.plugins.kotlin.kapt)
}

android {

    namespace = "com.der3.muslim"
    compileSdk = BuildVersions.COMPILE_SDK

    defaultConfig {
        applicationId = "com.der3.muslim"
        minSdk = BuildVersions.MIN_SDK
        targetSdk = BuildVersions.TARGET_SDK
        versionCode = BuildVersions.VERSION_CODE
        versionName = BuildVersions.VERSION_NAME

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    //noinspection WrongGradleMethod
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }



    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.savedstate)
    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))


    // Firebase BoM
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)

    // Compose UI
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Testing
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
  //  androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Debug
    debugImplementation(libs.androidx.compose.ui.tooling)
//    debugImplementation(libs.androidx.compose.ui.test.manifest)


    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
   // kapt(libs.hilt.compiler)

    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    implementation(project(path = ":core:ui"))
    implementation(project(path = ":core:utils"))
    implementation(project(path = ":core:ui-model"))
    implementation(project(path = ":core:player"))
    implementation(project(path = ":features:splash"))
    implementation(project(path = ":navigation"))
    implementation(project(path = ":screens"))

}
