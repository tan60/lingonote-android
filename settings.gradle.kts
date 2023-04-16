pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()

    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "LingoNote"
include(":app")
include(":core-data")
include(":core-domain")
