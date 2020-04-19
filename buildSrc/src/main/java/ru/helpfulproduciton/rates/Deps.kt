package ru.helpfulproduciton.rates

object Deps {

    object Config {
        const val applicationId = "ru.helpfulproduction.rates"
        const val appVersionCode = 1
        const val appVersionName = "1.0"
        const val compileSdk = 29
        const val minSdk = 22
        const val targetSdk = 29
        const val buildTools = "29.0.3"
        const val jvmTarget = "1.8"
    }

    object Versions {
        const val gradle = "3.6.2"
        const val kotlin = "1.3.61"

        const val constraintLayout = "1.1.3"
        const val coordinatorLayout = "1.1.0"
        const val recyclerView = "1.1.0"

        const val material = "1.1.0"
        const val rxJava = "3.0.0"
        const val rxAndroid = "3.0.0"
        const val okHttp = "4.5.0"
    }

    object LibrariesSupport {
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:${Versions.coordinatorLayout}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    }

    object Libraries {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val rxJava = "io.reactivex.rxjava3:rxjava:${Versions.rxJava}"
        const val rxAndroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxAndroid}"
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    }

    object Plugins {
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

}