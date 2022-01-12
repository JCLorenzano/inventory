package com.inventory.core.inventorysales.Repository;

import java.io.Serializable;

import com.inventory.core.inventorysales.Entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Serializable> {
    public abstract Product findById(long id);
}
