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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":core:designsystem")
include(":core:utility")
include(":core:common")
include(":core:testing")
include(":feature:account:domain")
include(":feature:account:data")
include(":feature:account:presentation")
include(":feature:groups:domain")
include(":feature:groups:data")
include(":feature:groups:presentation")

