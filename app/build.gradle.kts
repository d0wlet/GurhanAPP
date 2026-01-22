plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.gurhan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gurhan"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Compose BOM (Updated)
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.0")
    
    // Material Ripple for touch feedback
    implementation("androidx.compose.material:material-ripple:1.6.0")
    
    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    
    // Activity Compose
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Core
    implementation("androidx.core:core-ktx:1.12.0")
    
    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    
    // JSON Parsing
    implementation("com.google.code.gson:gson:2.10.1")
}
