package com.inventory.core.inventorysuppliers.Controller;

import java.util.BitSet;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.inventory.core.inventorysuppliers.Entity.Supplier;
import com.inventory.core.inventorysuppliers.Service.SupplierService;

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
public class SupplierController {
    @Autowired
    @Qualifier("supplierService")
    private SupplierService supplierService;

    @PutMapping("/supplier")
    @RolesAllowed("backend-admin")
    public BitSet PUT_newSupplier(@RequestBody Supplier supplier) {
        return supplierService.createSupplier(supplier);
    }

    @DeleteMapping("/supplier/{id}")
    @RolesAllowed("backend-admin")
    public BitSet DELETE_newSupplier(@PathVariable long id) {
        Supplier supplier = supplierService.getSupplierById(id);
        return supplierService.createSupplier(supplier);
    }

    @GetMapping("/supplier/{id}")
    @RolesAllowed("backend-user")
    public ResponseEntity<Supplier> GET_supplierById(@PathVariable long id) {
        return new ResponseEntity<>(supplierService.getSupplierById(id), HttpStatus.OK);
    }

    @GetMapping("/supplier/email")
    @RolesAllowed("backend-user")
    public ResponseEntity<Supplier> GET_supplierByEmail(@RequestBody Supplier supplier) {
        return new ResponseEntity<>(supplierService.getSupplierByEmail(supplier.getEmail()), HttpStatus.OK);
    }

    @GetMapping("/supplier/phone")
    @RolesAllowed("backend-user")
    public ResponseEntity<Supplier> GET_supplierByPhone(@RequestBody Supplier supplier) {
        return new ResponseEntity<>(supplierService.getSupplierByPhone(supplier.getPhone()), HttpStatus.OK);
    }

    @GetMapping("/supplier/all")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Supplier>> GET_SuppliersAll() {
        return new ResponseEntity<>(supplierService.getAllSuppliers(), HttpStatus.OK);
    }

    @GetMapping("/supplier")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Supplier>> GET_supplierByPage(@PathVariable Pageable pageable) {
        return new ResponseEntity<>(supplierService.getAllSuppliersByPage(pageable), HttpStatus.OK);
    }
}
