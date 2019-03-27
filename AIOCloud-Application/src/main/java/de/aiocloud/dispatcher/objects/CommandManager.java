package de.aiocloud.dispatcher.objects;

import java.util.HashMap;

import de.aiocloud.dispatcher.utils.Command;

public class CommandManager {
	
	private HashMap<String, Command> commands;
	
	/**
	 * This is the initial method of this object.
	 */
	public CommandManager() {
		this.commands = new HashMap<String, Command>();
	}
	/**
	 * This method add a Command to {@link commands}.
	 * @param cmd is the command to add.
	 * @param key first word in the command.
	 */
	public void addCommand(String key, Command cmd) {
		this.commands.put(key, cmd);
	}
	/**
	 * This method remove an command from {@link commands}.
	 * @param key first word from the command.
	 */
	public void removeCommand(String key) {
		this.commands.remove(key);
	}
	/**
	 * This method clear all commands.
	 */
	public void clear() {
		this.commands.clear();
	}
	/**
	 * GET method.
	 * @return a hashmap.
	 */
	public HashMap<String, Command> getMap() {
		return this.commands;
	}
}