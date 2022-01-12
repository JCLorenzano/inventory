package com.tutorial.keycloakbackend.service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tutorial.keycloakbackend.model.Vendor;
import com.tutorial.keycloakbackend.repository.VendorRepository;

@Service("vendorService")
public class VendorService {
    
    @Autowired
    @Qualifier("vendorRepository")
    private VendorRepository repository;

    @HystrixCommand(fallbackMethod = "getFallbackBitSet",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "vendorServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public BitSet createVendor(Vendor vendor) {
        BitSet bit = new BitSet();
        try {
            repository.save(vendor);
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
		threadPoolKey = "vendorServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public BitSet deleteVendor(Vendor vendor) {
        BitSet bit = new BitSet();
        try {
            repository.delete(vendor);
            bit.set(1);
        } catch (Exception e) {
            bit.set(0);
        }

        return bit;
    }

    @HystrixCommand(fallbackMethod = "getFallbackVendorById",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "vendorServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public Vendor getVendorById(long id) {
		return repository.findById(id);
	}

	@HystrixCommand(fallbackMethod = "getFallbackVendorByUsername",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "vendorServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
	public Vendor getVendorByUsername(String username) {
		return repository.findByUsername(username);
	}

    @HystrixCommand(fallbackMethod = "getFallbackVendorByEmail",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "vendorServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public Vendor getVendorByEmail(String email) {
		return repository.findByEmail(email);
	}

    @HystrixCommand(fallbackMethod = "getFallbackVendorByPhone",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "vendorServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public Vendor getVendorByPhone(String phone) {
		return repository.findByPhone(phone);
	}

    @HystrixCommand(fallbackMethod = "getFallbackListVendorsPageable",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "vendorServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public List<Vendor> getAllVendorsByPage(Pageable pageable) {
		return repository.findAll(pageable).getContent();
	}

    @HystrixCommand(fallbackMethod = "getFallbackAllVendors",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
		},
		threadPoolKey = "vendorServicePool",
		threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "20"),
			@HystrixProperty(name = "maxQueueSize", value = "10")
		}
	)
    public List<Vendor> getAllVendors() {
		return repository.findAll();
	}

    public BitSet getFallbackBitSet(Vendor vendor) {
		BitSet bit = new BitSet();
        bit.set(0);
		return bit;
	}

    public Vendor getFallbackVendorById(long id) {
		Vendor vendor = new Vendor();
		
        vendor.setId(0);
        vendor.setName("Vendor not found");

		return vendor;
	}

	public Vendor getFallbackVendorByUsername(String username) {
		Vendor vendor = new Vendor();
		
        vendor.setId(0);
        vendor.setName("Vendor not found");

		return vendor;
	}

    public Vendor getFallbackVendorByEmail(String email) {
		Vendor vendor = new Vendor();
		
        vendor.setId(0);
        vendor.setName("Vendor not found");

		return vendor;
	}

    public Vendor getFallbackVendorByPhone(String email) {
		Vendor vendor = new Vendor();
		
        vendor.setId(0);
        vendor.setName("Vendor not found");

		return vendor;
	}

    public List<Vendor> getFallbackListVendorsPageable(Pageable pageable) {
		Vendor vendor = new Vendor();
		
        vendor.setId(0);
        vendor.setName("Product not found");

        List<Vendor> vendors = new ArrayList<>();
        vendors.add(vendor);

		return vendors;
	}

    public List<Vendor> getFallbackAllVendors() {
		Vendor vendor = new Vendor();
		
        vendor.setId(0);
        vendor.setName("Product not found");

        List<Vendor> vendors = new ArrayList<>();
        vendors.add(vendor);

		return vendors;
	}

}
