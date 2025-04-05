package org.inventory.dao;

import org.inventory.models.Product;
import org.inventory.models.User;
import org.inventory.services.OrderService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.inventory.services.OrderService.getUserOrders;

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

    public static void getUserOrder(User user){
        List<Map<String,Object>> orders = getUserOrders(user.getUserId());
        if(orders.isEmpty()){
            System.out.println("No Order Placed");
        }
        else {
            System.out.println("\n===== User Order List =====");
            for (Map<String, Object> order : orders) {
                System.out.println("Order ID: " + order.get("order_id") +
                        " | Product ID: " + order.get("product_id") +
                        " | Product Name: " + order.get("product_name") +
                        " | Quantity: " + order.get("quantity") +
                        " | Order Date: " + order.get("order_date"));
            }
        }
    }
}
