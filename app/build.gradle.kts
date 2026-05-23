plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {

    namespace = "com.example.restoranprofill"

    compileSdk = 35

    defaultConfig {

        applicationId = "com.example.restoranprofill"

        minSdk = 24
        targetSdk = 35

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        release {

            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {

        sourceCompatibility =
            JavaVersion.VERSION_17

        targetCompatibility =
            JavaVersion.VERSION_17
    }

    buildFeatures {

        compose = true
    }

    composeOptions {

        kotlinCompilerExtensionVersion = "1.5.14"
    }
    kotlinOptions {

        jvmTarget = "17"
    }
}

dependencies {

    // CORE
    implementation(
        "androidx.core:core-ktx:1.13.1"
    )

    implementation(
        "androidx.lifecycle:lifecycle-runtime-ktx:2.8.4"
    )

    implementation(
        "androidx.activity:activity-compose:1.9.1"
    )

    // COMPOSE BOM
    implementation(
        platform(
            "androidx.compose:compose-bom:2024.06.00"
        )
    )

    // COMPOSE UI
    implementation(
        "androidx.compose.ui:ui"
    )

    implementation(
        "androidx.compose.ui:ui-graphics"
    )

    implementation(
        "androidx.compose.ui:ui-tooling-preview"
    )

    // MATERIAL 3
    implementation(
        "androidx.compose.material3:material3"
    )

    // ICONS
    implementation(
        "androidx.compose.material:material-icons-extended"
    )

    // NAVIGATION COMPOSE
    implementation(
        "androidx.navigation:navigation-compose:2.7.7"
    )

    // ANIMATION
    implementation(
        "androidx.compose.animation:animation"
    )

    // TEST
    testImplementation(
        "junit:junit:4.13.2"
    )

    androidTestImplementation(
        platform(
            "androidx.compose:compose-bom:2024.06.00"
        )
    )

    androidTestImplementation(
        "androidx.compose.ui:ui-test-junit4"
    )

    androidTestImplementation(
        "androidx.test.ext:junit:1.2.1"
    )

    androidTestImplementation(
        "androidx.test.espresso:espresso-core:3.6.1"
    )

    debugImplementation(
        "androidx.compose.ui:ui-tooling"
    )

    debugImplementation(
        "androidx.compose.ui:ui-test-manifest"
    )
}