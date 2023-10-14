package net.punjarrr.morebricks;

import net.fabricmc.api.ModInitializer;

import net.punjarrr.morebricks.block.ModBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreBricks implements ModInitializer {
	public static final String MOD_ID = "morebricks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
	}
}