package de.aiocloud.library.connection;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Download {

    private String url;
    private String output;
    private File downloadedfile;

    /**
     * This is the initial method of the 'Download' object.
     * @param url is the url from downloaded file.
     * @param output is the file path.
     */
    public Download(String url, String output) {
        this.url = url;
        this.output = output;
        try {
            URL website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(output);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            this.downloadedfile = new File(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
