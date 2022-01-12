package com.inventory.core.inventorysales.Util;

import com.inventory.core.inventorysales.Entity.Vendor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "inventory-vendor", url = "http://localhost:8084/v1/inventory")
public interface FeignUtilService {
    
    @GetMapping("/vendor/{id}")
    ResponseEntity<Vendor> GET_vendorById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable("id") long id);

}
