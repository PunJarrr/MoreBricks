pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") { name = "FabricMC" }
        maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    // https://stonecutter.kikugie.dev/
    id("dev.kikugie.stonecutter") version "0.10"
    // Picks the right Loom variant for obfuscated (<26.1) and unobfuscated (26.1+) Minecraft
    id("dev.kikugie.loom-back-compat") version "0.4.3"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        /** Creates `versions/{version}-{loader}` subprojects, each built by `build.{loader}.gradle.kts`. */
        fun target(version: String, vararg loaders: String) {
            for (loader in loaders) version("$version-$loader", version).buildscript("build.$loader.gradle.kts")
        }

        target("26.3", "fabric", "neoforge")

        vcsVersion = "26.3-fabric"
    }
}

rootProject.name = "MoreBricks"
