package de.aiocloud.worker;

import de.aiocloud.AIOCloud;
import de.aiocloud.logger.LoggingType;
import de.aiocloud.worker.objects.DispatcherObject;
import de.aiocloud.worker.objects.ServerVersion;
import de.aiocloud.worker.objects.WorkerSetup;
import de.aiocloud.worker.utils.FileManager;

public class Worker {

	public static DispatcherObject dispatcher;

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
		for(int i = 0; i < 5; i++) {
			System.out.println(" ");
		}
		AIOCloud.logginghandler.print("Told worker to load all components...", LoggingType.INFO);
		for(int i = 0; i < 2; i++) {
			System.out.println(" ");
		}
		dispatcher = new DispatcherObject(FileManager.config.getDispatcherAddress(), FileManager.config.getDispatcherPort());
	}
}