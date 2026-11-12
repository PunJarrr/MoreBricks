plugins {
    // Applies the right Loom variant for the Minecraft version
    id("dev.kikugie.loom-back-compat")
    // Uploads to Modrinth and CurseForge (https://modmuss50.github.io/mod-publish-plugin/)
    id("me.modmuss50.mod-publish-plugin") version "2.2.1"
}

val modId = sc.properties["mod.id"]
val modVersion = sc.properties["mod.version"]

version = "$modVersion+${sc.current.version}"
base.archivesName = "$modId-fabric"

val requiredJava: JavaVersion = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    else -> JavaVersion.VERSION_17
}

// Data generation runs on Fabric. Its output is shared with the NeoForge build of the same Minecraft version.
val generatedResources = rootProject.file("src/generated/${sc.current.version}")

sourceSets.main {
    resources.srcDir(generatedResources)
}

repositories {
    exclusiveContent {
        forRepository { maven("https://api.modrinth.com/maven") { name = "Modrinth" } }
        filter { includeGroup("maven.modrinth") }
    }
    exclusiveContent {
        forRepository { maven("https://maven.wispforest.io/releases") { name = "Wisp Forest" } }
        filter { includeGroupByRegex("io\\.wispforest(\\..*)?") }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${sc.current.version}")
    // Mojang's official names on obfuscated versions, so code matches 26.1+
    loomx.applyMojangMappings()

    // `mod*` configurations are converted by loom-back-compat on 26.1+
    modImplementation("net.fabricmc:fabric-loader:${sc.properties["deps.fabric_loader"]}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${sc.properties["deps.fabric_api"]}")

    // Dev-only: Isometric Renders for showcase images (`/isorender`). Only loaded by runClient,
    // never part of the published jar. Its newest version targets 1.21.4.
    if (sc.current.version == "1.21.4") {
        modLocalRuntime("maven.modrinth:isometric-renders:0.4.9+1.21.4")
        // From Wisp Forest, so its bundled libraries come along as normal dependencies
        modLocalRuntime("io.wispforest:owo-lib:0.12.20+1.21.4")
        // Bundled inside Isometric Renders, but dev runs don't load bundled jars
        modLocalRuntime("io.wispforest:worldmesher:0.4.7+1.21.4")
    }
}

fabricApi {
    configureDataGeneration {
        client = true
        modId = sc.properties["mod.id"]
        outputDirectory = generatedResources
    }
}

loom {
    fabricModJsonPath = rootProject.file("src/main/resources/fabric.mod.json")

    runConfigs.all {
        preferGradleTask = true
        generateRunConfig = true
        runDirectory = rootProject.file("run/${sc.current.version}")
    }
}

java {
    withSourcesJar()
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava

    toolchain {
        vendor = JvmVendorSpec.ADOPTIUM
        languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion)
    }
}

tasks {
    processResources {
        val props = mapOf(
            "id" to modId,
            "name" to sc.properties["mod.name"],
            "version" to modVersion,
            "minecraft" to sc.properties["mod.mc_compat"],
            "loader" to Regex("\\d+\\.\\d+").find(sc.properties["deps.fabric_loader"])!!.value,
        )
        inputs.properties(props)
        filesMatching("fabric.mod.json") { expand(props) }

        exclude("META-INF/neoforge.mods.toml", ".cache")
    }

    withType<Jar> {
        val id = modId
        inputs.property("mod_id", id)
        from(rootProject.file("LICENSE")) { rename { "${it}_$id" } }
        // Required by the Vanilla Tweaks terms (https://vanillatweaks.net/terms/)
        from(rootProject.file("credits.txt"))
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds the mod jar and copies it to `build/libs/{mod version}/`"

        inputs.property("version", modVersion)
        from(loomx.modJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("libs/$modVersion"))
    }
}

publishMods {
    val releases = sc.properties.rawOrNull("mod", "mc_releases")?.asList().orEmpty().map { it.toString() }
    val modrinthToken = providers.environmentVariable("MODRINTH_TOKEN")
    val curseforgeToken = providers.environmentVariable("CURSEFORGE_TOKEN")

    file = loomx.modJar.flatMap { it.archiveFile }
    displayName = "${sc.properties["mod.name"]} $modVersion (Fabric ${sc.current.version})"
    version = "$modVersion+${sc.current.version}-fabric"
    changelog = providers.environmentVariable("CHANGELOG").orElse("See https://github.com/PunJarrr/MoreBricks/releases")
    type = STABLE
    modLoaders.add("fabric")
    // Without tokens, `publishMods` only checks the configuration
    dryRun = modrinthToken.map { false }.orElse(true)

    modrinth {
        projectId = sc.properties["publish.modrinth"]
        accessToken = modrinthToken
        minecraftVersions.addAll(releases)
        requires("fabric-api")
    }
    curseforge {
        projectId = sc.properties["publish.curseforge"]
        accessToken = curseforgeToken
        minecraftVersions.addAll(releases)
        javaVersions.add(requiredJava)
        clientRequired = true
        serverRequired = true
        requires("fabric-api")
    }
}

// Upload order: Modrinth and CurseForge list the newest upload first. Upload every NeoForge file
// before any Fabric file, and older Minecraft versions before newer ones, so the top of each list
// is the newest Fabric version (Fabric is used by more players).
/** "1.21.4-fabric" -> "0001.0021.0004", so versions compare correctly as text */
fun versionSortKey(projectName: String) =
    projectName.substringBefore('-').split('.').joinToString(".") { it.padStart(4, '0') }

val uploadsBeforeThis = rootProject.subprojects.filter {
    it.name.endsWith("-neoforge") ||
        (it.name.endsWith("-fabric") && versionSortKey(it.name) < versionSortKey(project.name))
}.map { it.path }
tasks.matching { it.name == "publishModrinth" || it.name == "publishCurseforge" }.configureEach {
    val uploadTask = name
    mustRunAfter(uploadsBeforeThis.map { "$it:$uploadTask" })
}
