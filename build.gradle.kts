// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.google.dagger.hilt.android") version "2.44" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")

    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.1.0")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0-rc01")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}