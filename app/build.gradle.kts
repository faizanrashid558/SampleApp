plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.safeargs) apply false
    alias(libs.plugins.hiltPlugin)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.sampleapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sampleapp"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.sdp.android)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Room DB
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    //Koin DI
//    implementation (libs.koin.core)
//    implementation (libs.koin.android)
//    implementation (libs.koin.test)
    //Hilt DI

    implementation(libs.hilt.android.v248)
    kapt(libs.hilt.compiler.v248)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.gson)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.gson)

    //Work Manager
    implementation(libs.androidx.work.runtime.ktx)

    //Picasso
    implementation(libs.picasso)

    //Glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    implementation("com.google.android.play:review:2.0.1")
    implementation("com.google.android.play:review-ktx:2.0.1")
    //image text recognize
    implementation("com.google.mlkit:text-recognition:16.0.1")
    //face detection
    implementation("com.google.mlkit:face-detection:16.1.7")

    //face mesh
    implementation ("com.google.mlkit:face-mesh-detection:16.0.0-beta1")


    

}
