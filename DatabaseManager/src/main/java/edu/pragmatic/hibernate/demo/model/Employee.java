package edu.pragmatic.hibernate.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Employee")
//@Cacheable(true)
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="edu.pragmatic.hibernate.demo.model.Employee")
public class Employee extends Person{
	
	private static final long serialVersionUID = -2492344967995209686L;

	@Column(name="DIVISION")
	private String division;
	
	@ManyToMany(
	   targetEntity=Project.class,
	   cascade={CascadeType.ALL},
	   fetch=FetchType.LAZY
	)
    @JoinTable(
	   name="Project_Employee",
	   joinColumns=@JoinColumn(name="EMPLOYEE_ID"),
	   inverseJoinColumns=@JoinColumn(name="PROJECT_ID")
	)
	private List<Project> projects;

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
}
