package org.example.entity;

public class OrdersXMLEntity {
    private String name;
    private String type;
    private String saleDate;
    private String deliverDate;
    private String status;

    public OrdersXMLEntity() {
    }

    public OrdersXMLEntity(String name, String type, String saleDate, String deliverDate, String status) {
        this.name = name;
        this.type = type;
        this.saleDate = saleDate;
        this.deliverDate = deliverDate;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
