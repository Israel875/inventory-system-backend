package com.autoflex.inventory.service;
import java.util.Map;
import java.util.HashMap;
import com.autoflex.inventory.dto.ProductionSuggestionDTO;
import com.autoflex.inventory.model.Product;
import com.autoflex.inventory.model.ProductMaterial;
import com.autoflex.inventory.repository.ProductRepository;
import com.autoflex.inventory.repository.RawMaterialRepository;
import com.autoflex.inventory.repository.ProductMaterialRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.autoflex.inventory.model.RawMaterial;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;


@Service
public class ProductionService {
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductMaterialRepository productMaterialRepository;

    public ProductionService(ProductRepository productRepository,
                             RawMaterialRepository rawMaterialRepository,
                             ProductMaterialRepository productMaterialRepository) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.productMaterialRepository = productMaterialRepository;
    }

    public List<ProductionSuggestionDTO> calculateProduction() {

        List<Product> products = productRepository.findAll();


        products.sort(
                Comparator.comparing(Product::getPrice,
                        Comparator.nullsLast(BigDecimal::compareTo)
                ).reversed()
        );



        List<RawMaterial> rawMaterials = rawMaterialRepository.findAll();

        Map<Long, Integer> virtualStock = new HashMap<>();

        for (RawMaterial rm : rawMaterials) {
            virtualStock.put(rm.getId(), rm.getQuantity());
        }

        List<ProductionSuggestionDTO> suggestions = new ArrayList<>();

        for (Product product : products) {

            List<ProductMaterial> materials = product.getMaterials();

            if (materials == null || materials.isEmpty()) {
                continue;
            }

            int minPossible = Integer.MAX_VALUE;


            for (ProductMaterial pm : materials) {

                Long rawId = pm.getRawMaterial().getId();
                int available = virtualStock.getOrDefault(rawId, 0);
                int required = pm.getRequiredQuantity();

                if (required <= 0) {
                    throw new IllegalArgumentException("Invalid required quantity");
                }

                int possible = available / required;

                if (possible < minPossible) {
                    minPossible = possible;
                }
            }

            if (minPossible > 0) {


                for (ProductMaterial pm : materials) {

                    Long rawId = pm.getRawMaterial().getId();
                    int required = pm.getRequiredQuantity();

                    int totalToConsume = required * minPossible;

                    virtualStock.put(
                            rawId,
                            virtualStock.get(rawId) - totalToConsume
                    );
                }

                BigDecimal totalValue =
                        product.getPrice().multiply(BigDecimal.valueOf(minPossible));

                suggestions.add(
                        new ProductionSuggestionDTO(
                                product.getName(),
                                minPossible,
                                totalValue
                        )
                );
            }
        }

        return suggestions;
    }
    @Transactional
    public void consume(Long productId, Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        for (ProductMaterial pm : product.getMaterials()) {

            RawMaterial rawMaterial = pm.getRawMaterial();
            int required = pm.getRequiredQuantity();

            int totalToConsume = required * quantity;

            if (rawMaterial.getQuantity() < totalToConsume) {
                throw new RuntimeException("Insufficient stock for material: " + rawMaterial.getName());
            }

            rawMaterial.setQuantity(rawMaterial.getQuantity() - totalToConsume);
            rawMaterialRepository.save(rawMaterial);
        }
    }



}
