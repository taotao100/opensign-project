package org.owasp.oss.ca.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
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
	
	public List<User> loadSubEntities(String resourceName) {
		return getAll("resourcePath", resourceName, "resourceName");
	}

	@Override
	public int deleteByUserName(String userName) {
        String hql = "delete from User where userName = :name";
        Query query = _session.createQuery(hql);
        query.setString("name", userName);
        return query.executeUpdate();
	}

	@Override
	public User store(User entity) {
		User userToUpdate = loadByUserName(entity.getUserName());
		if (userToUpdate != null)
			entity.setId(userToUpdate.getId());
		return super.store(entity);
	}

}
