package com.rdm.eliminator.manager;


import com.mojang.datafixers.util.Pair;
import com.rdm.eliminator.Eliminator;
import com.rdm.eliminator.config.common.SimpleConfig;
import com.rdm.eliminator.config.common.SimpleConfigProvider;

public class EliminatorConfigManager {
    public static SimpleConfig MAIN_CONFIG;
    private static SimpleConfigProvider CONFIG_PROVIDER;
    
    public static boolean ENABLE_SERVER_DEATH_BAN_ANNOUNCEMENTS;
    
    public static String PVP_DEATH_BAN_MESSAGE_SERVER;
    public static String PVE_DEATH_BAN_MESSAGE_SERVER;

    public static String PVP_DEATH_BAN_MESSAGE;
    public static String PVE_DEATH_BAN_MESSAGE;
    
    public static int PVP_DEATH_BAN_INTERVAL;
    public static int PVE_DEATH_BAN_INTERVAL;
    
    public static void registerConfigs() {
        CONFIG_PROVIDER = new SimpleConfigProvider();
        createConfigs();

        MAIN_CONFIG = SimpleConfig.of(Eliminator.MODID).provider(CONFIG_PROVIDER).request();

        assignConfigs();
    }

    private static void createConfigs() {
    	CONFIG_PROVIDER.addKeyValuePair(new Pair<>("Enable Server Death Ban Announcements", true), "Specify whether or not player deaths/bans should be announced on the server | boolean");
    	
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>("PVP Death Ban Message (Server Announcement)", "%1$s died to %2$s in PVP!"), "The message broadcasted to the server upon a player's death in PVP (if the config option is enabled) | String");
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>("PVE Death Ban Message (Server Announcement)", "%1$s died to %2$s PVE!"), "The message broadcasted to the server upon a player's death in PVE (if the config option is enabled) | String");
    	
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>("PVP Death Ban Message (Banned Player)", "You died in PVP!"), "The message presented to a player upon death in PVP | String");
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>("PVE Death Ban Message (Banned Player)", "You died in PVE!"), "The message presented to a player upon death in PVE | String");
        
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>("The amount of time a player should be removed from the server upon dying in PVP (in minutes)", 1), "The amount of time (in minutes) a player should be banned for upon dying in PVP (DO NOT ATTEMPT TO INPUT NEGATIVE NUMBERS!) | int");
        CONFIG_PROVIDER.addKeyValuePair(new Pair<>("The amount of time a player should be removed from the server upon dying in PVE (in minutes)", 1), "The amount of time (in minutes) a player should be banned for upon dying in PVE (DO NOT ATTEMPT TO INPUT NEGATIVE NUMBERS!) | int");
    }

    private static void assignConfigs() {
    	ENABLE_SERVER_DEATH_BAN_ANNOUNCEMENTS = MAIN_CONFIG.getOrDefault("Enable Server Death Ban Announcements", true);
    	
    	PVP_DEATH_BAN_MESSAGE_SERVER = MAIN_CONFIG.getOrDefault("PVP Death Ban Message (Server Announcement)", "%1$s died to %2$s in PVP!");
    	PVE_DEATH_BAN_MESSAGE_SERVER = MAIN_CONFIG.getOrDefault("PVE Death Ban Message (Server Announcement)", "%1$s died to %2$s PVE!");
    	
    	PVP_DEATH_BAN_MESSAGE = MAIN_CONFIG.getOrDefault("PVP Death Ban Message (Banned Player)", "You died in PVP!");
    	PVE_DEATH_BAN_MESSAGE = MAIN_CONFIG.getOrDefault("PVE Death Ban Message (Banned Player)", "You died in PVE!");
    	
    	PVP_DEATH_BAN_INTERVAL = MAIN_CONFIG.getOrDefault("The amount of time a player should be removed from the server upon dying in PVP (in minutes)", 1);
    	PVE_DEATH_BAN_INTERVAL = MAIN_CONFIG.getOrDefault("The amount of time a player should be removed from the server upon dying in PVE (in minutes)", 1);
    }
}
