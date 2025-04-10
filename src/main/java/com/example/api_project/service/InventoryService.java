package com.example.api_project.service;

import com.example.api_project.dto.InventoryRequestDTO;
import com.example.api_project.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {

    InventoryResponseDTO createInventory(InventoryRequestDTO dto);

    InventoryResponseDTO getInventoryBySku(String sku);

    List<InventoryResponseDTO> getAllInventories(int page, int size);

    InventoryResponseDTO updateInventory(String sku, InventoryRequestDTO dto);

    void deleteInventory(String sku);
}