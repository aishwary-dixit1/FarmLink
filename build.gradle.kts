// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    kotlin("plugin.serialization") version "1.8.22" apply false
    id("com.google.dagger.hilt.android") version "2.52" apply false
    id("io.realm.kotlin") version "2.1.0" apply false
}

buildscript {
    repositories {
        // Make sure that you have the following two repositories
        gradlePluginPortal()
        google()  // Google's Maven repository

        mavenCentral()  // Maven Central repository

    }
    dependencies {

        // Add the dependency for the Google services Gradle plugin
        classpath("com.google.gms:google-services:4.4.2")
//        id 'com.google.android.libraries.mapsplatform.secrets-gradle-plugin' version '2.0.1' apply false

        classpath("io.realm:realm-gradle-plugin:10.11.1")
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}