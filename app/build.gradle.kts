plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")//args seguros
}

android {
    namespace = "com.kto.employejetnexa"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kto.employejetnexa"
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val dagger = "2.48"
    val retroGson = "2.9.0"
    val okhtt = "4.3.1"


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")

    //DaggerHilt injection
    implementation("com.google.dagger:hilt-android:${dagger}")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt("com.google.dagger:hilt-compiler:${dagger}")
    //implementation("androidx.hilt:hilt-work:1.1.0")
    //kapt("androidx.hilt:hilt-compiler:1.1.0")
    //implementation("androidx.work:work-runtime-ktx:2.9.0")
    //implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Lifecycle
    val lifecycle_version = "2.6.2"
    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${lifecycle_version}")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycle_version}")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:${lifecycle_version}")


    //Retrofit llamadas a apisrest
    implementation("com.squareup.retrofit2:retrofit:${retroGson}")
    implementation("com.squareup.retrofit2:converter-gson:${retroGson}")
    implementation("com.squareup.okhttp3:logging-interceptor:${okhtt}")

    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")


    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    //unitTesting
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation("io.mockk:mockk:1.12.3")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-test
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    //UITesting
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.48")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
