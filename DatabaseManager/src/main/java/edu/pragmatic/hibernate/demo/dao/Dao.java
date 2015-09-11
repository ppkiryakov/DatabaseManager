package edu.pragmatic.hibernate.demo.dao;

import org.hibernate.Session;

public interface Dao<T> {

	T get(Long id);

	T load(Long id);

	void delete(T o);

	void update(T o);

	void save(T o);
	
	Session getSession();

}
