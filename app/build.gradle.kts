plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace   = "com.example.cinemaapp"
    compileSdk  = 35

    defaultConfig {
        applicationId          = "com.example.cinemaapp"
        minSdk                 = 26
        targetSdk              = 35
        versionCode            = 1
        versionName            = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true          // ← Enables the Compose compiler
    }

    composeOptions {
        // Must be aligned with your Kotlin version (1.9.23 → 1.5.11)
        // See alignment table: https://developer.android.com/jetpack/androidx/releases/compose-kotlin
        kotlinCompilerExtensionVersion = "1.5.11"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

configurations.all {
    resolutionStrategy {
        force("androidx.lifecycle:lifecycle-runtime-compose:2.8.1")
        force("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1")
        force("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
        force("androidx.lifecycle:lifecycle-common-java8:2.8.1")
    }
}

dependencies {
    // ── Core ─────────────────────────────────────────────────────────────────
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // ── Compose BOM (one version controls all compose libs) ──────────────────
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)

    // ── Navigation ───────────────────────────────────────────────────────────
    implementation(libs.androidx.navigation.compose)

    // ── ViewModel & Lifecycle ─────────────────────────────────────────────────
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // ── Hilt (Dependency Injection) ───────────────────────────────────────────
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // ── Image Loading ─────────────────────────────────────────────────────────
    implementation(libs.coil.compose)

    // ── DataStore ─────────────────────────────────────────────────────────────
    implementation(libs.androidx.datastore.preferences)

    // ── Retrofit ──────────────────────────────────────────────────────────────
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)

    // ── Debug Only ────────────────────────────────────────────────────────────
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ── Testing ───────────────────────────────────────────────────────────────
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}

// Required for Hilt annotation processing
kapt {
    correctErrorTypes = true
}