package de.aiocloud.dispatcher.objects;

import de.aiocloud.AIOCloud;
import de.aiocloud.dispatcher.utils.FileManager;
import de.aiocloud.logger.LoggingType;

import java.io.IOException;

public class DispatcherSetup {

    private String hostaddress;
    private String firstworkername;
    private String firstproxygroup;
    private Integer firstproxygroupram;
    private String firstgroup;
    private Integer firstgroupram;
    private String minecraftversion;
    private String serverversion;
    private String proxyversion;

    private Integer taskid;

    /**
     * This is the initial method of the 'DispatcherSetup' object.
     */
    public DispatcherSetup() {
        this.taskid = 0;
        this.firstworkername = "Worker-1";
        this.nextQuestion();
    }
    /**
     * This method starts the dispatcher-setup.
     */
    public void nextQuestion() {
        AIOCloud.logginghandler.clear();
        String line = null;
        try {
            switch(this.taskid) {
                case 0 : AIOCloud.logginghandler.print("Please provide the dispatcher ip. (127.0.0.1)", LoggingType.INFO); line = AIOCloud.reader.readLine();
                this.hostaddress = line;
                this.taskid = this.taskid +1;
                this.nextQuestion();
                break;

                case 1 : AIOCloud.logginghandler.print("Please provide the name of the first proxy-group. (Proxy)", LoggingType.INFO); line = AIOCloud.reader.readLine();
                    this.firstproxygroup = line;
                    this.taskid = this.taskid +1;
                    this.nextQuestion();
                    break;

                case 2 : AIOCloud.logginghandler.print("Please provide the total ram of the first proxy-group. (512)", LoggingType.INFO); line = AIOCloud.reader.readLine();
                try {
                    this.firstproxygroupram = Integer.parseInt(line);
                    this.taskid = this.taskid +1;
                    this.nextQuestion();
                    break;
                } catch (NumberFormatException e) {
                    AIOCloud.logginghandler.print("Invalid answer. Please make it again.", LoggingType.WARNING);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    this.nextQuestion();
                    break;
                }

                case 3 : AIOCloud.logginghandler.print("Please provide the name of the first server-group. (Lobby)", LoggingType.INFO); line = AIOCloud.reader.readLine();
                    this.firstgroup = line;
                    this.taskid = this.taskid +1;
                    this.nextQuestion();
                    break;

                case 4 : AIOCloud.logginghandler.print("Please provide the total ram of the first server-group. (512)", LoggingType.INFO); line = AIOCloud.reader.readLine();
                    try {
                        this.firstgroupram = Integer.parseInt(line);
                        this.taskid = this.taskid +1;
                        this.nextQuestion();
                        break;
                    } catch (NumberFormatException e) {
                        AIOCloud.logginghandler.print("Invalid answer. Please make it again.", LoggingType.WARNING);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        this.nextQuestion();
                        break;
                    }

                case 5 : AIOCloud.logginghandler.print("Please provide and choose a minecraft-version:", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.7.10 (PAPER)", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.8", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.8.3", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.8.4", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.8.5", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.8.6", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.8.7", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.8.8 (PAPER)", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.9", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.9.2", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.9.4", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.10", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.10.2", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.11", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.11.1", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.11.2 (PAPER)", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.12", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.12.1", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.12.2 (PAPER)", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.13", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.13.1", LoggingType.INFO);
                    AIOCloud.logginghandler.print("1.13.2 (PAPER)", LoggingType.INFO);
                line = AIOCloud.reader.readLine();
                if(line.equalsIgnoreCase("1.7.10")
                        || line.equalsIgnoreCase("1.8")
                        || line.equalsIgnoreCase("1.8.3")
                        || line.equalsIgnoreCase("1.8.4")
                        || line.equalsIgnoreCase("1.8.5")
                        || line.equalsIgnoreCase("1.8.6")
                        || line.equalsIgnoreCase("1.8.7")
                        || line.equalsIgnoreCase("1.8.8")
                        || line.equalsIgnoreCase("1.9")
                        || line.equalsIgnoreCase("1.9.2")
                        || line.equalsIgnoreCase("1.9.4")
                        || line.equalsIgnoreCase("1.10")
                        || line.equalsIgnoreCase("1.10.2")
                        || line.equalsIgnoreCase("1.11")
                        || line.equalsIgnoreCase("1.11.1")
                        || line.equalsIgnoreCase("1.11.2")
                        || line.equalsIgnoreCase("1.12")
                        || line.equalsIgnoreCase("1.12.1")
                        || line.equalsIgnoreCase("1.12.2")
                        || line.equalsIgnoreCase("1.13")
                        || line.equalsIgnoreCase("1.13.1")
                        || line.equalsIgnoreCase("1.13.2")) {
                    this.minecraftversion = line;
                    this.taskid = this.taskid +1;
                    this.nextQuestion();
                    break;
                } else {
                    AIOCloud.logginghandler.print("Invalid answer. Please check your answer.", LoggingType.WARNING);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.nextQuestion();
                    break;
                }
                case 6 : AIOCloud.logginghandler.print("Please provide the server-version. (SPIGOT or PAPER)", LoggingType.INFO);
                line = AIOCloud.reader.readLine();
                if(line.equalsIgnoreCase("SPIGOT")) {
                    if(this.minecraftversion.equalsIgnoreCase("1.7.10")
                            || this.minecraftversion.equalsIgnoreCase("1.8")
                            || this.minecraftversion.equalsIgnoreCase("1.8.3")
                            || this.minecraftversion.equalsIgnoreCase("1.8.4")
                            || this.minecraftversion.equalsIgnoreCase("1.8.5")
                            || this.minecraftversion.equalsIgnoreCase("1.8.6")
                            || this.minecraftversion.equalsIgnoreCase("1.8.7")
                            || this.minecraftversion.equalsIgnoreCase("1.8.8")
                            || this.minecraftversion.equalsIgnoreCase("1.9")
                            || this.minecraftversion.equalsIgnoreCase("1.9.2")
                            || this.minecraftversion.equalsIgnoreCase("1.9.4")
                            || this.minecraftversion.equalsIgnoreCase("1.10")
                            || this.minecraftversion.equalsIgnoreCase("1.10.2")
                            || this.minecraftversion.equalsIgnoreCase("1.11")
                            || this.minecraftversion.equalsIgnoreCase("1.11.1")
                            || this.minecraftversion.equalsIgnoreCase("1.11.2")
                            || this.minecraftversion.equalsIgnoreCase("1.12")
                            || this.minecraftversion.equalsIgnoreCase("1.12.1")
                            || this.minecraftversion.equalsIgnoreCase("1.12.2")
                            || this.minecraftversion.equalsIgnoreCase("1.13")
                            || this.minecraftversion.equalsIgnoreCase("1.13.1")
                            || this.minecraftversion.equalsIgnoreCase("1.13.2")) {
                        this.serverversion = line;
                        this.taskid = this.taskid +1;
                        this.nextQuestion();
                        break;
                    } else {
                        AIOCloud.logginghandler.print("Invalid answer. PAPER is not running with the version: " + this.minecraftversion, LoggingType.WARNING);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        this.nextQuestion();
                        break;
                    }
                } else if(line.equalsIgnoreCase("PAPER")) {
                    if(this.minecraftversion.equalsIgnoreCase("1.7.10")
                    || this.minecraftversion.equalsIgnoreCase("1.8.8")
                    || this.minecraftversion.equalsIgnoreCase("1.11.2")
                    || this.minecraftversion.equalsIgnoreCase("1.12.2")
                    || this.minecraftversion.equalsIgnoreCase("1.13.2")) {
                        this.serverversion = line;
                        this.taskid = this.taskid +1;
                        this.nextQuestion();
                        break;
                    } else {
                        AIOCloud.logginghandler.print("Invalid answer. PAPER is not running with the version: " + this.minecraftversion, LoggingType.WARNING);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        this.nextQuestion();
                        break;
                    }
                }

                case 7 : AIOCloud.logginghandler.print("Please provide the proxy-version. (BUNGEECORD or WATERFALL)", LoggingType.INFO);
                    line = AIOCloud.reader.readLine();
                    if(line.equalsIgnoreCase("BUNGEECORD") || line.equalsIgnoreCase("WATERFALL")) {
                        this.proxyversion = line;
                        this.taskid = this.taskid +1;
                        FileManager.createDispatcherConfig(this.hostaddress, this.minecraftversion, this.serverversion, this.proxyversion);
                        FileManager.createProxyGroup(this.firstproxygroup, "Worker-1", this.firstproxygroupram, 1, -1, true);
                        AIOCloud.logginghandler.clear();
                        AIOCloud.logginghandler.print("Installation completed successfully.", LoggingType.INFO);
                        AIOCloud.logginghandler.print("In the 'config.json'-file you can change all data again.", LoggingType.INFO);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        AIOCloud.logginghandler.clear();
                        break;
                    } else {
                        AIOCloud.logginghandler.print("Invalid answer. Please check your answer.", LoggingType.WARNING);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        this.nextQuestion();
                        break;
                    }
            }
            hostaddress = line;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}