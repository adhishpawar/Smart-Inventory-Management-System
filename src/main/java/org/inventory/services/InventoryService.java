package org.inventory.services;

import org.inventory.dao.InventoryDAO;
import org.inventory.models.Product;

import java.util.List;

public class InventoryService {


    public InventoryService() {
        this.inventoryDAO = new InventoryDAO();
    }

    private static InventoryDAO inventoryDAO = null;
    //Add new Product
    public void addProduct(String name, String category, double price, int quantity)
    {
        Product newProduct = new Product(0,name,category,price,quantity);
        boolean result = inventoryDAO.registerProduct(newProduct);
        if(result)
            System.out.println("Product Added having Id: " + newProduct.getProductId());
        else{
            System.out.println("Product is not Register");
        }
    }

    public static void updateProduct(String name, String category, double price, int quantity) {
        // Step 1: Fetch the product first
        Product existingProduct = InventoryDAO.searchProductByUsingName(name);

        if (existingProduct != null) {
            // Step 2: Update product using correct product ID
            existingProduct.setCategory(category);
            existingProduct.setPrice(price);
            existingProduct.setQuantity(quantity);

            boolean updateResult = inventoryDAO.updateProduct(existingProduct);
            if (updateResult) {
                System.out.println("Product Updated Successfully with Id: " + existingProduct.getProductId());
            } else {
                System.out.println("Failed to update the product.");
            }
        } else {
            // Step 3: If product does not exist, insert it
            Product newProduct = new Product(0, name, category, price, quantity);
            boolean insertResult = inventoryDAO.registerProduct(newProduct);

            if (insertResult) {
                int productId = inventoryDAO.getProductId(name);
                System.out.println("Product Was Not in Inventory.....Now Added with Id: " + productId);
            } else {
                System.out.println("Unable to Add Product.");
            }
        }
    }


    public static boolean deleteProduct(Product product)
    {
        return inventoryDAO.deleteProduct(product);
    }

    public Product searchProductByUsingName(String name){
        return InventoryDAO.searchProductByUsingName(name);
    }

    public List<Product> searchProductByUsingCategory(String category)
    {
        return InventoryDAO.searchProductByUsingCategory(category);
    }


    public void displayAllInventory(){

        List<Product> allInventory = InventoryDAO.displayAllInventory();

        if(allInventory.isEmpty()){
            System.out.println("Inventory is Empty");
        }
        else {
            System.out.println("\n===== Inventory List =====");
            for (Product product : allInventory) {
                System.out.println("ID: " + product.getProductId() +
                        " | Name: " + product.getName() +
                        " | Category: " + product.getCategory() +
                        " | Price: $" + product.getPrice() +
                        " | Stock: " + product.getQuantity());
            }
        }
    }
    public static void displayAllInventory(List<Product> allInventory){
        if(allInventory.isEmpty()){
            System.out.println("Inventory is Empty");
        }
        else {
            System.out.println("\n===== Inventory List =====");
            for (Product product : allInventory) {
                System.out.println("ID: " + product.getProductId() +
                        " | Name: " + product.getName() +
                        " | Category: " + product.getCategory() +
                        " | Price: $" + product.getPrice() +
                        " | Stock: " + product.getQuantity());
            }
        }
    }

    public static void displayProduct(Product product)
    {
        // Check if products are found

        // Display product
        System.out.println("Products");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("| %-5s | %-20s | %-15s | %-10s | %-8s |\n", "ID", "Name", "Category", "Price", "Quantity");
        System.out.println("-------------------------------------------------------------");


        System.out.printf("| %-5d | %-20s | %-15s | %-10.2f | %-8d |\n",
                product.getProductId(),

                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity());

        System.out.println("-------------------------------------------------------------");
    }
}
