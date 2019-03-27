package de.aiocloud;

import de.aiocloud.logger.LoggingBuilder;
import de.aiocloud.logger.LoggingType;

public class AIOCloud {

    /**
     * This method create a new instance of AIOCloud-Application.
     *
     * @param args is for teh start-options from the system.
     */
    public static void main(String[] args) {
        System.out.print("  AIOCloud > ");
        new LoggingBuilder(LoggingType.WARNING, "The application can't load...").print();
        new LoggingBuilder(LoggingType.WARNING, "The application can't load...").print();
        new LoggingBuilder(LoggingType.WARNING, "The application can't load...").print();
        new LoggingBuilder(LoggingType.WARNING, "The application can't load...").print();
        while (true) {

        }
    }
}