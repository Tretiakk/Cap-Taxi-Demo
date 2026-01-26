import java.util.Properties

plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    kotlin("android")
    alias(libs.plugins.compose.compiler)

    // Hilt
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.cap.taxi"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.cap.taxi"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "T1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val keystoreFile = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        manifestPlaceholders["GOOGLE_MAP_KEY"] =
            properties.getProperty("GM_API_KEY") ?: ""
    }

    androidResources {
        localeFilters += listOf("en","pl","uk","de","es","fr")
    }

    signingConfigs {
        create("release") {
            storeFile = file("${projectDir.path}/ks/test.jks")
            storePassword = "111111"
            keyAlias = "key0"
            keyPassword = "111111"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

// Mockito
//val mockitoAgent = configurations.create("mockitoAgent")

dependencies {

    // modules
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.material)
    implementation(libs.compiler)
    implementation(libs.ui.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.constraintlayout.compose)
    implementation(libs.material3.android)
    debugImplementation(libs.ui.tooling)

    implementation(libs.appcompat)
    implementation(libs.google.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(kotlin("test"))

    // junit
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)

    // mockito
    testImplementation("io.mockk:mockk:1.13.12")


    // splash screen
    implementation(libs.core.splashscreen)

    // hilt
    implementation("com.google.dagger:hilt-android:2.57.2")
    ksp("com.google.dagger:hilt-android-compiler:2.57.2")

    // Google Maps UI
    implementation(libs.maps.compose)
}


// Mockito
//tasks.withType<Test> {
//    jvmArgs("-javaagent:${mockitoAgent.asPath}")
//}