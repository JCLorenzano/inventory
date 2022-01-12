package com.inventory.core.inventorypurchases.Repository;

import java.io.Serializable;

import com.inventory.core.inventorypurchases.Entity.PurchaseDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("purchaseDetailsRepository")
public interface PurchaseDetailsRepository extends JpaRepository<PurchaseDetails, Serializable> {
    
}
