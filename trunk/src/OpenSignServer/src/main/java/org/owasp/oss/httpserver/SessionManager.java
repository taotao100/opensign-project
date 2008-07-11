/**
 * 
 */
package org.owasp.oss.httpserver;

import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpPrincipal;

/**
 * This class maintains the active sessions
 */
public class SessionManager {
	Map<String, HttpPrincipal> _sessions = null;
	
	static Integer counter = 10000;
	
	private static SessionManager _instance = new SessionManager();
	
	private SessionManager(){
		_sessions = new HashMap<String, HttpPrincipal>();
	}
	
	public static SessionManager getInstance() {
		return _instance;
	}
	
	public static String generateSessionId() {
		//TODO: use a RNG		
		counter++;
		return counter.toString();
	}
	
	public HttpPrincipal getHttpPrincipal(String sessionId){
		return _sessions.get(sessionId);
	}
	
	public void addSession(String sessionId, HttpPrincipal principal) {
		_sessions.put(sessionId, principal);
	}
	
	public void removeSession(String sessionId) {
		_sessions.remove(sessionId);
	}
}
