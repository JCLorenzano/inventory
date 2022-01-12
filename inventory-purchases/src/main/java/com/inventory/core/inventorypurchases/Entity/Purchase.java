package com.inventory.core.inventorypurchases.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PURCHASES")
public class Purchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PURCHASE")
    private long id;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DATE_PURCHASE")
	private LocalDate date;
	
	@OneToMany
	@JoinColumn(name = "ID_PURCHASE", referencedColumnName = "ID_PURCHASE")
	List<PurchaseDetails> details;

	public Purchase() {
	}

	public Purchase(LocalDate date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<PurchaseDetails> getDetails() {
		return details;
	}

	public void setDetails(List<PurchaseDetails> details) {
		this.details = details;
	}

}
