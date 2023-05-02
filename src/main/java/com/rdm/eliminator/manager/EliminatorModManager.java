package com.rdm.eliminator.manager;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class EliminatorModManager {
	
	public static void registerAll() {
		// Server
		ServerLifecycleEvents.SERVER_STARTING.register((server) -> EliminatorConfigManager.registerConfigs());
	}

}
