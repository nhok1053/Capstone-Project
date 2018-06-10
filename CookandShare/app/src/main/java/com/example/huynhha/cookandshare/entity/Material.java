package com.example.huynhha.cookandshare.entity;

public class Material {
    private String materialID;
    private String materialName;
    private String quantity;
    private String type;

    public Material(String materialID, String materialName, String quantity, String type) {
        this.materialID = materialID;
        this.materialName = materialName;
        this.quantity = quantity;
        this.type = type;
    }

    public String getMaterialID() {
        return materialID;
    }

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
