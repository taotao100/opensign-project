package org.owasp.oss.ca.dao.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.owasp.oss.Configuration;
import org.owasp.oss.ca.dao.DaoFactory;
import org.owasp.oss.ca.dao.UserDao;

/**
 * Hibernate specific implementation of DaoFactory
 */
public class HibernateDaoFactory extends DaoFactory {

	private static Logger log = Logger.getLogger(HibernateDaoFactory.class);

	private static SessionFactory sessionFactory = null;
	static {
		if (Configuration.getInstance().getValue("STORAGE_TYPE")
				.equals("MYSQL")) {
			log.info("Storage type: MYSQL - configuration file: hibernate.cfg.xml");
			sessionFactory = new AnnotationConfiguration().configure()
					.buildSessionFactory();
		} else {
			log.info("Storage type: MEMORY - configuration file: hibernate_memory.cfg.xml");
			sessionFactory = new AnnotationConfiguration().configure(
					"hibernate_memory.cfg.xml").buildSessionFactory();
		}
	}

	Session _session;

	public HibernateDaoFactory() {
		log.info("New session opened");
		_session = sessionFactory.openSession();
	}

	public Session getSession() {
		return _session;
	}

	public UserDao getUserDao() {
		return new UserHibernateDao(_session);
	}
}
