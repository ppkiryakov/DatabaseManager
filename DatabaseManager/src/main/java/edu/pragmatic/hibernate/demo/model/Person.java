package edu.pragmatic.hibernate.demo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Person")
@Inheritance(strategy=InheritanceType.JOINED)
//@Cacheable(true)
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Person implements Serializable{

	private static final long serialVersionUID = 8541140544628255647L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="age")
	private Integer age;
	
	// sets a proxy instead of the actual object, it will be initialized only when a property of the object itself is called
	@OneToOne(fetch=FetchType.LAZY, 
			  orphanRemoval=false, 
			  targetEntity=Address.class, // useful when working against interfaces  
			  cascade={CascadeType.ALL},
			  optional=false)
	@JoinColumn(name="ADDRESS_ID")
	private Address address;

	@OneToMany(cascade=CascadeType.ALL, 
			   fetch=FetchType.EAGER, 
			   mappedBy="owner", 
			   orphanRemoval=true,
			   targetEntity=Email.class)
	private List<Email> emails; 
	
	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}
