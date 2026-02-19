package com.autoflex.inventory.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")


public class Product {

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductMaterial> materials = new ArrayList<>();


    public List<ProductMaterial> getMaterials() {
        return materials;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private  Long id ;
    private String name;
    private BigDecimal price;



    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Long getId() { return id; }
    public String getName() {return name; }
    public BigDecimal getPrice() {return price; }


}

