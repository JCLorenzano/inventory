package com.inventory.core.inventoryproducts.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import com.inventory.core.inventoryproducts.Entity.Category;
import com.inventory.core.inventoryproducts.Entity.Product;
import com.inventory.core.inventoryproducts.Service.ProductService;

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
public class ProductController {
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("productService")
    private ProductService productService;

    @PutMapping("/product")
    @RolesAllowed("backend-admin")
    public ResponseEntity<BitSet> PUT_newProduct(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody Product product) {
        boolean band = false;
        BitSet bit = new BitSet(1);
        HttpHeaders headers = new HttpHeaders();

        if(authHeader != null) {
            headers.add("Authorization", authHeader);
            HttpEntity<?> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<List<Category>> categoriesEntity = restTemplate.exchange("http://inventory-category/v1/inventory/category/all", HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Category>>(){});
            List<Category> categories = categoriesEntity.getBody();

            long productId = product.getCategory().getId();

            for (Category category : categories) {
                if (productId == category.getId()) {
                    band = true;
                    bit = productService.createProduct(product);
                }
            }
        }

        if (band) {
            return new ResponseEntity<>(bit, HttpStatus.OK);
        } else {
            System.out.println("category not found");
            bit.set(2);
            return new ResponseEntity<>(bit, HttpStatus.NOT_FOUND);
        }
        
    }

    @PostMapping("/product/update/{id}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<BitSet> POST_Product(@PathVariable long id, @RequestBody Product product) {
        return new ResponseEntity<BitSet>(productService.updateProduct(id, product), HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    @RolesAllowed("backend-admin")
    public BitSet DELETE_product(@PathVariable long id) {
        Product product = productService.getProductById(id);
        return productService.deleteProduct(product);
    }

    @GetMapping("/product/{id}")
    @RolesAllowed("backend-user")
    public ResponseEntity<Product> GET_productById(@PathVariable long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("/product/price/{id}")
    @RolesAllowed("backend-user")
    public ResponseEntity<Float> GET_productPriceByProductId(@PathVariable long id) {
        return new ResponseEntity<>(productService.getProductById(id).getSalePrice(), HttpStatus.OK);
    }

    @GetMapping("/category/product/{id}")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Product>> GET_productByCategory(@PathVariable long id) {
        return new ResponseEntity<>(productService.getProductsByCategoryId(id), HttpStatus.OK);
    }

    @GetMapping("/product/due-date/{dueDate}")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Product>> GET_productsByDueDate(@PathVariable LocalDate dueDate) {
        return new ResponseEntity<>(productService.getProductsByDueDate(dueDate), HttpStatus.OK);
    }

    @GetMapping("/product/all")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Product>> GET_productsAll() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<Product>> GET_productsByPage(@PathVariable Pageable pageable) {
        return new ResponseEntity<>(productService.getAllProductsByPage(pageable), HttpStatus.OK);
    }

    @GetMapping("/category/product/all")
    @RolesAllowed("backend-user")
    public ResponseEntity<List<List<Product>>> GET_productsByCategoryAll() {
        List<Category> categories = productService.getAllCategories();
        List<List<Product>> products = new ArrayList<>();

        for (Category category : categories)
            products.add(productService.getProductsByCategoryId(category.getId()));

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
