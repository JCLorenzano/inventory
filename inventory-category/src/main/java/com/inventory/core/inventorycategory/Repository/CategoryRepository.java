package com.inventory.core.inventorycategory.Repository;

import java.io.Serializable;

import com.inventory.core.inventorycategory.Entity.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Serializable> {
    public abstract Category findById(long id);
    public abstract Page<Category> findAll(Pageable pageable);
}
