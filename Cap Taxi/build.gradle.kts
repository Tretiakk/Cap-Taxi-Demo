buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle.v852)
        classpath(libs.secrets.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin.v170)
    }
}

plugins {
    alias(libs.plugins.compose.compiler) apply false

    // Hilt
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
    id("com.google.devtools.ksp") version "2.2.21-2.0.4"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
