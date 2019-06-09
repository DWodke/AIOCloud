package de.aiocloud.proxyapp.library.time;

import de.aiocloud.proxyapp.Main;
import de.aiocloud.proxyapp.logger.LoggingType;

public class TimeHandler {

    /**
     * This method sleeps the main-thread.
     * @param millis of the sleep.
     */
    public static void sleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Main.logginghandler.print("ERROR while sleep this main-thread.", LoggingType.ERROR);
        }
    }
}
