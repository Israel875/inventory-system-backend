package com.autoflex.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;




@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "raw_materials")
public class RawMaterial {

    @JsonIgnore
    @OneToMany(mappedBy = "rawMaterial")
    private List<ProductMaterial> products;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Version
    private Long version;

    private Integer quantity;
}




