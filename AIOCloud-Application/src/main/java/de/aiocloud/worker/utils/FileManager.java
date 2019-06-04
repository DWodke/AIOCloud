package de.aiocloud.worker.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.aiocloud.worker.objects.Config;

import java.io.*;

public class FileManager {

    public static File maindir;
    public static File workerconfigfile;
    public static File tempdir;
    public static File filesdir;
    public static File templatedir;

    private static Gson gson;
    private static JsonParser parser;
    public static Config config;

    /**
     * This is the initial method of the class.
     */
    public static Boolean load() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        parser = new JsonParser();
        Boolean returnBoolean = false;
        try {
            maindir = new File("./worker");
            if(!maindir.exists()) {
                returnBoolean = true;
                maindir.mkdir();
            }
            workerconfigfile = new File("./config.json");
            if(!workerconfigfile.exists()) {
                returnBoolean = true;
                workerconfigfile.createNewFile();
            }
            tempdir = new File("./worker/temp");
            if(!tempdir.exists()) {
                tempdir.mkdir();
            }
            filesdir = new File("./worker/files");
            if(!filesdir.exists()) {
                filesdir.mkdir();
            } else {
                File proxyjar = new File("./worker/files/proxy.jar");
                if(proxyjar.exists()) {
                    proxyjar.delete();
                }
                File serverjar = new File("./worker/files/server.jar");
                if(serverjar.exists()) {
                    serverjar.delete();
                }
            }
            templatedir = new File("./worker/templates");
            if(!templatedir.exists()) {
                templatedir.mkdir();
            }
        } catch (Exception e) {
            returnBoolean = true;
        }
        return returnBoolean;
    }
    /**
     * Create config.json from the worker.
     */
    public static void createWorkerConfig(String name, String host, String dispatcherhost, Integer dispatcherport, String workerid, Integer totalram) {
        try {
            FileWriter writer = new FileWriter(workerconfigfile);
            JsonObject json = new JsonObject();
            json.addProperty("name", name);
            json.addProperty("hostAddress", host);
            json.addProperty("dispatcherAddress", dispatcherhost);
            json.addProperty("dispatcherPort", dispatcherport);
            json.addProperty("authToken", workerid);
            json.addProperty("totalRam", totalram);
            writer.write(gson.toJson(json));
            writer.flush();
            writer.close();
            loadWorkerConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Load config.json from the worker.
     */
    public static void loadWorkerConfig() {
        try {
            JsonReader reader = new JsonReader(new FileReader(workerconfigfile));
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            config = new Config(json);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method deletes directories
     * @param file
     * @throws IOException
     */
    public static void deleteDir(File file) throws IOException {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDir(entry);
                }
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }
    }
    /**
     * This method copy an file to a directory.
     * @param source to copy.
     * @param dest output.
     * @throws IOException
     */
    public static void copyFile(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
}