package com.inventory.core.inventorysales.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.inventory.core.inventorysales.Entity.Product;
import com.inventory.core.inventorysales.Entity.Sale;
import com.inventory.core.inventorysales.Entity.SaleDetails;
import com.inventory.core.inventorysales.Entity.Vendor;
import com.inventory.core.inventorysales.Service.ProductService;
import com.inventory.core.inventorysales.Service.SaleDetailService;
import com.inventory.core.inventorysales.Service.SaleService;
import com.inventory.core.inventorysales.Util.FeignUtilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * CREAR METODO PARA LISTAR VENTAS POR VENDEDOR
 */


@RestController
@RequestMapping("v1/inventory")
public class SaleController {
    
    @Autowired
    @Qualifier("saleService")
    private SaleService saleService;

    @Autowired
    @Qualifier("saleDetailService")
    private SaleDetailService saleDetailService;

    @Autowired
    @Qualifier("productService")
    private ProductService productService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FeignUtilService feignUtilService;

    @PutMapping("/sale")
    @RolesAllowed("backend-user")
    public ResponseEntity<Boolean> PUT_newSale(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody Sale sale) {
        HttpHeaders headers = new HttpHeaders();
    
        long vendorId = sale.getVendor().getId();

        // Eureka non registered service
        ResponseEntity<Vendor> vendorEntity = feignUtilService.GET_vendorById(authHeader, vendorId);
        Vendor vendor = vendorEntity.getBody();
        
        sale.setVendor(vendor);

        List<SaleDetails> details = sale.getDetails();

        boolean band = true;

        for (SaleDetails detail : details) {
            long productId = detail.getProduct().getId();
            Product product = productService.getProductById(productId);
            
            System.out.println("\n\n\n" + product.getStock());
            System.out.println("\n\n\n" + sale.getTipoPago());
            
            if(detail.getQuantity() <= product.getStock()) {
                product.setStock(product.getStock() - detail.getQuantity());

                System.out.println(product.getStock());

                System.out.println(product.getName()  + "\n\n\n");

                productService.updateProduct(productId, product);

                detail.setProduct(product);
                saleDetailService.createSaleDetail(detail);
            } else {
                band = false;
            }
            
        }

        if (band) {
            return new ResponseEntity<>(saleService.createSale(sale), HttpStatus.OK);
            

        }
        else {
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
        
    }

    @DeleteMapping("/sale/{id}")
    @RolesAllowed("backend-admin")
    public boolean DELETE_newSale(@PathVariable long id) {
        Sale sale = saleService.getSaleById(id);
        List<SaleDetails> details = sale.getDetails();

        for (SaleDetails saleDetails : details) {
            saleDetailService.deleteSaleDetail(saleDetails);
        }

        return saleService.deleteSale(sale);
    }

    @PostMapping("/sale/update/{id}")
    @RolesAllowed("backend-user")
    public boolean POST_editSale(@PathVariable long id, @RequestBody Sale sale) {
        return saleService.updateSale(sale);
    }

    @PostMapping("/sale/increase/product/{id}/{stock}")
    @RolesAllowed("backend-user")
    public boolean POST_increase_product_stock(@PathVariable long id, @PathVariable long stock) {
        System.out.println("increase product stock ");
        return productService.increaseProductQuantity(id, stock);
    }

    @GetMapping("/sale/{id}")
    @RolesAllowed("backend-user")
    public ResponseEntity<Sale> GET_saleById(@PathVariable long id) {
        return new ResponseEntity<>(saleService.getSaleById(id), HttpStatus.OK);
    }

    @GetMapping("/sale/all")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Sale>> GET_categoriesAll() {
        return new ResponseEntity<>(saleService.getAllSales(), HttpStatus.OK);
    }

    @GetMapping("/sale/vendor/{id}")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Sale>> GET_saleByVendorId(@PathVariable long id) {
        return new ResponseEntity<>(saleService.getSaleByVendorId(id), HttpStatus.OK);
    }
    

    @GetMapping("/sale/date/{date}")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Sale>> GET_saleByDate(@PathVariable String date) {
		/*
			List<Sale> allSales = saleService.getAllSales();
		    List<Sale> sales = new ArrayList<>();

		    for (Sale sale : allSales) {
		        if (sale.getDate().toString() == date)
		            sales.add(sale);
		    }
		*/
        
        System.out.println("\n\n" + date + "\n\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return new ResponseEntity<>(saleService.getSaleByDate(localDate), HttpStatus.OK);
    }

}
