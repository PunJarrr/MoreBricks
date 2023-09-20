package net.punjarrr.morebricks;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreBricks implements ModInitializer {
	public static final String MOD_ID = "morebricks";
    public static final Logger LOGGER = LoggerFactory.getLogger("morebricks");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}