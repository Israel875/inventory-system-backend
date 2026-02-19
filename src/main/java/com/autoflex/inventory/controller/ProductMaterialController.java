package com.autoflex.inventory.controller;

import com.autoflex.inventory.dto.ProductMaterialDTO;
import com.autoflex.inventory.model.Product;
import com.autoflex.inventory.model.ProductMaterial;
import com.autoflex.inventory.model.RawMaterial;
import com.autoflex.inventory.repository.ProductMaterialRepository;
import com.autoflex.inventory.repository.ProductRepository;
import com.autoflex.inventory.repository.RawMaterialRepository;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/product-materials")
public class ProductMaterialController {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;


    private final ProductMaterialRepository repository;

    public ProductMaterialController(
            ProductMaterialRepository repository,
            ProductRepository productRepository,
            RawMaterialRepository rawMaterialRepository) {

        this.repository = repository;
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }


    @PostMapping
    public ProductMaterial create(@RequestBody ProductMaterialDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.getRawMaterialId())
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        ProductMaterial productMaterial =
                new ProductMaterial(product, rawMaterial, dto.getRequiredQuantity());


        product.getMaterials().add(productMaterial);

        return repository.save(productMaterial);
    }




    @GetMapping
    public List<ProductMaterial> findAll() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

