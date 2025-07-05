package com.system.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.system.interfaces.IShippable;
import com.system.model.Cart;
import com.system.model.Product;
import com.system.model.ShippableProduct;

public class CartServiceTest {

  private CartService cartService;
  private Cart cart;

  @BeforeEach
  public void setUp() {
    cartService = new CartService();
    cart = new Cart();
  }

  @Test
  void calculateTotalPrice_ShouldReturnCorrectSum() {
    Product book = new Product("Book", 10.0, 2);
    Product pen = new Product("Pen", 2, 2);

    cart.getItems().put(book, 2); // 2 * 10 = 20
    cart.getItems().put(pen, 3); // 3 * 2 = 6

    double totalPrice = cartService.calculateTotalPrice(cart);
    assertEquals(26.0, totalPrice, 0.01);
  }

  @Test
  void calculateTotalPrice_ShouldReturnZero_WhenCartIsEmpty() {
    double totalPrice = cartService.calculateTotalPrice(cart);
    assertEquals(0.0, totalPrice, 0.01);
  }

  @Test
  void calculateTotalPrice_ShouldReturnZero_WhenCartHasNoItems() {
    cart.getItems().clear();
    double totalPrice = cartService.calculateTotalPrice(cart);
    assertEquals(0.0, totalPrice, 0.01);
  }

  @Test
  void addProductToCart_ShouldAddProduct_WhenValid() {
    Product product = new Product("Laptop", 1000.0, 5);
    cartService.addProductToCart(cart, product, 2);

    assertEquals(2, cart.getItems().get(product));
  }

  @Test
  void addProductToCart_ShouldThrowException_WhenQuantityIsNegative() {
    Product product = new Product("Laptop", 1000.0, 5);
    try {
      cartService.addProductToCart(cart, product, -1);
    } catch (IllegalArgumentException e) {
      assertEquals("Quantity cannot be negative", e.getMessage());
    }
  }

  @Test
  void addProductToCart_ShouldThrowException_WhenProductIsOutOfStock() {
    Product product = new Product("Laptop", 1000.0, 0);
    try {
      cartService.addProductToCart(cart, product, 1);
    } catch (IllegalArgumentException e) {
      assertEquals("Product is out of stock", e.getMessage());
    }
  }

  @Test
  void removeProductFromCart_ShouldRemoveProduct_WhenValid() {
    Product product = new Product("Laptop", 1000.0, 5);
    cartService.addProductToCart(cart, product, 2);
    cartService.removeProductFromCart(cart, product, 1);

    assertEquals(1, cart.getItems().get(product));
  }

  @Test
  void removeProductFromCart_ShouldThrowException_WhenQuantityIsNegative() {
    Product product = new Product("Laptop", 1000.0, 5);
    cartService.addProductToCart(cart, product, 2);
    try {
      cartService.removeProductFromCart(cart, product, -1);
    } catch (IllegalArgumentException e) {
      assertEquals("Quantity cannot be negative", e.getMessage());
    }
  }

  @Test
  void removeProductFromCart_ShouldThrowException_WhenProductNotInCart() {
    Product product = new Product("Laptop", 1000.0, 5);
    Product anotherProduct = new Product("Phone", 500.0, 10);
    cartService.addProductToCart(cart, anotherProduct, 1);
    try {
      cartService.removeProductFromCart(cart, product, 1);
    } catch (IllegalArgumentException e) {
      assertEquals("Product not found in the cart", e.getMessage());
    }
  }

  @Test
  void removeProductFromCart_ShouldThrowException_WhenNotEnoughQuantity() {
    Product product = new Product("Laptop", 1000.0, 5);
    cartService.addProductToCart(cart, product, 1);
    try {
      cartService.removeProductFromCart(cart, product, 2);
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough quantity in the cart to remove", e.getMessage());
    }
  }

  @Test
  void removeProductFromCart_ShouldThrowException_WhenCartIsEmpty() {
    Product product = new Product("Laptop", 1000.0, 5);
    try {
      cartService.removeProductFromCart(cart, product, 1);
    } catch (IllegalArgumentException e) {
      assertEquals("Cart is empty", e.getMessage());
    }
  }

  @Test
  void calculateTotalWeight_ShouldReturnCorrectWeight() {
    ShippableProduct book = new ShippableProduct("Book", 1.0, 2, 5);
    ShippableProduct pen = new ShippableProduct("Pen", 0.1, 3, 2);

    cart.getItems().put(book, 2); // 2 * 5 = 10
    cart.getItems().put(pen, 3); // 3 * 2 = 6

    double totalWeight = cartService.calculateTotalWeight(cart);
    assertEquals(16, totalWeight, 0.01);
  }

  @Test
  void calculateTotalWeight_ShouldReturnZero_WhenCartIsEmpty() {
    double totalWeight = cartService.calculateTotalWeight(cart);
    assertEquals(0.0, totalWeight, 0.01);
  }

  @Test
  void getShippableItems_ShouldReturnShippableItems_WhenCartHasShippableProducts() {
    ShippableProduct shippableProduct = new ShippableProduct(
      "Shippable Item",
      100.0,
      2,
      1.0
    );
    Product nonShippableProduct = new Product("Non-Shippable Item", 50.0, 5);
    cart.getItems().put(shippableProduct, 1);
    cart.getItems().put(nonShippableProduct, 3);

    List<IShippable> shippableItems = cartService.getShippableItems(cart);
    assertEquals(1, shippableItems.size());
    assertEquals(shippableProduct, shippableItems.get(0));
  }

  @Test
  void getShippableItems_ShouldReturnEmptyList_WhenNoShippableProducts() {
    Product nonShippableProduct = new Product("Non-Shippable Item", 50.0, 5);
    cart.getItems().put(nonShippableProduct, 3);
    List<IShippable> shippableItems = cartService.getShippableItems(cart);
    assertEquals(0, shippableItems.size());
  }
}
