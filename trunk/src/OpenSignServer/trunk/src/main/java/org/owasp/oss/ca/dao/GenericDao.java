package org.owasp.oss.ca.dao;

import java.io.Serializable;
import java.util.List;

/**
 * This is the interface for all Data Access Objects
 */
public interface GenericDao<T, ID extends Serializable> {

    T get(ID id);

    List<T> getAll();

    T store(T entity);

    void delete(T entity);
}