package org.owasp.oss.ca.dao;


import org.hibernate.Session;
import org.owasp.oss.ca.dao.hibernate.HibernateDaoFactory;

/**
 * This class defines the interface to access all DAOs available and,
 * furthermore, is responsible for session management
 */
public abstract class DaoFactory {

	// Set the Hibernate implementation
	private static DaoFactory _instance = new HibernateDaoFactory();

	public static DaoFactory getInstance() {
		return _instance;
	}

	public abstract Session getSession();

	public abstract UserDao getUserDao();

}