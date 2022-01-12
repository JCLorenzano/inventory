package com.inventory.core.inventorysales.Service;

import com.inventory.core.inventorysales.Entity.Product;
import com.inventory.core.inventorysales.Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductService {

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

    public Product getProductById(long id) {
        return productRepository.findById(id);
    }

    public boolean createProduct(Product product) {
        try {
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean increaseProductQuantity(long id, long quantity) {
        try {
            Product productTmp = getProductById(id);
            productTmp.setStock(productTmp.getStock() + quantity);
            productRepository.save(productTmp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean decreaseProductQuantity(long id, long quantity) {
        try {
            Product productTmp = getProductById(id);
            productTmp.setStock(productTmp.getStock() - quantity);
            productRepository.save(productTmp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateProduct(long id, Product product) {

        try {
			Product productTmp = getProductById(id);
			productTmp.setCategory(product.getCategory());
			productTmp.setDescription(product.getDescription());
			productTmp.setName(product.getName());
			productTmp.setSalePrice(product.getSalePrice());
			productTmp.setStock(product.getStock());
            productRepository.save(productTmp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
