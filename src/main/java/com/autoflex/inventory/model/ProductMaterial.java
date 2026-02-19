package com.autoflex.inventory.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Entity
@Table(name = "product_material")

public class ProductMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")

    private Product product;

    @ManyToOne
    @JoinColumn(name = "raw_material_id")

    private RawMaterial rawMaterial;

    private Integer requiredQuantity;


    public ProductMaterial(Product product, RawMaterial rawMaterial, Integer requiredQuantity) {
        if (requiredQuantity == null || requiredQuantity <= 0) {
            throw new IllegalArgumentException("Required quantity must be positive. ");
        }
        this.product = product;
        this.rawMaterial = rawMaterial;
        this.requiredQuantity = requiredQuantity;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public Integer getRequiredQuantity() {
        return requiredQuantity;
    }
}
