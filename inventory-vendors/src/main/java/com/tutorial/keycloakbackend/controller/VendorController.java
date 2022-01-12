package com.tutorial.keycloakbackend.controller;

import java.util.BitSet;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.tutorial.keycloakbackend.model.Vendor;
import com.tutorial.keycloakbackend.service.VendorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/inventory")
public class VendorController {
    
    @Autowired
    @Qualifier("vendorService")
    private VendorService vendorService;

    @PutMapping("/vendor")
    //@RolesAllowed("backend-admin")
    public BitSet PUT_newProduct(@RequestBody Vendor vendor) {
        return vendorService.createVendor(vendor);
    }

    @DeleteMapping("/vendor/{id}")
    @RolesAllowed("backend-admin")
    public BitSet DELETE_newProduct(@PathVariable long id) {
        Vendor vendor = vendorService.getVendorById(id);
        return vendorService.createVendor(vendor);
    }

    @GetMapping("/vendor/{id}")
    @RolesAllowed("backend-user")
    public ResponseEntity<Vendor> GET_vendorById(@PathVariable long id) {
        return new ResponseEntity<>(vendorService.getVendorById(id), HttpStatus.OK);
    }

    @GetMapping("/vendor/username/{username}")
    public ResponseEntity<Long> GET_vendorIdByUsername(@PathVariable String username) {
        return new ResponseEntity<>(vendorService.getVendorByUsername(username).getId(), HttpStatus.OK);
    }

    @GetMapping("/vendor/email")
    @RolesAllowed("backend-user")
    public ResponseEntity<Vendor> GET_vendorByEmail(@RequestBody Vendor vendor) {
        return new ResponseEntity<>(vendorService.getVendorByEmail(vendor.getEmail()), HttpStatus.OK);
    }

    @GetMapping("/vendor/phone")
    @RolesAllowed("backend-user")
    public ResponseEntity<Vendor> GET_vendorByPhone(@RequestBody Vendor vendor) {
        return new ResponseEntity<>(vendorService.getVendorByPhone(vendor.getPhone()), HttpStatus.OK);
    }

    @GetMapping("/vendor/all")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Vendor>> GET_vendorsAll() {
        return new ResponseEntity<>(vendorService.getAllVendors(), HttpStatus.OK);
    }

    @GetMapping("/vendor")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Vendor>> GET_vendorsByPage(@PathVariable Pageable pageable) {
        return new ResponseEntity<>(vendorService.getAllVendorsByPage(pageable), HttpStatus.OK);
    }

}
