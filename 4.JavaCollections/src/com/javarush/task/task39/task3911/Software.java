package com.javarush.task.task39.task3911;

import java.util.*;

public class Software {
    int currentVersion;

    private Map<Integer, String> versionHistoryMap = new LinkedHashMap<>();

    public void addNewVersion(int version, String description) {
        if (version > currentVersion) {
            versionHistoryMap.put(version, description);
            currentVersion = version;
        }
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public Map<Integer, String> getVersionHistoryMap() {
        return Collections.unmodifiableMap(versionHistoryMap);
    }

    public boolean rollback(int rollbackVersion) {
        boolean result = false;
        if (versionHistoryMap.containsKey(rollbackVersion)) {
            versionHistoryMap.entrySet().removeIf(integerStringEntry -> integerStringEntry.getKey() > rollbackVersion);
            currentVersion = rollbackVersion;
            result = true;
        }
        return result;
    }
}
