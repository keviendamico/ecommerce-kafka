package it.kevien.demo.inventoryservice.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class InventoryRepository {

    // simulation of a DB query - static values, only for demo
    public Map<String, Integer> findAll() {
        return Map.of(
                "NS00123", 5,
                "NS00122", 1,
                "NS00127", 10
        );
    }
}
