package de.aiocloud.library.versioneering;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Mär, 2019
 */
public class VersionHandler {

    private String currentversion;

    /**
     * This method load the current cloud version.
     */
    public VersionHandler() {
        this.currentversion = "1.0-ALPHA";
    }
    /**
     * GET method for @param(currentversion)
     */
    public String getCurrentVersion() {
        return currentversion;
    }
}
