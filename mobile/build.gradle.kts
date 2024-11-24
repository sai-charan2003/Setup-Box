plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.mikepenz.aboutlibrary)

}

android {
    namespace = "com.charan.setupBox"
    compileSdk = 34
    val key:String=com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir,providers).getProperty("SUPABASE_ANON_KEY")

    val url:String=com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir,providers).getProperty("SUPABASE_URL")

    val google_signin = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir,providers).getProperty("GOOGLE_SERVER_CLIENT_ID")

    defaultConfig {
        applicationId = "com.charan.setupBox"
        minSdk = 21
        targetSdk = 34
        versionCode = 2
        versionName = "2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String","SUPABASE_ANON_KEY","\"$key\"")
        buildConfigField("String", "SUPABASE_URL", "\"$url\"")
        buildConfigField("String","GOOGLE_SERVER_CLIENT_ID","\"$google_signin\"")
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
    hilt { enableAggregatingTask = false }
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
    annotationProcessor (libs.androidx.room.compiler)
    kapt("androidx.room:room-compiler:2.6.1")
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.auth.kt)
    implementation(libs.coil.compose)
    implementation (libs.androidx.core.splashscreen)
    implementation("androidx.compose.material:material-icons-extended-android:1.7.5")
    implementation (libs.ktor.client.core)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.runtime.livedata)
    implementation (libs.ktor.utils)
    implementation(libs.ktor.client.cio)
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation( libs.androidx.credentials)
    implementation (libs.credentials.play.services.auth)
    implementation (libs.googleid)
    implementation (libs.play.services.auth)
    implementation (libs.aboutlibraries.core)
    implementation(libs.aboutlibraries.compose.m3)
}