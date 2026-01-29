rootProject.name = "endelith"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.spongepowered.org/maven")
    }
}

include("api", "server", "data", "server:log4j2-plugin")
project(":server:log4j2-plugin").projectDir = file("server/log4j2-plugin")
