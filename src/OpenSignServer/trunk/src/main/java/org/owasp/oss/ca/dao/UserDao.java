package org.owasp.oss.ca.dao;

import java.util.List;

import org.owasp.oss.ca.model.User;

/**
 * Interface for the Data Access Object for the model "User"
 */
public interface UserDao extends GenericDao<User, Long>{
	
	public User loadByUserName(String userName);
	
	public List<User> loadIssuers();
	
	public List<User> loadSubEntities(String resourceName);

	public int deleteByUserName(String userName);

}