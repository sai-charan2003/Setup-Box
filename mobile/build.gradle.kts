plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.charan.setupBox"
    compileSdk = 34
    val key:String=com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir,providers).getProperty("SUPABASE_ANON_KEY")

    val url:String=com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir,providers).getProperty("SUPABASE_URL")

    defaultConfig {
        applicationId = "com.charan.setupBox"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String","SUPABASE_ANON_KEY","\"$key\"")
        buildConfigField("String", "SUPABASE_URL", "\"$url\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion ="1.5.2"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3.android)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.crashlytics)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation (libs.postgrest.kt)
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    implementation (libs.realtime.kt)
    implementation (libs.storage.kt)
    implementation(libs.gotrue.kt)
    annotationProcessor (libs.androidx.room.compiler)
    kapt("androidx.room:room-compiler:2.6.1")
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation ("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.compose.material:material-icons-extended-android:1.6.5")
    implementation (libs.ktor.client.core)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.runtime.livedata)
    implementation (libs.ktor.utils)
    implementation(libs.ktor.client.cio)
}