// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript { // Configures the build script classpath and dependencies.
    repositories { // Repository definitions for dependencies.
        gradlePluginPortal() // Gradle Plugin Portal repository.
        google() // Google's Maven repository.
        jcenter() // JFrog's Bintray JCenter repository.
    }

    dependencies { // Classpath dependencies for the build script.
        classpath("com.android.tools.build:gradle:8.2.2") // Android Gradle plugin.
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.22") // Kotlin Gradle plugin.
        classpath("com.chaquo.python:gradle:15.0.1") // Chaquopy Gradle plugin for Python support.
    }
}

plugins { // Plugin block to configure plugins for the project.
    id("com.android.application") apply false // Disables the Android application plugin.
    id("org.jetbrains.kotlin.android") apply false // Disables the Kotlin Android plugin.
}

allprojects { // Configures repositories for all sub-projects.
    repositories { // Repository definitions for sub-projects.
        google() // Google's Maven repository.
        jcenter() // JFrog's Bintray JCenter repository.
    }
}

task clean(type: Delete) { // Defines a 'clean' task to delete the build directory.
    delete rootProject.buildDir // Deletes the root project's build directory.
}
