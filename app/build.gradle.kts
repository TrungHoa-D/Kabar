plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.socialnetwork"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.socialnetwork"
        minSdk = 28
        targetSdk = 36
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("androidx.lifecycle:lifecycle-livedata:2.7.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
// Converter JSON
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
// OkHttp (cho networking)
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
// Logging Interceptor (log request/response)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
}