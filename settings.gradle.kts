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

rootProject.name = "Arumo-beta"
include(":app")
include(":data:repository")
include(":data:local")
include(":data:remote")
include(":domain:repository")
include(":domain:usecase")
include(":domain:model")
include(":core:ui")
include(":core:common")
include(":core:navigation")
