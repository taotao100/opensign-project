/**
 * 
 */
package org.owasp.oss.ca;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	private void dummyInit(){
		User u1 = new User();
		u1.setUserName("user1");
		u1.setPassword("123");
		User u2 = new User();
		u2.setUserName("user2");
		u2.setPassword("123");
		User u3 = new User();
		u3.setUserName("user3");
		u3.setPassword("123");
		
		registerUser(u1);
		registerUser(u2);
		registerUser(u3);
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
