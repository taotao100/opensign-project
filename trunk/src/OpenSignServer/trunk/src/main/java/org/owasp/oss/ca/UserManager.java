/**
 * 
 */
package org.owasp.oss.ca;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.owasp.oss.ca.dao.DaoFactory;
import org.owasp.oss.ca.dao.UserDao;
import org.owasp.oss.ca.model.User;
import org.owasp.oss.httpserver.OSSHttpServer;

/**
 * Class for managing users within the system
 */
public class UserManager {
	
	private static Logger log = Logger.getLogger(UserManager.class);

	static UserManager _instance = new UserManager();
	DaoFactory daoFactory = DaoFactory.getInstance();
	UserDao _userDao = daoFactory.getUserDao();

	static public UserManager getInstance() {
		return _instance;
	}

	private UserManager() {
		init();
	}

	private void init() {
		log.info("Initializing UserManager");
		List<User> users = _userDao.getAll();
		Iterator<User> iter = users.iterator();
		while (iter.hasNext()) {
			User user = iter.next();
			registerUser(user);
		}

	}

	public void registerUser(User user) {
		log.info("User " + user.getUserName() + " registering");
		Transaction tx = DaoFactory.getInstance().getSession()
				.beginTransaction();
		try {
			CertificationAuthority ca = CertificationAuthority.getInstance();
			if (user.isIssuer())
				ca.createIssuer(user);
			OSSHttpServer.getInstance().registerOsResource(
					user.getResourceName());

			_userDao.store(user);
			tx.commit();
		} catch (CertificationAuthorityException e) {
			tx.rollback();
			log.error("User " + user.getUserName() + " could not register", e);			
		}
	}
	
	public void storeUser(User user) {
		log.info("Storing user " + user.getUserName());
		Transaction tx = DaoFactory.getInstance().getSession()
		.beginTransaction();
		_userDao.store(user);
		tx.commit();
	}
	
	public void deleteUser(User user) {
		log.info("Deleting user " + user.getUserName());
		Transaction tx = DaoFactory.getInstance().getSession()
		.beginTransaction();
		_userDao.delete(user);
		tx.commit();
	}	
	
	public int deleteUserByName(String userName) {
		log.info("Deleting user " + userName);
		Transaction tx = DaoFactory.getInstance().getSession()
		.beginTransaction();
		int rowsAffected = _userDao.deleteByUserName(userName);
		tx.commit();
		return rowsAffected;
	}

	public List<User> getAllUsers() {
		return _userDao.getAll();
	}

	public User getUser(String userName) {
		return _userDao.loadByUserName(userName);
	}

	public List<User> getAllIssuers() {
		return _userDao.loadIssuers();
	}

	public boolean checkCredentials(String userName, String password) {
		User user = _userDao.loadByUserName(userName);
		if (user != null && user.getPassword().equals(password)) {
			return true;
		}
		return false;
	}
	
	public List<User> getAllSubEntities(String resourcePath) {
		return _userDao.loadSubEntities(resourcePath);
	}
	
	public List<User> getAllUsersInPath(String resourcePath) {
		List<User> users = new Vector<User>();
		
		if (resourcePath == null)
			return users;
		StringTokenizer st = new StringTokenizer(resourcePath, "/");
		
		while (st.hasMoreElements()) {
			String userName = st.nextToken();
			users.add(_userDao.loadByUserName(userName));
		}
		return users;
	}
}
