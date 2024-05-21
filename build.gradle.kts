// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.22")
        classpath("com.chaquo.python:gradle:15.0.1")
    }
}

plugins {
    id("com.android.application") apply false
    id("org.jetbrains.kotlin.android") apply false
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
