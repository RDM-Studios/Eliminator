package com.rdm.eliminator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rdm.eliminator.manager.EliminatorModManager;

import net.fabricmc.api.ModInitializer;

public class Eliminator implements ModInitializer {
	public static final String MODID = "eliminator";
    public static final Logger LOGGER = LoggerFactory.getLogger("eliminator");

    @Override
    public void onInitialize() {
    	EliminatorModManager.registerAll();
    }
}