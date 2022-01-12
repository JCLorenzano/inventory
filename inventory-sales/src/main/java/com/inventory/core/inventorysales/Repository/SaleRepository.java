package com.inventory.core.inventorysales.Repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.inventory.core.inventorysales.Entity.Sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("saleRepository")
public interface SaleRepository extends JpaRepository<Sale, Serializable> {
    public abstract Sale findById(long id);
    public abstract List<Sale> findByVendorId(long id);
    public abstract List<Sale> findByDate(LocalDate date);
    public abstract Page<Sale> findAll(Pageable pageable);
}
