// The buildscript block is used to configure the build script and its classpath.
buildscript {
    // Repositories define where the build script looks for dependencies.
    repositories {
        // Google() and jcenter() are repositories that contain the required dependencies.
        google()
        jcenter()
    }

    // Dependencies define the libraries and plugins needed for the build script.
    dependencies {
        // The Android Gradle plugin is used to build Android applications.
        classpath("com.android.tools.build:gradle:7.3.1")

        // The Kotlin Gradle plugin is used to compile Kotlin code.
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")

        // The Chaquopy plugin is used to integrate Python with Android applications.
        classpath("com.chaquo.python:gradle:12.1.0")
    }
}

// Plugins apply various functionalities to the project.
plugins {
    // The com.android.application plugin is used to build Android applications.
    id("com.android.application")

    // The org.jetbrains.kotlin.android plugin is used to compile Kotlin code for Android.
    id("org.jetbrains.kotlin.android")

    // The com.chaquo.python plugin is used to integrate Python with Android applications.
    id("com.chaquo.python")
}

// The android block is used to configure the Android application.
android {
    // The namespace is the unique identifier for the application.
    namespace = "com.example.dtcbuslist"

    // The compileSdk and targetSdk define the Android API level to compile and target.
    compileSdk = 33 // updated to the latest stable version
    targetSdk = 33 // updated to the latest stable version

    // The defaultConfig block contains default configurations for the application.
    defaultConfig {
        // The applicationId is the unique identifier for the application.
        applicationId = "com.example.dtcbuslist"

        // The minSdk and targetSdk define the minimum and target Android API levels.
        minSdk = 24
        targetSdk = 33 // updated to the latest stable version

        // The versionCode and versionName define the application version.
        versionCode = 1
        versionName = "1.0"

        // The testInstrumentationRunner is the test runner for Android Instrumentation tests.
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // The ndk block is used to configure the Native Development Kit (NDK) for native libraries.
        ndk {
            // The abiFilters define the list of supported CPU architectures.
            abiFilters += listOf("arm64-v8a", "x86_64")
        }
    }

    // The buildTypes block is used to configure the build types for the application.
    buildTypes {
        // The release build type is used for production builds.
        release {
            // isMinifyEnabled is used to enable ProGuard or R8 for code obfuscation.
            isMinifyEnabled = false

            // proguardFiles define the ProGuard or R8 rules for code obfuscation.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // The compileOptions block is used to configure the Java compiler options.
    compileOptions {
        // sourceCompatibility and targetCompatibility define the Java version.
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // The kotlinOptions block is used to configure the Kotlin compiler options.
    kotlinOptions {
        // jvmTarget defines the target Java version for Kotlin code.
        jvmTarget = "1.8"
    }
}

// The chaquopy block is used to configure the Chaquopy plugin.
chaquopy {
    // The defaultConfig block contains default configurations for the Chaquopy plugin.
    defaultConfig {
        // The version defines the version of the Chaquopy plugin.
        version = "12.1.0" // updated to the latest stable version

        /*
        // The pip block is used to install Python packages using pip.
        pip {
            // install installs the specified Python packages.
            install("geopy")
            install("protobuf")
            install("requests")
            install("gtfs-realtime-bindings==0.0.7")
        }
        */
    }
}

// The dependencies block is used to define the dependencies for the application.
dependencies {
    // The implementation block is used to define the dependencies for the application.
    implementation("androidx.core:core-ktx:1.10.0") // updated to the latest stable version
    implementation("androidx.appcompat:appcompat:1.6.1") // updated to the latest stable version
    implementation("com.google.android.material:material:1.7.0") // updated to the latest stable version
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-location:21.0.1") // updated to the latest stable version

    // The testImplementation block is used to define the test dependencies for the application.
    testImplementation("junit:junit:4.13.2")

    // The androidTestImplementation block is used to define the Android test dependencies for the application.
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
