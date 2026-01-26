import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

android {
    namespace = "hminq.dev.madridata"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "hminq.dev.madridata"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val apiBaseUrl = localProperties.getProperty("API_BASE_URL") ?: "http://10.0.2.2:5004/api/"
        buildConfigField("String", "API_BASE_URL", "\"$apiBaseUrl\"")
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    //Coil for image loading
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)
    //Hilt for DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // Retrofit for API calling
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.gson)
    // Gson for JSON parsing
    implementation(libs.gson)
    // ViewModel
    implementation(libs.lifecycle.viewmodel.ktx)
    // Views/Fragments integration
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.navigation.ui)
    // Feature module support for Fragments
    implementation(libs.androidx.navigation.dynamic.features.fragment)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}