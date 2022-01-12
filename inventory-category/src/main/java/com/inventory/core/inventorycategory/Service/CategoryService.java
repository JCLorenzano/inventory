package com.inventory.core.inventorycategory.Service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import com.inventory.core.inventorycategory.Entity.Category;
import com.inventory.core.inventorycategory.Repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service("categoryService")
public class CategoryService {
    
    @Autowired
    @Qualifier("categoryRepository")
    private CategoryRepository categoryRepository;

    @HystrixCommand(fallbackMethod = "getFallbackBitSet",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "categoryServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public BitSet createCategory(Category category) {
        BitSet bit = new BitSet();

        try {
			
            categoryRepository.save(category);
            bit.set(1);
            return bit;
        } catch (Exception e) {
            bit.set(0);
            return bit;
        }
    }

	@HystrixCommand(fallbackMethod = "getFallbackBitSetUpdate",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "categoryServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
	public BitSet updateCategory(long  id, Category category) {
        BitSet bit = new BitSet();

        try {
			Category categoryTmp = getCategoryById(id);

			categoryTmp.setName(category.getName());

            categoryRepository.save(categoryTmp);
            bit.set(1);
            return bit;
        } catch (Exception e) {
            bit.set(0);
            return bit;
        }
    }

    @HystrixCommand(fallbackMethod = "getFallbackBitSet",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "categoryServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public BitSet deleteCategory(Category category) {
        BitSet bit = new BitSet();

        try {
            categoryRepository.delete(category);
            bit.set(1);
            return bit;
        } catch (Exception e) {
            bit.set(0);
            return bit;
        }
    }

    @HystrixCommand(fallbackMethod = "getFallbackCategoryById",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "categoryServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id);
    }

    @HystrixCommand(fallbackMethod = "getFallbackListCategoryPageable",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "categoryServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public List<Category> getAllCategoriesByPage(Pageable pageable) {
        return categoryRepository.findAll(pageable).getContent();
    }

    @HystrixCommand(fallbackMethod = "getFallbackListCategory",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "categoryServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    public Category getFallbackCategoryById(long id) {
		Category category = new Category();
		
        category.setId(0);
        category.setName("Category not found");

		return category;
	}

    public BitSet getFallbackBitSet(Category category) {
		BitSet bit = new BitSet();
        bit.set(0);
		return bit;
	}

	public BitSet getFallbackBitSetUpdate(long id, Category category) {
		BitSet bit = new BitSet();
        bit.set(0);
		return bit;
	}

    public List<Category> getFallbackListCategoryPageable(Pageable pageable) {
		Category category = new Category();
		
        category.setId(0);
        category.setName("Category not found");

        List<Category> cat = new ArrayList<>();
        cat.add(category);

		return cat;
	}

    public List<Category> getFallbackListCategory() {
		Category category = new Category();
		
        category.setId(0);
        category.setName("Category not found");

        List<Category> cat = new ArrayList<>();
        cat.add(category);

		return cat;
	}
}
