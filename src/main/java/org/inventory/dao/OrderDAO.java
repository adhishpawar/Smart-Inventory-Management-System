package org.inventory.dao;

import org.inventory.models.Product;
import org.inventory.models.User;
import org.inventory.services.OrderService;

import java.util.Objects;

public class OrderDAO {
    public static void placeOrder(User user, String productName, int quantity) {
        //Check for the availability of Product
       Product product = InventoryDAO.searchProductByUsingName(productName);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        if (product.getQuantity() >= quantity) {
            OrderService.placeOrder(user, product, quantity);
        } else {
            System.out.println("Not enough stock available!");
        }
    }
}
