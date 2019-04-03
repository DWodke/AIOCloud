package de.aiocloud.library.time;

import de.aiocloud.AIOCloud;
import de.aiocloud.logger.LoggingType;

public class TimeHandler {

    /**
     * This method sleeps the main-thread.
     */
    public static void sleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            AIOCloud.logginghandler.print("ERROR while sleep this main-thread.", LoggingType.ERROR);
        }
    }
}
