plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
}

android {
    namespace = "cz.cvut.fit.hlianole.miracle_meal_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "cz.cvut.fit.hlianole.miracle_meal_app"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    }
}

dependencies {

    implementation(libs.android.core)

    implementation(libs.android.lifecycle.runtime)
    implementation(libs.android.lifecycle.runtime.compose)
    implementation(libs.android.lifecycle.viewmodel.compose)

    implementation(libs.android.navigation.compose)

    implementation(libs.android.room)
    ksp(libs.android.room.compiler)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.activity)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    debugImplementation(libs.compose.uiTooling)
    implementation(libs.compose.uiToolingPreview)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)

    implementation(libs.kotlin.serialization.json)

    implementation(libs.ktor.android)
    implementation(libs.ktor.contentNegotiation)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.serialization.json)

    implementation(libs.material)

    implementation(libs.coil.compose)

    implementation(libs.worker)

    implementation(libs.activity.ktx)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
}
