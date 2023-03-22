rootProject.name = "SharedBill"

pluginManagement {
    includeBuild("build-logic")
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

include(":app")
include(":core:designsystem")
include(":core:utility")
include(":core:common")
include(":feature:account:domain")
include(":feature:account:data")
include(":feature:account:presentation")
