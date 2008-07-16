/**
 * 
 */
package org.owasp.oss.ca;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Class for managing users
 */
public class UserManager {
	
	static UserManager _instance = new UserManager();
	
	Map<String, User> _userMap = null;
	List<User> _userList = null;
	
	static public UserManager getInstance(){
		return _instance;
	}
	
	private UserManager() {
		_userMap = new HashMap<String, User>();
		_userList = new Vector<User>();
		dummyInit();
	}
	
	// TODO: Remove this and load Users from DB
	private void dummyInit(){
		try {
			User u1 = new User("user1", "123", "root");
			User u2 = new User("user2", "123", "root");
			User u3 = new User("user3", "123", "root");
			
			u3.setIssuer(true);
			CertificationAuthority.getInstance().registerIssuer(u3.getResourcePathAndName());		
			
			registerUser(u1);
			registerUser(u2);
			registerUser(u3);
		} catch (CertificationAuthorityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void registerUser(User user) {
		_userMap.put(user.getUserName(), user);
		_userList.add(user);
	}
	
	public List<User> getAllUsers() {
		return _userList;
	}
	
	public User getUser(String userName){
		return _userMap.get(userName);
	}
	
	public boolean checkCredentials(String username, String password) {	
		User user = _userMap.get(username);
		if (user != null &&user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}	
	
	
}
