stonecutter active "26.3-fabric"

// See https://stonecutter.kikugie.dev/wiki/config/params
stonecutter parameters {
    val (version, loader) = current.project.split('-', limit = 2)

    // Applies version- and loader-specific values from `stonecutter.properties.toml`
    properties {
        tags(version, loader)
    }

    // Enables `//? if fabric {` and `//? if neoforge {` in source files
    constants {
        match(loader, "fabric", "neoforge")
    }

    replacements {
        // Mojang renamed ResourceLocation to Identifier in 1.21.11
        string(current.parsed >= "1.21.11") {
            replace("ResourceLocation", "Identifier")
        }
    }
}
