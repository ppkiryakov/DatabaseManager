package edu.pragmatic.hibernate.demo.model.inheritance.singletable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name="EmployeeSingleTable")
@DiscriminatorValue("Employee")
public class Employee extends Person{
	
	private static final long serialVersionUID = -2293307492743655632L;

	@Column(name="DIVISION")
	private String division;
	
	@ManyToMany(
	   targetEntity=Project.class,
	   cascade={CascadeType.ALL}
	)
    @JoinTable(
	   name="Project_Employee_SingleTable",
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
