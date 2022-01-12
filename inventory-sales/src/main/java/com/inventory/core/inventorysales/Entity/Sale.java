package com.inventory.core.inventorysales.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "SALE")
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SALE")
    private long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DATE_PURCHASE")
    private LocalDate date;

    @Column(name = "TIPO_PAGO")
    private String tipoPago;

    @OneToMany
	@JoinColumn(name = "ID_SALE", referencedColumnName = "ID_SALE")
    List<SaleDetails> details;

    @ManyToOne
    @JoinColumn(name = "ID_VENDOR")
    private Vendor vendor;

    public Sale() {
    }

    public Sale(long id, LocalDate date, List<SaleDetails> details, Vendor vendor, String tipoPago) {
        this.id = id;
        this.date = date;
        this.details = details;
        this.vendor = vendor;
        this.tipoPago = tipoPago;
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

    public List<SaleDetails> getDetails() {
        return details;
    }

    public void setDetails(List<SaleDetails> details) {
        this.details = details;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
    
}
