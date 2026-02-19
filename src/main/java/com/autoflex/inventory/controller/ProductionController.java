package com.autoflex.inventory.controller;

import com.autoflex.inventory.dto.ProductionRequestDTO;
import com.autoflex.inventory.dto.ProductionSuggestionDTO;
import com.autoflex.inventory.service.ProductionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/production")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping("/suggestions")
    public List<ProductionSuggestionDTO> getProductionSuggestions() {
        return productionService.calculateProduction();
    }

    @PostMapping("/consume")
    public String consume(@RequestBody ProductionRequestDTO request) {
        productionService.consume(request.getProductId(), request.getQuantity());
        return "Production completed successfully";
    }

}

