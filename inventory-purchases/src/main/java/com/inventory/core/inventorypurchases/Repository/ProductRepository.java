package com.inventory.core.inventorypurchases.Repository;

import java.io.Serializable;

import com.inventory.core.inventorypurchases.Entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Serializable> {
    
}
