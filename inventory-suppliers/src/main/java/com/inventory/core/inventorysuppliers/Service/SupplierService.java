package com.inventory.core.inventorysuppliers.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import com.inventory.core.inventorysuppliers.Entity.Supplier;
import com.inventory.core.inventorysuppliers.Repository.SupplierRepository;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service("supplierService")
public class SupplierService {
    @Autowired
    @Qualifier("supplierRepository")
    private SupplierRepository repository;

    @HystrixCommand(
		fallbackMethod = "getFallbackBitSet",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "supplierServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public BitSet createSupplier (Supplier supplier) {
        BitSet bit = new BitSet();
        try {
            repository.save(supplier);
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
		threadPoolKey = "supplierServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public BitSet deleteSupplier (Supplier supplier) {
        BitSet bit = new BitSet();
        try {
            repository.delete(supplier);
            bit.set(1);
        } catch (Exception e) {
            bit.set(0);
        }

        return bit;
    }

    @HystrixCommand(fallbackMethod = "getFallbackSupplierById",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "supplierServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public Supplier getSupplierById (long id) {
		return repository.findById(id);
	}

    @HystrixCommand(fallbackMethod = "getFallbackSupplierByEmail",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "supplierServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public Supplier getSupplierByEmail (String email) {
		return repository.findByEmail(email);
	}

    @HystrixCommand(fallbackMethod = "getFallbackSupplierByPhone",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "supplierServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public Supplier getSupplierByPhone(String phone) {
		return repository.findByPhone(phone);
	}

    @HystrixCommand(fallbackMethod = "getFallbackListSuppliersPageable",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "supplierServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public List<Supplier> getAllSuppliersByPage(Pageable pageable) {
		return repository.findAll(pageable).getContent();
	}

    @HystrixCommand(fallbackMethod = "getFallbackAllSuppliers",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "supplierServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public List<Supplier> getAllSuppliers() {
		return repository.findAll();
	}

    public BitSet getFallbackBitSet(Supplier supplier) {
		BitSet bit = new BitSet();
        bit.set(0);
		return bit;
	}

    public Supplier getFallbackSupplierById(long id) {
		Supplier supplier = new Supplier();
		
        supplier.setId(0);
        supplier.setName("Supplier not found");

		return supplier;
	}

    public Supplier getFallbackSupplierByEmail(String email) {
		Supplier supplier = new Supplier();
		
        supplier.setId(0);
        supplier.setName("Supplier not found");

		return supplier;
	}

    public Supplier getFallbackSupplierByPhone(String email) {
		Supplier supplier = new Supplier();
		
        supplier.setId(0);
        supplier.setName("Supplier not found");

		return supplier;
	}

    public List<Supplier> getFallbackListSuppliersPageable(Pageable pageable) {
		Supplier supplier = new Supplier();
		
        supplier.setId(0);
        supplier.setName("Supplier not found");

        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(supplier);

		return suppliers;
	}

    public List<Supplier> getFallbackAllSuppliers() {
		Supplier supplier = new Supplier();
		
        supplier.setId(0);
        supplier.setName("Supplier not found");

        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(supplier);

		return suppliers;
	}
}
