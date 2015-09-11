package edu.pragmatic.hibernate.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;


import edu.pragmatic.hibernate.demo.config.HibernateUtils;
import edu.pragmatic.hibernate.demo.dao.Dao;
import edu.pragmatic.hibernate.demo.dao.HibernateDao;
import edu.pragmatic.hibernate.demo.model.Address;
import edu.pragmatic.hibernate.demo.model.Email;
import edu.pragmatic.hibernate.demo.model.Employee;

public class TestCachedQueries {
	
	private static final Log log = LogFactory.getLog(TestCachedQueries.class);
	
//	@Test
	public void testQueries(){
		String hql = "from Person";
		Query notCachedQuery = HibernateUtils.createHQlQuery(hql);
		long startQNoCache = System.currentTimeMillis();	
		notCachedQuery.list();
		long endQNoCache = System.currentTimeMillis();
		long timeSpendQNoCache = endQNoCache - startQNoCache ;
		
		log.debug(String.format("It took %d ms for no cache query[%s]",timeSpendQNoCache,hql));
		
		Query cachedInit = HibernateUtils.createHQlQuery(hql);
		// execute the query once to cache it
		cachedInit.setCacheable(true).list();
		
		Query cachedQ = HibernateUtils.createHQlQuery(hql);
		long startCachedQ = System.currentTimeMillis();
		cachedQ.setCacheable(true).list();
		long endCachedQ = System.currentTimeMillis();
		long timeSpendCachedQ = endCachedQ - startCachedQ;
		
		log.debug(String.format("It took %d ms for cached query[%s]",timeSpendCachedQ, hql));
		Assert.assertTrue(timeSpendCachedQ < timeSpendQNoCache);
		
		// clean up
		HibernateUtils.evictQueries();
	}
	
	@Test
	public void testSecondLevelCache(){
		// get Employee (it's a bigger graph)
		Session sess = HibernateUtils.openSession();
		final Dao<Employee> dao = new HibernateDao<>(Employee.class, sess);
		Employee employee = employee();
		dao.save(employee);
		final Long id = employee.getId();
		boolean isInCache = sess.getSessionFactory().getCache().containsEntity(Employee.class, employee.getId());
		log.info(String.format("Entity %s[%d] is %s in cache", Employee.class.getName(), employee.getId(), (isInCache ? "present" : "NOT present") ));
		
		sess.getSessionFactory().getCache().evictEntity(Employee.class, id); // clear second level cache
		sess.evict(employee); // clear entity cache
		sess.close();
		// get from DB
		final Dao<Employee> dao1 = new HibernateDao<>(Employee.class, HibernateUtils.openSession());
		long start = System.currentTimeMillis();
		dao1.get(id);
		long end = System.currentTimeMillis();
		long dbGetTook = end - start;
		
		int sessionCount = 500;
		// List<String> errorMessages = new ArrayList<>(sessionCount);
		Long sigma = 0L;
		Long slowerTransactionsCount = 0L;
		for (int i =0 ; i < sessionCount; i++){
			Session session = HibernateUtils.openSession();
			session.setCacheMode(CacheMode.NORMAL);
			Dao<Employee> daoCached = new HibernateDao<>(Employee.class, session);
			long start1 = System.currentTimeMillis();
			daoCached.get(id);
			long end1 = System.currentTimeMillis();
			Long cacheGetTook = ( end1 - start1 ) ;
			sigma += cacheGetTook;
			final String message = "With session["+ i +"] we've got cachedGet in [" + cacheGetTook + "ms] and dbGet in[" + dbGetTook + "ms]";
			log.debug(message);
			if (cacheGetTook > dbGetTook){
				slowerTransactionsCount++;
			}
			session.close();
		}
		
		Long averageTimeSpent = sigma/sessionCount ;
		log.debug("Average time spent ["+ averageTimeSpent +"ms] non cached time spend ["+ dbGetTook +"ms]");
		log.debug( String.format("There were [%d] transactions with retrieval time slower than the non cached version out of [%d]", slowerTransactionsCount, sessionCount) );
		if (averageTimeSpent > dbGetTook){
			Assert.fail("Getting objects from the second level cache is for some reason longer");
		}
		
		// clean up
		log.info("Cleaning up");
		dao1.delete(employee);
	}
	
	private Employee employee() {
		Employee employee = new Employee();
		employee.setAge(26);
		employee.setDivision("117th");
		employee.setLastName("Smith");
		employee.setName("John");
		Address addrs = new Address();
		addrs.setCity("London");
		addrs.setCountryIso(Address.Country.EN);
		addrs.setPostCode("EC1A 4HD");
		addrs.setStreet("200 Aldersgate Street");
		employee.setAddress(addrs);
		Email email = new Email();
		email.setCreatedOn(new Date());
		email.setOwner(employee);
		email.setUrl("john.smith@javelingroup.com");
		List<Email> emails = new ArrayList<>(1);
		employee.setEmails(emails);
		employee.getEmails().add(email);
		return employee;
	}
	
}
