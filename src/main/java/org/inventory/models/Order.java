package org.inventory.models;

import java.time.LocalDateTime;

public class Order {
    private int orderId;
    private int productId;
    private int userId;
    private int quantity;
    private LocalDateTime orderDate; // Stores the timestamp of the order

    public Order(int orderId, int productId, int userId, int quantity, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", orderDate=" + orderDate +
                '}';
    }
}
