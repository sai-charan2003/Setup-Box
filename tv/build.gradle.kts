plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    alias(libs.plugins.hilt.android)
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
        versionCode = 2
        versionName = "2.0"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    hilt { enableAggregatingTask = false }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)
    implementation (libs.postgrest.kt)
    implementation (libs.realtime.kt)
    implementation (libs.storage.kt)
    implementation(libs.firebase.crashlytics)
    annotationProcessor (libs.androidx.room.compiler)
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    implementation(libs.auth.kt)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.runtime.livedata)
    kapt("androidx.room:room-compiler:2.6.1")
    implementation(libs.coil.compose)
    implementation (libs.ktor.client.core)
    implementation (libs.ktor.utils)
    implementation(libs.ktor.client.cio)
    implementation(libs.androidx.tv.foundation)
    implementation(libs.androidx.tv.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation ("androidx.core:core-splashscreen:1.0.1")
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
    implementation ("androidx.compose.material:material:1.7.5")
}