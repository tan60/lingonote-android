plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.musdev.lingonote.core.data"
}

dependencies {

    api(libs.moshi)
    ksp(libs.moshi.codegen)
}