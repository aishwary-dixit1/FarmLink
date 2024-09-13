import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("io.realm.kotlin")
}
android {
    namespace = "com.example.farmlinkapp1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.farmlinkapp1"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        android.buildFeatures.buildConfig = true

        val localProperties = Properties()
        val stream = FileInputStream(File("local.properties"))
        localProperties.load(stream)

        val apiKey = localProperties.getProperty("Google_API_KEY", "")
        buildConfigField("String", "Google_API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Compose Navigation
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //kotlin serialization
    implementation(libs.kotlinx.serialization.json)

    //Coil Library
    implementation(libs.coil.compose)

    //Compose Compiler
    implementation(libs.ui)

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    //Dagger-Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Realm DB
    implementation(libs.library.base)
    // If using Device Sync
    implementation("io.realm.kotlin:library-sync:2.1.0")
    // If using coroutines with the SDK
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    implementation(libs.play.services.maps)
    implementation(libs.maps.compose)
    implementation(libs.mongodb.driver.sync)

    //message bar compose
    implementation("com.github.stevdza-san:MessageBarCompose:1.0.5")

    //one-tap compose
    implementation("com.github.stevdza-san:OneTapCompose:1.0.0")

    //maps sdk
    implementation("com.google.maps.android:maps-compose:6.1.2")

    // Optionally, you can include the Compose utils library for Clustering,
    // Street View metadata checks, etc.
    implementation("com.google.maps.android:maps-compose-utils:6.1.2")

    // Optionally, you can include the widgets library for ScaleBar, etc.
    implementation("com.google.maps.android:maps-compose-widgets:6.1.2")
}