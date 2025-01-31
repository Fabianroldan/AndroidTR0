plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.unscramble"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.unscramble"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Asegúrate de que sea compatible con tu versión de Compose
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.08.00")) // Utiliza el BOM para manejar versiones de Compose
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("io.coil-kt:coil-compose:2.1.0")

    // Dependencia para cargar imágenes
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Dependencias de Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Cliente de Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Convertidor de JSON

    // Si decides usar Kotlin Serialization en lugar de GSON, descomenta la siguiente línea
    // implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    debugImplementation("androidx.compose.ui:ui-test-manifest") // Test manifest para Compose
    debugImplementation("androidx.compose.ui:ui-tooling") // Herramientas para depuración de UI
}
