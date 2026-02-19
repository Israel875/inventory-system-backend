package com.autoflex.inventory.controller;
import com.autoflex.inventory.dto.ProductDTO;
import com.autoflex.inventory.model.Product;
import com.autoflex.inventory.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
    }

    @PostMapping
    public Product create(@RequestBody ProductDTO dto) {

        if (dto.getPrice() == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        return productRepository.save(product);
    }



    @GetMapping
    public List<Product> findALL() {
        return productRepository.findAll();
    }
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product updated) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(updated.getName());
        product.setPrice(updated.getPrice());

        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }


}
