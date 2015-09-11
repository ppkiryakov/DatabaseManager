package edu.pragmatic.hibernate.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import edu.pragmatic.hibernate.demo.dao.HibernateDao;
import edu.pragmatic.hibernate.demo.model.Address;
import edu.pragmatic.hibernate.demo.model.Email;
import edu.pragmatic.hibernate.demo.model.Employee;
import edu.pragmatic.hibernate.demo.model.Person;
import edu.pragmatic.hibernate.demo.model.Project;


public class SimpleObjectCRUDTest {
	
	private List<Address> addresses;
	private HibernateDao<Address> addressDao;
	
	private static final Log log = LogFactory.getLog(SimpleObjectCRUDTest.class);

	@Before
	public void setUp(){
		this.addressDao = new HibernateDao<>(Address.class);
		this.addresses = new ArrayList<>(3);
		Address addrs1 = new Address();
		addrs1.setCity("City1");
		addrs1.setCountryIso(Address.Country.BG);
		addrs1.setPostCode("qwrrt");
		addrs1.setStreet("Street 1");
		this.addresses.add(addrs1);
		
		Address addrs2 = new Address();
		addrs2.setCity("City2");
		addrs2.setCountryIso(Address.Country.EN);
		addrs2.setPostCode("qwrrt2");
		addrs2.setStreet("Street 2");
		this.addresses.add(addrs2);
		
		Address addrs3 = new Address();
		addrs3.setCity("City3");
		addrs3.setCountryIso(Address.Country.AU);
		addrs3.setPostCode("qwrrt3");
		addrs3.setStreet("Street 3");
		this.addresses.add(addrs3);
		
		log.info(String.format("Created test data. We have %d addresses to insert", addresses.size()));
	} 
	
	@Test
	public void insertSomeAddressesInTheDB(){
		for(Address address : this.addresses){
			addressDao.save(address);
		}
		
		// if the object were indeed persisted to the db then 
		// they should have id's assigned to them
		for(Address address : this.addresses){
			Assert.assertNotNull("OBJECT NOT PERSISTED -> " + address , address.getId() );
		}
		
		// clean up
		for(Address address : this.addresses){
			log.debug("Removing address.id = " + address.getId());
			addressDao.delete(address);
		}
	}
	
	@Test
	public void insertPersonTest(){
		Person person = new Person();
		HibernateDao<Person> personDao = new HibernateDao<Person>(Person.class);
		person.setAge(25);
		person.setLastName("Persons");
		person.setName("John");
		Address javHome = new Address();
		javHome.setCity("London");
		javHome.setCountryIso(Address.Country.EN);
		javHome.setPostCode("EC1A 4HD");
		javHome.setStreet("200 Aldersgate Street");
		person.setAddress(javHome);
		Email email = new Email();
		email.setCreatedOn(new Date());
		email.setOwner(person);
		email.setUrl("first.email@gmail.com");
		List<Email> emails = new ArrayList<Email>();
		Email email2 = new Email();
		email2.setCreatedOn(new Date());
		email2.setOwner(person);
		email2.setUrl("second.email@gmail.com");
		emails.add(email);
		emails.add(email2);
		person.setEmails(emails);
		personDao.save(person);
		Assert.assertNotNull("No id assigned to person after person object being persisted",person.getId());
		Assert.assertNotNull("No id assigned to person.address after person object being persisted",person.getAddress().getId());
		personDao.delete(person);
	}
	
	@Test
	public void insertNewEmployee(){
		HibernateDao<Employee> dao = new HibernateDao<Employee>(Employee.class);
		Employee employee = new Employee();
		Address javHome = new Address();
		javHome.setCity("London");
		javHome.setCountryIso(Address.Country.EN);
		javHome.setPostCode("EC1A 4HD");
		javHome.setStreet("200 Aldersgate Street");
		employee.setAddress(javHome);
		employee.setLastName("LastNameEmployee");
		employee.setName("FirstNameEmployee");
		employee.setDivision("EmployeeStation");
		employee.setAge(25);
		Project proj = new Project();
		proj.setCreatedOn(new Date());
		proj.setName("The code monkey project");
		List<Employee> employees = new ArrayList<Employee>(1);
		employees.add(employee);
		proj.setEmployees(employees);
		List<Project> projects = new ArrayList<Project>(1);
		projects.add(proj);
		employee.setProjects(projects);
		dao.save(employee);
		Assert.assertNotNull("No id assigned to person after person object being persisted",employee.getId());
		dao.delete(employee);// clean up
	}
	
	@Test
	public void insertPersonSingleTable(){
		edu.pragmatic.hibernate.demo.model.inheritance.singletable.Person person = 
				new edu.pragmatic.hibernate.demo.model.inheritance.singletable.Person();
		HibernateDao<edu.pragmatic.hibernate.demo.model.inheritance.singletable.Person> dao 
               = new HibernateDao<>(edu.pragmatic.hibernate.demo.model.inheritance.singletable.Person.class);
       Address javHome = new Address();
       javHome.setCity("London");
       javHome.setCountryIso(Address.Country.EN);
       javHome.setPostCode("EC1A 4HD");
       javHome.setStreet("200 Aldersgate Street");    
       person.setAddress(javHome);
       person.setAge(47);
       person.setLastName("PersonLastName");
       person.setName("PersonName");
       dao.save(person);
       Assert.assertNotNull("No id assigned to person after person object being persisted",person.getId());
       dao.delete(person);
	}
	
	@Test
	public void insertSingleTableEmployeePerson(){
		HibernateDao<edu.pragmatic.hibernate.demo.model.inheritance.singletable.Employee> dao 
		        = new HibernateDao<>(edu.pragmatic.hibernate.demo.model.inheritance.singletable.Employee.class);
		edu.pragmatic.hibernate.demo.model.inheritance.singletable.Employee employee 
		 = new edu.pragmatic.hibernate.demo.model.inheritance.singletable.Employee();
		Address javHome = new Address();
		javHome.setCity("London");
		javHome.setCountryIso(Address.Country.EN);
		javHome.setPostCode("EC1A 4HD");
		javHome.setStreet("200 Aldersgate Street");
		employee.setAddress(javHome);
		employee.setLastName("LastNameEmployee");
		employee.setName("FirstNameEmployee");
		employee.setDivision("EmployeeStation");
		employee.setAge(25);
		edu.pragmatic.hibernate.demo.model.inheritance.singletable.Project proj = 
				 new edu.pragmatic.hibernate.demo.model.inheritance.singletable.Project();
		proj.setCreatedOn(new Date());
		proj.setName("The code monkey project");
		List<edu.pragmatic.hibernate.demo.model.inheritance.singletable.Employee> employees 
		     = new ArrayList<>(1);
		employees.add(employee);
		proj.setEmployees(employees);
		List<edu.pragmatic.hibernate.demo.model.inheritance.singletable.Project> projects = new ArrayList<>(1);
		projects.add(proj);
		employee.setProjects(projects);
		dao.save(employee);
		Assert.assertNotNull("No id assigned to employee after employee object being persisted", employee.getId());
		dao.delete(employee);// clean up
	}
	
}
