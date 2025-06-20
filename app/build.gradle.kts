import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.parcelize")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.example.mnrader"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mnrader"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Naver Map API 키 설정
        manifestPlaceholders["NAVER_CLIENT_ID"] = properties["NAVER_CLIENT_ID"].toString()
        buildConfigField("String", "BASE_URL", properties["BASE_URL"].toString())
        buildConfigField("String", "DATA_PORTAL_BASE_URL", properties["DATA_PORTAL_BASE_URL"].toString())
        buildConfigField("String", "DATA_PORTAL_SERVICE_KEY", properties["DATA_PORTAL_SERVICE_KEY"].toString())
        buildConfigField("String", "NAVER_BASE_URL", properties["NAVER_BASE_URL"].toString())
        buildConfigField("String", "NAVER_CLIENT_ID", properties["NAVER_CLIENT_ID"].toString())
        buildConfigField("String", "NAVER_MAP_CLIENT_SECRET", properties["NAVER_MAP_CLIENT_SECRET"].toString())
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.compose.material:material-icons-extended")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // retrofit
    implementation(libs.retrofit)
    //converter-gson
    implementation(libs.converter.gson)
    // okhttp3 의존성 (멀티파트 로그 체크용(
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Permission
    implementation(libs.accompanist.permissions)

    // NaverMap
    implementation(libs.map.sdk)
    implementation(libs.naver.map.compose)

    // Location
    implementation(libs.play.services.location)
    implementation(libs.naver.map.location)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Network
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.15.0"))
    implementation("com.google.firebase:firebase-messaging")
    
    // Coroutines Play Services
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1")

}