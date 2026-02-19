package com.autoflex.inventory.controller;

import com.autoflex.inventory.model.RawMaterial;
import com.autoflex.inventory.repository.RawMaterialRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw-materials")
@CrossOrigin(origins = " http://localhost:5173/")
public class RawMaterialController {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialController(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @PostMapping
    public RawMaterial create(@RequestBody RawMaterial rawMaterial) {

        if (rawMaterial.getName() == null || rawMaterial.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (rawMaterial.getQuantity() == null || rawMaterial.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        return rawMaterialRepository.save(rawMaterial);
    }

    
    @GetMapping
    public List<RawMaterial> findAll() {
        return rawMaterialRepository.findAll();
    }

    @PutMapping("/{id}")
    public RawMaterial update(@PathVariable Long id, @RequestBody RawMaterial updated) {
        RawMaterial material = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        material.setName(updated.getName());
        material.setQuantity(updated.getQuantity());

        return rawMaterialRepository.save(material);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rawMaterialRepository.deleteById(id);
    }
}
