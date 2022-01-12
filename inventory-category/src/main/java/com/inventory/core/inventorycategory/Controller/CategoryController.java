package com.inventory.core.inventorycategory.Controller;

import java.util.BitSet;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.inventory.core.inventorycategory.Entity.Category;
import com.inventory.core.inventorycategory.Entity.Product;
import com.inventory.core.inventorycategory.Service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequestMapping("v1/inventory")
public class CategoryController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("categoryService")
    private CategoryService categoryService;

    @PutMapping("/category")
    @RolesAllowed("backend-admin")
    public BitSet PUT_category(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PostMapping("/category/update/{id}")
    @RolesAllowed("backend-admin")
    public BitSet POST_category(@PathVariable long id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/category/{id}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<BitSet> DELETE_category(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable long id) {
        
        boolean band = false;
        BitSet bit = new BitSet(1);
        HttpHeaders headers = new HttpHeaders();

        if(authHeader != null) {
            headers.add("Authorization", authHeader);
            HttpEntity<?> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<List<Product>> productsByCategory = restTemplate.exchange("http://inventory-product/v1/inventory/category/product/" + id, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Product>>(){});
            List<Product> products = productsByCategory.getBody();

            if (products.isEmpty()) {
                bit = categoryService.deleteCategory(categoryService.getCategoryById(id));
                band = true;
                
            }

        }
        
        if (band) {
            return new ResponseEntity<>(bit, HttpStatus.OK);
        } else {
            System.out.println("the category you are trying to delete has products associated with it");
            bit.set(2);
            return new ResponseEntity<>(bit, HttpStatus.BAD_REQUEST);
        }
        
    }

    @GetMapping("/category/{id}")
    @RolesAllowed("backend-user")
    public ResponseEntity<Category> GET_categoryById(@PathVariable long id) {
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    @GetMapping("/category/all")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Category>> GET_categoriesAll() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/category")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Category>> GET_categoriesByPage(@PathVariable Pageable pageable) {
        return new ResponseEntity<>(categoryService.getAllCategoriesByPage(pageable), HttpStatus.OK);
    }

}
