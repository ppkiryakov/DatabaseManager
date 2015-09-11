package edu.pragmatic.hibernate.demo.dao;

import java.io.Serializable;

import javax.persistence.Entity;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.pragmatic.hibernate.demo.config.HibernateUtils;

/**
 * A utility class providing simple CRUD operations in a more generic way
 * 
 * @author peterm1
 * @param <T> the persistent object class. It should be class annotated with the {@link Entity} annotation
 */
@SuppressWarnings("unchecked")
public class HibernateDao<T extends Serializable> implements Dao<T> {
	
	private Class<? extends T> persistentClass;
	private Session session = null;
	
	public HibernateDao(Class<T> persistentClass, Session session) {
		if (persistentClass == null){
			throw new IllegalArgumentException("HibernateDao wasn't correctly configured");
		}
		this.persistentClass = persistentClass;
		
		this.session = session;
	}
	
	public HibernateDao(Class<T> persistentClass) {
		this(persistentClass, HibernateUtils.openSession());
	}
	
	/**
	 * Persist an object to the db
	 * @param o the object to persist
	 */
	@Override
	public void save(T o){
		Transaction t = HibernateUtils.beginTransaction();
		this.session.save(o);
		t.commit();
	} 
	
	/**
	 * Update a persistent object
	 * 
	 * @param o
	 */
	@Override
	public void update(T o){
		Transaction t = HibernateUtils.beginTransaction();
		this.session.update(o);
		t.commit();
	} 
	
	
	/**
	 * Remove an object from the DB
	 * 
	 * @param o the object to remove
	 */
	@Override
	public void delete(T o) {
		Transaction t = HibernateUtils.beginTransaction();
		this.session.delete(o);
		t.commit();
	} 
	
	/** 
	 * @param id the object identifier ( the PK from the DB)
	 * @return a proxy to the object. To initialised it you can use 
	 *           {@link Hibernate#initialize(Object)} or simply call a method of the model object
	 *           However be careful as the method works only with hibernate proxies.   
	 */
	@Override
	public T load(Long id) {
		 HibernateUtils.beginTransaction();
		 return (T) this.session.get(this.persistentClass, id);
	}
	
	/**
	 * @param id the object identifier ( the PK from the DB)
	 * @return the actual initialized object, the object is detached
	 */
	@Override
	public T get(Long id) {
		Transaction t = HibernateUtils.beginTransaction();
		Object o = this.session.get(this.persistentClass, id);
		t.commit();
		return (T) o;
	}

	@Override
	public Session getSession() {
		return this.session;
	}

}
