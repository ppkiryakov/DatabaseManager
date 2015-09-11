package edu.pragmatic.hibernate.demo.config.programatic;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public final class HibernateUtils {
	
	private static Session currentSession;
	private static Configuration conf;
	
	static {
	 	File configFile = new File("src/main/resources/db/hibernate.cfg.xml");
		Configuration configuration = new Configuration();
		SessionFactory sessionFactory = configuration.configure(configFile).buildSessionFactory();
		currentSession = sessionFactory.openSession();
	}
	
	public static Session getCurrentSession(){
		return currentSession ;
	}
	
	public static Transaction beginTransaction(){
		return getCurrentSession().beginTransaction();
	} 
	
	

}
