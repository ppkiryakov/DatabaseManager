package edu.pragmatic.hibernate.demo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.IntegerType;


import edu.pragmatic.hibernate.demo.config.HibernateUtils;
import edu.pragmatic.hibernate.demo.model.Address;
import edu.pragmatic.hibernate.demo.model.Employee;
import edu.pragmatic.hibernate.demo.model.inheritance.singletable.Person;

public class QueryService {
	
	@SuppressWarnings("unchecked")
	public List<Object> getAllPeopleRaw(){
		String sql = "select * from hibernate_test.person";
		SQLQuery sqlQuery = HibernateUtils.createSqlQuery(sql);
		// note that a pure sql query will not return a domain object, but an raw object instance
		// the name of the of the returned properties follows the logic of a jdbc ResultSet 
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Person> getAllPeopleSQL(){


		String sql = "select * from hibernate_test.person";
		SQLQuery sqlQuery = HibernateUtils.createSqlQuery(sql);
		sqlQuery.addScalar("ID", IntegerType.INSTANCE);
		sqlQuery.addScalar("AGE", IntegerType.INSTANCE);
		sqlQuery.addScalar("ADDRESS_ID", IntegerType.INSTANCE);
		sqlQuery.setResultTransformer(new ResultTransformer() {
			
			private static final long serialVersionUID = -5609382900973397099L;

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				Person person = new Person();
				Dao<Address> addressDao = new HibernateDao<>(Address.class);
				for (int i=0; i< aliases.length ; i++) {
					switch (aliases[i]) {
					case "ID": person.setId(Long.valueOf(tuple[i].toString())); break;
					case "NAME" : person.setName(tuple[i].toString()); break;
					case "AGE" : person.setAge(Integer.valueOf(tuple[i].toString())); break;
					case "LAST_NAME" : person.setLastName(tuple[i].toString()); break;
					case "ADDRESS_ID" : person.setAddress(addressDao.load(Long.valueOf(tuple[i].toString())));break;
					default:
						break;
					}
				}
				
				
				return person;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public List<Person> transformList(List collection) {
				return collection;
			}
		});
		
		
		return sqlQuery.list();
	}

	@SuppressWarnings("unchecked")
	public List<Person> getAllPeopleHQL() {
		Query hqlQuery = HibernateUtils.createHQlQuery("from Person");
		return hqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Person> getAllPeopleCriteria(){
		Criteria cr = HibernateUtils.createCriteria(edu.pragmatic.hibernate.demo.model.Person.class);
		return cr.list();
	}
	
	public Employee findEmployeeByCriteria(String name){
		Criteria cr = HibernateUtils.createCriteria(Employee.class);
		cr.add(Restrictions.eq("name", name));
		cr.setMaxResults(1);
		return (Employee) cr.uniqueResult();
	}
	
	public Employee findEmployeeByHQL(String name){
		Query q = HibernateUtils.createHQlQuery(" from Employee where name=:name");
		q.setString("name", name);
		return (Employee) q.uniqueResult();
	}

}
