package com.inventory.core.inventorypurchases.Repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.inventory.core.inventorypurchases.Entity.Purchase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("purchaseRepository")
public interface PurchaseRepository extends JpaRepository<Purchase, Serializable> {
    public abstract Purchase findById(long id);
	public abstract List<Purchase> findByDate(LocalDate date);
	public abstract Page<Purchase> findAll(Pageable pageable);
}
