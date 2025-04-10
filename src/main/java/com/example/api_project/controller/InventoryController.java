
package com.example.api_project.controller;

import com.example.api_project.dto.InventoryRequestDTO;
import com.example.api_project.dto.InventoryResponseDTO;
import com.example.api_project.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> createInventory(@RequestBody InventoryRequestDTO dto) {
        return ResponseEntity.status(201).body(inventoryService.createInventory(dto));
    }

    @GetMapping("/{sku}")
    public ResponseEntity<InventoryResponseDTO> getInventory(@PathVariable String sku) {
        return ResponseEntity.ok(inventoryService.getInventoryBySku(sku));
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(inventoryService.getAllInventories(page, size));
    }

    @PutMapping("/{sku}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(@PathVariable String sku,
                                                                @RequestBody InventoryRequestDTO dto) {
        return ResponseEntity.ok(inventoryService.updateInventory(sku, dto));
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String sku) {
        inventoryService.deleteInventory(sku);
        return ResponseEntity.noContent().build();
    }
}