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
    }
}

rootProject.name = "Der3 Muslim"
include(":app")
include(":navigation")
include(":features")
include(":core")
include(":core:utils")
include(":core:ui")
include(":features:splash")
include(":core:data_store")
include(":core:ui-model")
include(":core:mvi")
