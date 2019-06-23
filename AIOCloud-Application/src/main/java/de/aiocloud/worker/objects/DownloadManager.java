package de.aiocloud.worker.objects;

import de.aiocloud.AIOCloud;
import de.aiocloud.library.connection.Download;
import de.aiocloud.logger.LoggingType;

import java.io.File;

public class DownloadManager {

    private String proxyapiurl;

    /**
     * This is the initial method of the 'DownloadManager' object.
     */
    public DownloadManager() {
        File proxyapifile = new File("./worker/files/proxy-api.jar");
        if(proxyapifile.exists()) {
            //proxyapifile.delete();
        }
        AIOCloud.logginghandler.print("Trying to download proxy-api.jar...", LoggingType.INFO);
        //new Download("https://file.io/DiYkPS", "./worker/files/proxy-api.jar");
        AIOCloud.logginghandler.print("Download complete!", LoggingType.INFO);
    }
}
