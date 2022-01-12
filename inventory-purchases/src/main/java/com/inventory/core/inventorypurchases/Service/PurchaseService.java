package com.inventory.core.inventorypurchases.Service;

import com.inventory.core.inventorypurchases.Entity.Purchase;
import com.inventory.core.inventorypurchases.Repository.PurchaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("purchaseService")
public class PurchaseService {
    @Autowired
    @Qualifier("purchaseRepository")
    private PurchaseRepository repository;

    public void createPurchas(Purchase purchase) {
        repository.save(purchase);
    }

    public Purchase getPurchaseById(long id) {
        return repository.findById(id);
    }
}
