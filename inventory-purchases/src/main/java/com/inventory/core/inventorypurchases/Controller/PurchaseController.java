package com.inventory.core.inventorypurchases.Controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import com.inventory.core.inventorypurchases.Entity.Product;
import com.inventory.core.inventorypurchases.Entity.Purchase;
import com.inventory.core.inventorypurchases.Entity.PurchaseDetails;
import com.inventory.core.inventorypurchases.Entity.Supplier;
import com.inventory.core.inventorypurchases.Service.PurchaseDetailService;
import com.inventory.core.inventorypurchases.Service.PurchaseService;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/v1/inventory")
public class PurchaseController {
    @Autowired
    @Qualifier("purchaseService")
    private PurchaseService purchaseService;

    @Autowired
    @Qualifier("purchaseDetailService")
    private PurchaseDetailService purchaseDetailService;

    @Autowired
    private RestTemplate restTemplate;
    
    @PutMapping("/purchase")
    @RolesAllowed("backend-user")
    public Purchase PUT_newPurchase(@RequestBody Purchase purchase) {

        List<PurchaseDetails> details = purchase.getDetails();

        HttpServletRequest request = 
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();

        System.out.println("\n\n\n\n\n\n\n" + accessToken.getPreferredUsername() + "\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n" + accessToken.getId() + "\n\n\n\n\n\n\n");

        //HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Authorization", "Bearer " + accessToken.);


        for (PurchaseDetails detail : details) {

            long productId = detail.getProduct().getId();
            Product product = restTemplate.getForObject("http://inventory-product/v1/inventory/product/" + productId, Product.class);

            System.out.println(product.getName());

            long supplierId = detail.getSupplier().getId();
            Supplier supplier = restTemplate.getForObject("http://inventory-supplier/v1/inventory/supplier/" + supplierId, Supplier.class);

            detail.setProduct(product);
            detail.setSupplier(supplier);

            purchaseDetailService.createPurchaseDetail(detail);
        }

        purchaseService.createPurchas(purchase);

        return purchase;
    }

    @RolesAllowed("backend-user")
    @GetMapping("purchase/{id}")
    public Purchase GET_purchaseById(@PathVariable long id) {
        return purchaseService.getPurchaseById(id);
    }

}
