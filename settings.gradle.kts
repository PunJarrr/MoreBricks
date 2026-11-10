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

        // Each target covers the Minecraft releases listed under `mod.mc_releases` in stonecutter.properties.toml
        target("1.20.1", "fabric") // NeoForge has no stable 1.20.1 release (1.20.1 modpacks use Forge)
        target("1.20.4", "fabric", "neoforge")
        target("1.20.6", "fabric", "neoforge")
        target("1.21.1", "fabric", "neoforge")
        target("1.21.4", "fabric", "neoforge")
        target("1.21.5", "fabric", "neoforge")
        target("1.21.8", "fabric", "neoforge")
        target("1.21.10", "fabric", "neoforge")
        target("1.21.11", "fabric", "neoforge")
        target("26.1.2", "fabric", "neoforge")
        target("26.2", "fabric", "neoforge")
        target("26.3", "fabric", "neoforge")

        vcsVersion = "26.3-fabric"
    }
}

rootProject.name = "MoreBricks"
