package com.tutorial.keycloakbackend.repository;

import java.io.Serializable;

import com.tutorial.keycloakbackend.model.Vendor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("vendorRepository")
public interface VendorRepository extends JpaRepository<Vendor, Serializable> {
    public abstract Vendor findById(long id);
    public abstract Vendor findByEmail(String email);
    public abstract Vendor findByPhone(String phone);
    public abstract Vendor findByUsername(String username);
	public abstract Page<Vendor> findAll(Pageable pageable);
}
