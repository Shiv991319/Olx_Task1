package com.example.api_project.service;

import com.example.api_project.dto.*;
import com.example.api_project.entity.Inventory;
import com.example.api_project.entity.InventoryAttribute;
import com.example.api_project.exception.ResourceNotFoundException;
import com.example.api_project.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public InventoryResponseDTO createInventory(InventoryRequestDTO dto) {
        Inventory inventory = mapToEntity(dto);
        inventory.setCreatedAt(LocalDateTime.now());
        inventory.setUpdatedAt(LocalDateTime.now());

        // Remove unnecessary lambda for setting inventory on attributes
        // As it is handled when creating InventoryAttributes in mapToEntity()

        inventory = inventoryRepository.save(inventory);
        return mapToDTO(inventory);
    }

    @Override
    public InventoryResponseDTO getInventoryBySku(String sku) {
        Inventory inventory = inventoryRepository.findById(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
        return mapToDTO(inventory);
    }

    @Override
    public List<InventoryResponseDTO> getAllInventories(int page, int size) {
        return inventoryRepository.findAll(PageRequest.of(page, size)).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryResponseDTO updateInventory(String sku, InventoryRequestDTO dto) {
        Inventory existing = inventoryRepository.findById(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        existing.setType(dto.getType());
        existing.setStatus(dto.getStatus());
        existing.setLocation(dto.getLocation());
        existing.setCostPrice(dto.getCostPrice());
        existing.setSellingPrice(dto.getSellingPrice());
        existing.setUpdatedBy(dto.getUpdatedBy());
        existing.setUpdatedAt(LocalDateTime.now());

        existing.getAttributes().clear();
        List<InventoryAttribute> newAttrs = dto.getAttributes().stream().map(attrDTO -> {
            InventoryAttribute attr = new InventoryAttribute();
            attr.setAttributeName(attrDTO.getAttributeName());
            attr.setAttributeValue(attrDTO.getAttributeValue());
            attr.setInventory(existing);
            return attr;
        }).collect(Collectors.toList());
        existing.getAttributes().addAll(newAttrs);

        return mapToDTO(inventoryRepository.save(existing));
    }

    @Override
    public void deleteInventory(String sku) {
        Inventory inventory = inventoryRepository.findById(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
        inventoryRepository.delete(inventory);
    }

    private Inventory mapToEntity(InventoryRequestDTO dto) {
        Inventory inventory = new Inventory();
        inventory.setSku(dto.getSku());
        inventory.setType(dto.getType());
        inventory.setStatus(dto.getStatus());
        inventory.setLocation(dto.getLocation());
        inventory.setCostPrice(dto.getCostPrice());
        inventory.setSellingPrice(dto.getSellingPrice());
        inventory.setCreatedBy(dto.getCreatedBy());
        inventory.setUpdatedBy(dto.getUpdatedBy());

        List<InventoryAttribute> attrList = dto.getAttributes().stream().map(attrDTO -> {
            InventoryAttribute attr = new InventoryAttribute();
            attr.setAttributeName(attrDTO.getAttributeName());
            attr.setAttributeValue(attrDTO.getAttributeValue());
            attr.setInventory(inventory);  // Sets inventory reference here
            return attr;
        }).collect(Collectors.toList());

        inventory.setAttributes(attrList);
        return inventory;
    }

    private InventoryResponseDTO mapToDTO(Inventory inventory) {
        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setSku(inventory.getSku());
        dto.setType(inventory.getType());
        dto.setStatus(inventory.getStatus());
        dto.setLocation(inventory.getLocation());
        dto.setCostPrice(inventory.getCostPrice());
        dto.setSellingPrice(inventory.getSellingPrice());
        dto.setCreatedBy(inventory.getCreatedBy());
        dto.setUpdatedBy(inventory.getUpdatedBy());
        dto.setCreatedAt(inventory.getCreatedAt());
        dto.setUpdatedAt(inventory.getUpdatedAt());

        List<AttributeDTO> attrList = inventory.getAttributes().stream().map(attr -> {
            AttributeDTO attrDTO = new AttributeDTO();
            attrDTO.setAttributeName(attr.getAttributeName());
            attrDTO.setAttributeValue(attr.getAttributeValue());
            return attrDTO;
        }).collect(Collectors.toList());

        dto.setAttributes(attrList);
        return dto;
    }
}
