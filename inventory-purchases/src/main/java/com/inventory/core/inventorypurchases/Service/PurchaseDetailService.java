package com.inventory.core.inventorypurchases.Service;

import java.util.BitSet;

import com.inventory.core.inventorypurchases.Entity.PurchaseDetails;
import com.inventory.core.inventorypurchases.Repository.PurchaseDetailsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("purchaseDetailService")
public class PurchaseDetailService {
    @Autowired
    @Qualifier("purchaseDetailsRepository")
    private PurchaseDetailsRepository detailsRepository;

    public void createPurchaseDetail(PurchaseDetails purchaseDetails) {
        detailsRepository.save(purchaseDetails);
    }

    public BitSet deletePurchase(PurchaseDetails purchaseDetails) {
        BitSet bit = new BitSet();

        try {
            detailsRepository.delete(purchaseDetails);
            bit.set(0);

        } catch (Exception e) {
            bit.set(1);
            
        }

        return bit;
    }
}
