package com.inventory.core.inventoryproducts.Repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.inventory.core.inventoryproducts.Entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Serializable> {
    public abstract Product findById(long id);
    public abstract List<Product> findByDueDate(LocalDate dueDate);
    public abstract List<Product> findByCategoryId(long id);
    public abstract Page<Product> findAll(Pageable pageable);
}
