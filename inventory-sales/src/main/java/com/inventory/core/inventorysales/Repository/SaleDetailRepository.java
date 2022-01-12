package com.inventory.core.inventorysales.Repository;

import java.io.Serializable;
import java.util.List;

import com.inventory.core.inventorysales.Entity.SaleDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("saleDetailRepository")
public interface SaleDetailRepository extends JpaRepository<SaleDetails, Serializable>{
    //public abstract List<SaleDetails> findBySaleId(long id);
    public abstract List<SaleDetails> findByProductId(long id);
    public abstract Page<SaleDetails> findAll(Pageable pageable);
}
