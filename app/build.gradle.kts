plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
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
        sourceCompatibility =
            JavaVersion.toVersion(BuildVersions.JAVA_VERSION)
        targetCompatibility =
            JavaVersion.toVersion(BuildVersions.JAVA_VERSION)
    }

    kotlinOptions {
        jvmTarget = BuildVersions.JAVA_VERSION.toString()
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

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))

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
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Debug
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)


    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)


    implementation(project(path = ":core:ui"))
    implementation(project(path = ":core:ui-model"))

}
