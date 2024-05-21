plugins {
    id 'com.android.application' version '7.2.0' apply false
    id 'com.github.dcendents.android-maven' version '2.1'
    id 'org.jetbrains.kotlin.android' version '1.6.10'
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "DTCBuslist"
include(":app")

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}
