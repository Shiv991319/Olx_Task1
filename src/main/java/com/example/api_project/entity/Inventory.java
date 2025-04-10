package com.example.api_project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventories")
public class Inventory {

    @Id
    private String sku;

    private String type;
    private String status;
    private String location;
    private int costPrice;
    private int sellingPrice;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryAttribute> attributes = new ArrayList<>();

    public Inventory() {}

    public Inventory(String sku, String type, String status, String location, int costPrice, int sellingPrice,
                     String createdBy, String updatedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.sku = sku;
        this.type = type;
        this.status = status;
        this.location = location;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(int costPrice) {
        this.costPrice = costPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<InventoryAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<InventoryAttribute> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            for (InventoryAttribute attribute : attributes) {
                addAttribute(attribute);
            }
        }
    }

    public void addAttribute(InventoryAttribute attribute) {
        attribute.setInventory(this);
        this.attributes.add(attribute);
    }

    public void removeAttribute(InventoryAttribute attribute) {
        attribute.setInventory(null);
        this.attributes.remove(attribute);
    }
}
