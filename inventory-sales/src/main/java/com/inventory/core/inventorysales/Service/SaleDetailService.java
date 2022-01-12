package com.inventory.core.inventorysales.Service;

import com.inventory.core.inventorysales.Entity.SaleDetails;
import com.inventory.core.inventorysales.Repository.SaleDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("saleDetailService")
public class SaleDetailService {
    
    @Autowired
    @Qualifier("saleDetailRepository")
    private SaleDetailRepository saleDetailRepository;

    public boolean createSaleDetail(SaleDetails saleDetails) {
        try {
            saleDetailRepository.save(saleDetails);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteSaleDetail(SaleDetails saleDetails) {
        try {
            saleDetailRepository.delete(saleDetails);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
