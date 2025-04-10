package com.example.api_project.repository;

import com.example.api_project.entity.InventoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryAttributeRepository extends JpaRepository<InventoryAttribute, Long> {
}
