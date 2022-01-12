package com.inventory.core.inventorysales.Service;

import java.time.LocalDate;
import java.util.List;

import com.inventory.core.inventorysales.Entity.Sale;
import com.inventory.core.inventorysales.Entity.SaleDetails;
import com.inventory.core.inventorysales.Repository.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("saleService")
public class SaleService {
    @Autowired
    @Qualifier("saleRepository")
    private SaleRepository saleRepository;

    @Autowired
    @Qualifier("productService")
    private ProductService productService;

    public boolean createSale(Sale sale) {
        try {
            saleRepository.save(sale);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteSale(Sale sale) {
        try {
            saleRepository.delete(sale);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Sale getSaleById(long id) {
        return saleRepository.findById(id);
    }

    public List<Sale> getSaleByDate(LocalDate date) {
        return saleRepository.findByDate(date);
    }

    public List<Sale> getSaleByVendorId(long id) {
        return saleRepository.findByVendorId(id);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public boolean updateSale(Sale sale) {
        try {
            Sale saleInDB = getSaleById(sale.getId());
            saleInDB.setDate(sale.getDate());

            List<SaleDetails> details = sale.getDetails();
            for (SaleDetails detail : details) {

                for (SaleDetails saledetail : saleInDB.getDetails()) {    
                    if (detail.getId() == saledetail.getId()) {
                        if (detail.getProduct().getId() == saledetail.getProduct().getId()) {
                            if (detail.getQuantity() > saledetail.getQuantity()) {
                                System.out.println("\n\n" + detail.getProduct().getName() + "  -  " + detail.getQuantity() + "\n");
                                long new_quantity = detail.getQuantity() - saledetail.getQuantity();
                                System.out.println("asdasdadsadasdasdsadasdasss");
                                System.out.println("\n new_quantity: " + new_quantity + "\n");
                                productService.decreaseProductQuantity(detail.getProduct().getId(), new_quantity);
                            }
                            else {
                                System.out.println("\n\n" + detail.getProduct().getName() + " - old quantity:  " + saledetail.getQuantity() + "  - new quantity:  " + detail.getQuantity() + "\n");
                                long new_quantity = saledetail.getQuantity() - detail.getQuantity();
                                System.out.println("new_quantity: " + new_quantity);
                                System.out.println("asdasdadsadasdasdsadasdasss");
                                productService.increaseProductQuantity(detail.getProduct().getId(), new_quantity);
                            }
                            
                            saledetail.setQuantity(detail.getQuantity());
                            System.out.println("\n\n\n");
                        }
                        
                    }
                }
                
            }

            saleInDB.setDetails(details);


            saleRepository.save(saleInDB);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
