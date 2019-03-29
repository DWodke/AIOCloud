package de.aiocloud.worker;

import de.aiocloud.worker.objects.WorkerSetup;
import de.aiocloud.worker.utils.FileManager;

public class Worker {

	/**
	 * This is the initial method of the worker.
	 */
	public static void startWorkerComponent() {
		if(FileManager.load()) {
			new WorkerSetup();
		} else {
			FileManager.loadWorkerConfig();
		}
	}
}
