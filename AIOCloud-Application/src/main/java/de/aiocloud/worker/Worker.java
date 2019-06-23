package de.aiocloud.worker;

import de.aiocloud.AIOCloud;
import de.aiocloud.logger.LoggingType;
import de.aiocloud.worker.objects.*;
import de.aiocloud.worker.utils.FileManager;

public class Worker {

	public static DispatcherObject dispatcher;
	public static ProxyManager proxymanager;
	public static Queue queue;

	/**
	 * This is the initial method of the worker.
	 */
	public static void startWorkerComponent() {
		if(FileManager.load()) {
			new WorkerSetup();
		} else {
			FileManager.loadWorkerConfig();
		}
		AIOCloud.printHeader();
		for(int i = 0; i < 7; i++) {
			if(i < 5 || i > 5) {
				System.out.println(" ");
			} else {
				AIOCloud.logginghandler.print("Told worker to load all components...", LoggingType.INFO);
			}
		}
		proxymanager = new ProxyManager();
		queue = new Queue();
		dispatcher = new DispatcherObject(FileManager.config.getDispatcherAddress(), FileManager.config.getDispatcherPort());
	}
}