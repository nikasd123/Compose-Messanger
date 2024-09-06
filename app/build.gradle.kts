plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id ("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.kaz4.composemessanger"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kaz4.composemessanger"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    kotlinOptions {
        jvmTarget = "19"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.20"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    //Navigation
    implementation (libs.androidx.navigation.compose)

    //Accompanist
    implementation (libs.accompanist.systemuicontroller)
    implementation (libs.accompanist.swiperefresh)
    implementation (libs.accompanist.pager)

    //icon
    implementation (libs.androidx.material.icons.extended)

    // Coil
    implementation (libs.coil.compose)

    //Dagger-hilt
    implementation (libs.hilt.android)
    ksp (libs.hilt.android.compiler)
    implementation (libs.androidx.hilt.navigation.compose)
    ksp (libs.androidx.hilt.compiler)

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.logging.interceptor)

    //OkHttp
    implementation (libs.okhttp)
    implementation (libs.okio)

    //Gson
    implementation (libs.gson.v2101)
    implementation (libs.converter.gson)

    implementation (libs.kotlinx.coroutines.play.services)

    implementation (libs.material3)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}