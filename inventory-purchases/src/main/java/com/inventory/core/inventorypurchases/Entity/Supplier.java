package com.inventory.core.inventorypurchases.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SUPPLIERS")
public class Supplier implements Serializable {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SUPPLIER", unique = true)
	private long id;
	
	@Column(name = "NAME", length = 35)
	private String name;
	
	@Column(name = "LASTNAME", length = 35)
	private String lastname;
	
	@Column(name = "BUSINESS_NAME", length = 50)
	private String businessName;
	
	@Column(name = "ADDRESS", length = 50)
	private String address;
	
	@Column(name = "PHONE")
	@Size(min = 10, max = 12, message = "FIELD OUT OF ESTABLISHED RANGE, MIN VIOLATION")
	private String phone;
	
	@Email
	@Column(name = "EMAIL", length = 40)
	private String email;
	
	@Column(name = "POSTAL_CODE", length = 5)
	private String postalCode;

	public Supplier() {
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
