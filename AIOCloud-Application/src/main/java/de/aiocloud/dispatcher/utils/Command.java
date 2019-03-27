package de.aiocloud.dispatcher.utils;

public interface Command {

	public void execute(String[] args);
	public String usage();
}
