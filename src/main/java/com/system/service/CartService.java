package com.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.system.interfaces.IShippable;
import com.system.model.Cart;
import com.system.model.Product;

/**
 * CartService class provides methods to manage the shopping cart.
 * It allows adding and removing products from the cart with appropriate checks.
 */
public class CartService {

  /**
   * Method to add a product to the cart
   *
   * @param cart - the cart to which the product will be added
   * @param product - the product to be added
   * @param quantity - the quantity of the product to be added
   *
   * @throws IllegalArgumentException if the quantity is negative or if the product is out of stock
   */
  public void addProductToCart(Cart cart, Product product, int quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }

    int currentQuantityInCart = cart.getItems().getOrDefault(product, 0);
    int newTotalQuantity = currentQuantityInCart + quantity;

    if (!product.isInStock(quantity)) {
      throw new IllegalArgumentException("Product is out of stock");
    }

    cart.getItems().put(product, newTotalQuantity);
    System.out.println(
      "Added product: " +
      product.getName() +
      " with quantity: " +
      quantity +
      " to the cart."
    );
  }

  /**
   * Method to remove a product from the cart
   *
   * @param cart - the cart from which the product will be removed
   * @param product - the product to be removed
   * @param quantity - the quantity of the product to be removed
   */
  public void removeProductFromCart(Cart cart, Product product, int quantity) {
    if (cart.isEmpty()) {
      throw new IllegalArgumentException("Cart is empty");
    }

    if (!cart.getItems().containsKey(product)) {
      throw new IllegalArgumentException("Product not found in the cart");
    }

    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }

    // Check if the quantity to remove is greater than the current quantity in the cart
    int currentQuantityInCart = cart.getItems().get(product);
    if (currentQuantityInCart < quantity) {
      throw new IllegalArgumentException(
        "Not enough quantity in the cart to remove"
      );
    }

    // Calculate the new quantity after removal and update the cart
    int newQuantity = currentQuantityInCart - quantity;
    if (newQuantity == 0) {
      cart.getItems().remove(product);
    } else {
      cart.getItems().put(product, newQuantity);
    }
  }

  /**
   * Method to calculate the total price of the items in the cart
   *
   * @param cart - the cart for which the total price will be calculated
   * @return the total price of the items in the cart
   */
  public double calculateTotalPrice(Cart cart) {
    double totalPrice = 0.0;
    for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
      Product product = entry.getKey();
      Integer quantity = entry.getValue();
      totalPrice += product.getPrice() * quantity;
    }
    return totalPrice;
  }

  /**
   * Method to calculate the total weight of the items in the cart
   *
   * @param cart - the cart for which the total weight will be calculated
   * @return the total weight of the items in the cart
   */
  public double calculateTotalWeight(Cart cart) {
    double totalWeight = 0.0;
    for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
      Product product = entry.getKey();
      if (product instanceof IShippable) {
        totalWeight += ((IShippable) product).getWeight() * entry.getValue();
      }
    }
    return totalWeight;
  }

  /**
   * Method to get a list of shippable items in the cart
   *
   * @param cart - the cart from which shippable items will be retrieved
   * @return a list of shippable items in the cart
   */
  public List<IShippable> getShippableItems(Cart cart) {
    List<IShippable> shippableItems = new ArrayList<>();

    for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
      Product product = entry.getKey();
      if (product instanceof IShippable) {
        shippableItems.add((IShippable) product);
      }
    }

    return shippableItems;
  }
}
