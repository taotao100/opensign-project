package org.owasp.oss.client;

import java.security.Security;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.owasp.oss.client.command.CommandCertificateSignRequest;
import org.owasp.oss.client.command.CommandGetCertificate;
import org.owasp.oss.client.command.CommandVerifyChain;
import org.owasp.oss.client.command.CommandInterface;

public class OSSJClientMain {
	
	private static Map<String, CommandInterface> _commands;	

	private static void init() {
		
		Security.addProvider(new BouncyCastleProvider());
		
		_commands  = new HashMap<String, CommandInterface>();
		_commands.put("csr", new CommandCertificateSignRequest());
		_commands.put("getcert", new CommandGetCertificate());
		_commands.put("verifycert", new CommandVerifyChain());
	}
	
	private static Map<String, String> buildParameterMap(String[] args) {
		Map<String, String> parameter  = new HashMap<String, String>();
		for (int i = 0; i < args.length; ++i){	
			if (args[i] != null && args[i].startsWith("-")) {
				parameter.put(args[i].replaceFirst("-", ""), args[i + 1]);
				++i;
			}
		}
		return parameter;
	}
	
	public static void main(String[] args) {
		System.out.println("Opensign Java Client Tool started");
		init();
		
		CommandInterface command = _commands.get(args[0]);
		
		if (command != null) {
			command.execute(buildParameterMap(args));
		} else {
			Set<String> keys = _commands.keySet();
			Iterator<String> i = keys.iterator();
			while (i.hasNext()) {
				String key = i.next();
				System.out.println(key + " - " + _commands.get(key).getDescription());
			}
		}
		
	}

}
