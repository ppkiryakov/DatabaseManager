package edu.pragmatic.hibernate.demo;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import edu.pragmatic.hibernate.demo.dao.QueryService;
import edu.pragmatic.hibernate.demo.model.Employee;
import edu.pragmatic.hibernate.demo.model.inheritance.singletable.Person;

public class TestQueries {
	
	private QueryService qService;
	
	@Before
	public void setUp(){
		qService = new QueryService();
	}
	
	@Test
	public void testGetAllPeopleRaw(){
		List<Object> allPeople = qService.getAllPeopleRaw();
		Assert.assertNotNull(allPeople);
		Assert.assertFalse(allPeople.isEmpty());
	}
	
	@Test
	public void testGetAllPeopleSQL(){
		List<Person> allPeople = qService.getAllPeopleSQL();
		Assert.assertNotNull(allPeople);
		Assert.assertFalse(allPeople.isEmpty());
	}
	
	@Test
	public void testGetAllPeopleHQL(){
		List<Person> allPeople = qService.getAllPeopleHQL();
		Assert.assertNotNull(allPeople);
		Assert.assertFalse(allPeople.isEmpty());
	}
	
	@Test
	public void testGetAllPeopleCriteria(){
		List<Person> allPeople = qService.getAllPeopleCriteria();
		Assert.assertNotNull(allPeople);
		Assert.assertFalse(allPeople.isEmpty());
	}
	
	@Test
	public void testGetEmployee(){
		String name = "FirstNameEmployee";
		Employee employee = qService.findEmployeeByCriteria(name);
		Assert.assertNotNull(employee);
		Assert.assertNotNull(employee.getName());
		Assert.assertEquals(name, employee.getName());
		
		employee = qService.findEmployeeByHQL(name);
		Assert.assertNotNull(employee);
		Assert.assertNotNull(employee.getName());
		Assert.assertEquals(name, employee.getName());
	}
}
