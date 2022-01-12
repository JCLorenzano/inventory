package com.inventory.core.inventorysuppliers.Repository;

import java.io.Serializable;

import com.inventory.core.inventorysuppliers.Entity.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("supplierRepository")
public interface SupplierRepository extends JpaRepository<Supplier, Serializable> {
    public abstract Supplier findById(long id);
    public abstract Supplier findByEmail(String email);
    public abstract Supplier findByPhone(String phone);
	public abstract Page<Supplier> findAll(Pageable pageable);    
}
