pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://androidx.dev/snapshots/builds/13508953/artifacts/repository")
        }
    }
}

rootProject.name = "Der3 Muslim"
include(":app")
include(":navigation")
include(":features")
include(":core")
include(":core:ui")
include(":features:splash")
include(":core:data_store")
include(":core:ui-model")
include(":core:mvi")
include(":features:on_boarding")
include(":screens")
include(":features:home")
include(":core:utils")
include(":core:data")
