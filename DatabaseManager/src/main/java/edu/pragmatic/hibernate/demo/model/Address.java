package edu.pragmatic.hibernate.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Address")
//@Cacheable
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Address implements Serializable{

	private static final long serialVersionUID = 638403242627631591L;

	@Id
	@GeneratedValue
	private Long id;

	public static enum Country {
		BG,EN,US,GR,IT,CN,AU,RO,SB
	}  
	
	@Column(name="COUNTRY_ISO")
	@Enumerated(EnumType.STRING)
	private Country countryIso ;

	// No need to give the @column as this class is an @Entity
	// meaning all class properties are by default @Basic .If a property is not annotated
	// it is assumed that the DB table column is named just like the property
	// A property is not persistent only if annotated with @Transient
	// @see javax.persistence.Transient
	// @see javax.persistence.Basic
	private String street;

	// we need the @column here
	@Column(name="POST_CODE")
	private String postCode;
	
	// we can omit the @column here as well as mySQL db makes no difference between
	// column named city and CITY
	@Column(name="CITY")
	private String city ;
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountryIso() {
		return countryIso;
	}

	public void setCountryIso(Country countryIso) {
		this.countryIso = countryIso;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	@Override
	public String toString() {
		return	String.format(
						" addrs [ street : %s , postCode : %s , city : %s , country : %s ]",
				this.getStreet(),
				this.getPostCode(),
				this.getCity(),
				this.getCountryIso());
	}
	
}
