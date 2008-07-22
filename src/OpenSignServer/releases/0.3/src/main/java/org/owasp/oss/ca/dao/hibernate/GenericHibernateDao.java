package org.owasp.oss.ca.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.owasp.oss.ca.dao.GenericDao;

/**
 * Generic, Hibernate specific, implementation of GenericDao interface 
 */
public abstract class GenericHibernateDao<T, ID extends Serializable>
		implements GenericDao<T, ID> {

	private Session _session;
	private Class<T> _persistentClass;

	public GenericHibernateDao(Session session) {
		_session = session;
		_persistentClass = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Session getSession() {
		return _session;
	}

	public void setSession(Session session) {
		this._session = session;
	}

	@Override
	public void delete(T entity) {
		_session.delete(entity);
	}

	@Override
	public T get(ID id) {
		return (T) _session.get(_persistentClass, id);
	}

	@Override
	public List<T> getAll() {
		Criteria criteria = _session.createCriteria(_persistentClass);
		return criteria.list();
	}

	@Override
	public T store(T entity) {
		return (T) _session.merge(entity);
	}

	@SuppressWarnings("unchecked")
	public T getUnique(String propertyName, Object value) {
		return (T) _session.createCriteria(_persistentClass).add(
				Restrictions.like(propertyName, value)).uniqueResult();	
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll(String propertyName, Object value, String orderPropertyName) {
		return (List<T>) _session.createCriteria(_persistentClass).add(
				Restrictions.eq(propertyName, value)).addOrder(
				Order.asc(orderPropertyName)).list();		 
	}
}
