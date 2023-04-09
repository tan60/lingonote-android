buildscript {
    repositories {
        google()
        maven("https://plugins.gradle.org/m2/")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.android.library) apply false
    //alias(libs.plugins.kotlin.jvm) apply false

    alias(libs.plugins.com.google.devtools.ksp) apply false

}