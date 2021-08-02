buildscript {
    val compose_version by extra("1.0.0-rc02")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
        classpath("com.android.tools.build:gradle:7.0.0-rc01")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.0")
        classpath("io.insert-koin:koin-gradle-plugin:2.2.3")

    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
