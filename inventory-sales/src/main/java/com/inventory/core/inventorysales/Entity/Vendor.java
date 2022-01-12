package com.inventory.core.inventorysales.Entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "VENDORS")
public class Vendor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENDOR", unique = true)
    private long id;

    @Column(name = "NAME", length = 35)
    private String name;

    @Column(name = "LASTNAME", length = 35)
    private String lastname;

    @Column(name = "ADDRESS", length = 50)
    private String address;

    @Column(name = "PHONE")
    @Size(min = 10, max = 12, message = "FIELD OUT OF ESTABLISHED RANGE, MIN VIOLATION")
    private String phone;

    @Email
    @Column(name = "EMAIL", length = 40)
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    public Vendor() {
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }    

}
