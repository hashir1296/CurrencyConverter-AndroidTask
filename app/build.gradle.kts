@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.hashir.currencyconverter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hashir.currencyconverter"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildFeatures {
            buildConfig = true
        }

        buildConfigField(
            "String", "API_KEY", "${project.property("CURRENCY_RATE_API_KEY")}"
        )
        buildConfigField(
            "String", "CHARTS_API_KEY", "${project.property("CHARTS_RATE_API_KEY")}"
        )
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
        }
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    viewBinding {
        android.buildFeatures.viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Sdp & ssp library for responsive dimensions - it will generate its own dimen file so we don"t have to maintain ours manually
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    // Hilt dependencies
    implementation("com.google.dagger:hilt-android:2.44")
    implementation("androidx.test:core-ktx:1.5.0")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    //Okhttp & Stetho
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    implementation("com.facebook.stetho:stetho-okhttp3:1.6.0")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    //Kotlin Coroutines
    val coroutinesVersion = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    //Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    //Gson
    implementation("com.google.code.gson:gson:2.10.1")

    //Chart library without license
    // Houses the core logic for charts and other elements. Included in all other modules.
    implementation("com.patrykandpatrick.vico:core:1.12.0")

    // For the view system.
    implementation("com.patrykandpatrick.vico:views:1.12.0")

    //Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")



}