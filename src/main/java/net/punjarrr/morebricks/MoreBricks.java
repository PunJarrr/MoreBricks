package net.punjarrr.morebricks;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Loader-independent constants and helpers. */
public final class MoreBricks {
    public static final String MOD_ID = "morebricks";
    public static final Logger LOGGER = LoggerFactory.getLogger("MoreBricks");

    private MoreBricks() {}

    public static Identifier id(String path) {
        //? if <1.21 {
        /*return new Identifier(MOD_ID, path);
        *///?} else
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
