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

val composeVersion = "1.0.0-rc02"

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("com.github.zerobranch:SwipeLayout:1.3.1")
    implementation("de.charlex.compose:revealswipe:1.0.0-beta05")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    //koin
    // Koin for Android
    implementation("io.insert-koin:koin-android:2.2.3")
    // Koin Android Scope features
    implementation("io.insert-koin:koin-android-scope:2.2.3")
// Koin Android ViewModel features
    implementation("io.insert-koin:koin-android-viewmodel:2.2.3")
// Koin Android Experimental features
    implementation("io.insert-koin:koin-android-ext:2.2.3")
    //corotines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")
    //Drag Drop Swipe RecyclerView
    implementation("com.ernestoyaquello.dragdropswiperecyclerview:drag-drop-swipe-recyclerview:1.1.0")

    implementation("androidx.compose.ui:ui:$composeVersion")
    // Material Design
    implementation("androidx.compose.material:material:$composeVersion")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.3.0-rc02")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
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
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
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