package com.inventory.core.inventoryproducts.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import com.inventory.core.inventoryproducts.Entity.Category;
import com.inventory.core.inventoryproducts.Entity.Product;
import com.inventory.core.inventoryproducts.Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service("productService")
public class ProductService {
    
    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

	@Autowired
	private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackBitSet",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "productServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public BitSet createProduct(Product product) {
        BitSet bit = new BitSet(1);
        try {
            productRepository.save(product);
            bit.set(1);
        } catch (Exception e) {
            bit.set(0);
        }

        return bit;
    }

	@HystrixCommand(fallbackMethod = "getFallbackUpdateBitSet",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "productServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
	public BitSet updateProduct(long id, Product product) {

		BitSet bit = new BitSet(1);

        try {
			Product productTmp = getProductById(id);
			productTmp.setCategory(product.getCategory());
			productTmp.setDescription(product.getDescription());
			productTmp.setName(product.getName());
			productTmp.setSalePrice(product.getSalePrice());
			productTmp.setStock(product.getStock());
            productRepository.save(productTmp);
            bit.set(1);
        } catch (Exception e) {
            bit.set(0);
        }

        return bit;
    }

    @HystrixCommand(fallbackMethod = "getFallbackBitSet",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "productServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public BitSet deleteProduct(Product product) {
        BitSet bit = new BitSet(1);
        try {
            productRepository.delete(product);
            bit.set(1);
        } catch (Exception e) {
            bit.set(0);
        }

        return bit;
    }

    @HystrixCommand(fallbackMethod = "getFallbackProductById",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "productServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public Product getProductById(long id) {
        return productRepository.findById(id);
    }

    @HystrixCommand(fallbackMethod = "getFallbackProductByCtegoryId",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "productServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public List<Product> getProductsByCategoryId(long id) {
        return productRepository.findByCategoryId(id);
    }

    @HystrixCommand(fallbackMethod = "getFallbackProductByDueDate",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "productServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public List<Product> getProductsByDueDate(LocalDate dueDate) {
        return productRepository.findByDueDate(dueDate);
    }

    @HystrixCommand(fallbackMethod = "getFallbackListProduct",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "productServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @HystrixCommand(fallbackMethod = "getFallbackListProductPageable",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "productServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public List<Product> getAllProductsByPage(Pageable pageable) {
        return productRepository.findAll(pageable).getContent();
    }

	@HystrixCommand(fallbackMethod = "getFallbackAllCategories",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "productServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
	public List<Category> getAllCategories() {
		return Arrays.asList(restTemplate.getForObject("http://inventory-category/v1/inventory/category/all", Category[].class));
	}

    public BitSet getFallbackBitSet(Product product) {
		BitSet bit = new BitSet();
        bit.set(0);
		return bit;
	}

	public BitSet getFallbackUpdateBitSet(long id, Product product) {
		BitSet bit = new BitSet();
        bit.set(0);
		return bit;
	}

	public Product getFallbackProductById(long id) {
        Product product = new Product();
		
        product.setId(0);
        product.setName("Product not found");

		return product;
	}
	
	
    public Product getFallbackCategoryById(long id) {
		Product product = new Product();
		
        product.setId(0);
        product.setName("Product not found");

		return product;
	}

    public List<Product> getFallbackProductByCtegoryId(long id) {
		List<Product> products = new ArrayList<>();
        Product product = new Product();
		
        product.setId(0);
        product.setName("Product not found");

		return products;
	}

    public List<Product> getFallbackProductByDueDate(LocalDate dueDate) {
		List<Product> products = new ArrayList<>();
        Product product = new Product();
		
        product.setId(0);
        product.setName("Product not found");

		return products;
	}

    public List<Product> getFallbackListProduct() {
		Product product = new Product();
		
        product.setId(0);
        product.setName("Product not found");

        List<Product> products = new ArrayList<>();
        products.add(product);

		return products;
	}

    public List<Product> getFallbackListProductPageable(Pageable pageable) {
		Product product = new Product();
		
        product.setId(0);
        product.setName("Product not found");

        List<Product> products = new ArrayList<>();
        products.add(product);

		return products;
	}

	public List<Category> getFallbackAllCategories() {
		Category category = new Category();
		
        category.setId(0);
        category.setName("Product not found");

        List<Category> categories = new ArrayList<>();
        categories.add(category);

		return categories;
	}
}
