plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    id("kotlin-android")
}
group = "com.jetbrains"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}
dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.compose.ui:ui:1.0.0-rc02")
    implementation("com.github.zerobranch:SwipeLayout:1.3.1")
    implementation("de.charlex.compose:revealswipe:1.0.0-beta05")
    implementation("androidx.compose.material:material:1.0.0-rc02")
    implementation("androidx.compose.ui:ui-tooling:1.0.0-rc02")
    implementation("androidx.compose.ui:ui-tooling:1.0.0-rc02")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.0-rc02")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.0-rc02")
}
android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.jetbrains.androidApp"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.0-rc02"
        kotlinCompilerVersion = "1.4.32"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
}