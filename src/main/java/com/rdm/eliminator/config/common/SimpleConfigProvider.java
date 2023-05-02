package com.rdm.eliminator.config.common;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;
import com.rdm.eliminator.config.common.SimpleConfig.DefaultConfig;

public class SimpleConfigProvider implements DefaultConfig {
    private String configContents = "";

    public List<Pair<String, ?>> getConfigsList() {
        return configsList;
    }

    private final List<Pair<String, ?>> configsList = new ArrayList<>();

    public void addKeyValuePair(Pair<String, ?> keyValuePair, String comment) {
        configsList.add(keyValuePair);
        configContents += keyValuePair.getFirst() + "=" + keyValuePair.getSecond() + " #" + comment + " | default: " + keyValuePair.getSecond() + "\n";
    }

    @Override
    public String get(String namespace) {
        return configContents;
    }
}
