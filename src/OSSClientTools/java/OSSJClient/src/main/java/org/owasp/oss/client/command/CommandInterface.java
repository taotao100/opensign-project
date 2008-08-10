package org.owasp.oss.client.command;

import java.util.Map;

public interface CommandInterface {

	public abstract void execute(Map<String, String> parameters);
	
	public abstract void printHelp();
	
	public abstract String getDescription();
	
}