package de.aiocloud.worker.objects;

import de.aiocloud.AIOCloud;
import de.aiocloud.logger.LoggingType;
import de.aiocloud.worker.utils.FileManager;

import java.io.IOException;

public class WorkerSetup {

    private String workername;
    private String hostaddress;
    private String dispatcheraddress;
    private Integer dispatcherport;
    private String workerid;
    private Integer totalram;

    private Integer taskid;

    /**
     * This is the initial method of the 'DispatcherSetup' object.
     */
    public WorkerSetup() {
        this.taskid = 0;
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
                case 0 : AIOCloud.logginghandler.print("Please provide the name of this worker. (Worker-1)", LoggingType.INFO); line = AIOCloud.reader.readLine();
                    this.workername = line;
                    this.taskid = this.taskid +1;
                    this.nextQuestion();
                    break;

                case 1 : AIOCloud.logginghandler.print("Please provide the host-address of the worker. (127.0.0.1)", LoggingType.INFO); line = AIOCloud.reader.readLine();
                    this.hostaddress = line;
                    this.taskid = this.taskid +1;
                    this.nextQuestion();
                    break;

                case 2 : AIOCloud.logginghandler.print("Please provide the address from the dispatcher. (127.0.0.1)", LoggingType.INFO); line = AIOCloud.reader.readLine();
                    this.dispatcheraddress = line;
                    this.taskid = this.taskid +1;
                    this.nextQuestion();
                    break;

                case 3 : AIOCloud.logginghandler.print("Please provide the port of the dispatcher. (12000)", LoggingType.INFO); line = AIOCloud.reader.readLine();
                try {
                    this.dispatcherport = Integer.parseInt(line);
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

                case 4 : AIOCloud.logginghandler.print("Please provide the auth-token from the dispatcher screen. (UUID)", LoggingType.INFO); line = AIOCloud.reader.readLine();
                    this.workerid = line;
                    this.taskid = this.taskid +1;
                    this.nextQuestion();
                    break;

                case 5 : AIOCloud.logginghandler.print("Please provide the total ram of this worker. (1024)", LoggingType.INFO); line = AIOCloud.reader.readLine();
                    try {
                        this.totalram = Integer.parseInt(line);
                        this.taskid = this.taskid +1;
                        FileManager.createWorkerConfig(this.workername, this.hostaddress, this.dispatcheraddress, this.dispatcherport, this.workerid, this.totalram);
                        FileManager.loadWorkerConfig();
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
            }
            hostaddress = line;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
