package de.aiocloud.dispatcher.objects;

import de.aiocloud.AIOCloud;
import de.aiocloud.logger.LoggingType;

import java.io.IOException;

public class DispatcherSetup {

    private String hostaddress;
    private String firstworkername;
    private String firstworkeraddress;
    private String firstproxygroup;
    private Integer firstproxygroupramm;
    private String firstgroup;
    private Integer firstgroupramm;

    private Integer taskid;

    /**
     * This is the initial method of the 'DispatcherSetup' object.
     */
    public DispatcherSetup() {
        this.taskid = 0;
    }
    /**
     * This method starts the dispatcher-setup.
     */
    public void nextQuestion() {
        String line = null;
        try {
            switch(this.taskid) {
                case 0 : line = AIOCloud.reader.readLine("   AIOCloud > ");
                this.hostaddress = line;
                this.taskid = this.taskid +1;
                this.nextQuestion();
                break;

                case 1 : line = AIOCloud.reader.readLine("   AIOCloud > ");
                    this.firstworkername = line;
                    this.taskid = this.taskid +1;
                    this.nextQuestion();
                    break;

                case 2 : line = AIOCloud.reader.readLine("   AIOCloud > ");
                    this.firstworkeraddress = line;
                    this.taskid = this.taskid +1;
                    this.nextQuestion();
                    break;

                case 3 : line = AIOCloud.reader.readLine("   AIOCloud > ");
                    this.firstproxygroup = line;
                    this.taskid = this.taskid +1;
                    this.nextQuestion();
                    break;

                case 4 : line = AIOCloud.reader.readLine("   AIOCloud > ");
                try {
                    this.firstproxygroupramm = Integer.parseInt(line);
                    this.taskid = this.taskid +1;
                    this.nextQuestion();
                    break;
                } catch (NumberFormatException e) {
                    nextQuestion();
                }
            }
            hostaddress = line;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
