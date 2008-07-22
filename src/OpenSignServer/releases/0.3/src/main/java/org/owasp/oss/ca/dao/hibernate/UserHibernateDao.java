package org.owasp.oss.ca.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.owasp.oss.ca.dao.UserDao;
import org.owasp.oss.ca.model.User;

/**
 * Implementation of UserDao
 */
public class UserHibernateDao extends GenericHibernateDao<User, Long> implements
		UserDao {

	public UserHibernateDao(Session session) {
		super(session);
	}

	public User loadByUserName(String userName) {
		return getUnique("userName", userName);
	}

	public List<User> loadIssuers() {
		return getAll("issuer", true, "resourceName");
	}

}
