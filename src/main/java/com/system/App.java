package com.system;

import java.time.LocalDate;

import com.system.model.Cart;
import com.system.model.Customer;
import com.system.model.ExpirableProduct;
import com.system.model.ExpirableShippableProduct;
import com.system.model.Product;
import com.system.model.ShippableProduct;
import com.system.service.CartService;
import com.system.service.CheckoutService;
import com.system.service.ShippingService;

public class App {

  public static void main(String[] args) {
    // Services
    CartService cartService = new CartService();
    ShippingService shippingService = new ShippingService();
    CheckoutService checkoutService = new CheckoutService(
      cartService,
      shippingService
    );

    // Products
    Product cheese = new ExpirableProduct(
      "Cheese",
      5.0,
      10,
      LocalDate.now().plusDays(10)
    );
    Product biscuits = new ExpirableShippableProduct(
      "Biscuits",
      2.0,
      20,
      1,
      LocalDate.now().plusMonths(2)
    );
    Product tv = new ShippableProduct("Smart TV", 500, 5, 10);
    Product mobile = new ShippableProduct("Iphone", 1000, 1, 0.5);

    // Customer
    Customer customer = new Customer("Tasneem", 5000);

    System.out.println("=====E-Commerce Demo=====\n");
    Cart cart = new Cart();

    try {
      cartService.addProductToCart(cart, cheese, 2);
      cartService.addProductToCart(cart, biscuits, 5);
      cartService.addProductToCart(cart, tv, 1);
      cartService.addProductToCart(cart, mobile, 1);

      System.out.println("--------------------------");

      checkoutService.checkout(customer, cart);
    } catch (IllegalArgumentException e) {
      System.out.println("=====Transaction Failed=====");
      System.out.println("Reason: " + e.getMessage());
    }
  }
}
