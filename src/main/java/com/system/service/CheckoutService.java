package com.system.service;

import java.util.List;
import java.util.Map;

import com.system.interfaces.IShippable;
import com.system.model.Cart;
import com.system.model.Customer;

public class CheckoutService {

  private final CartService cartService;
  private final ShippingService shippingService;

  public CheckoutService(
    CartService cartService,
    ShippingService shippingService
  ) {
    this.cartService = cartService;
    this.shippingService = shippingService;
  }

  /**
   * Processes the checkout for a customer with the given cart.
   *
   * @param customer the customer who is checking out
   * @param cart the cart containing the items to be purchased
   */
  public void checkout(Customer customer, Cart cart) {
    double subtotalCost = cartService.calculateTotalPrice(cart);
    if (subtotalCost <= 0) {
      System.out.println("Cart is empty or total cost is zero.");
      return;
    }

    double totalWeight = cartService.calculateTotalWeight(cart);
    double shippingCost = 0.0;
    double totalCost = subtotalCost;
    if (totalWeight > 0) {
      shippingCost = shippingService.calculateShippingCost(totalWeight);
      totalCost += shippingCost;
    }

    if (customer.canAfford(totalCost)) {
      // Ship the items
      List<IShippable> itemsToShip = cartService.getShippableItems(cart);
      shippingService.processShipment(itemsToShip);

      printReceipt(cart, subtotalCost, totalCost, shippingCost, totalWeight);

      customer.setBalance(customer.getBalance() - totalCost);
      System.out.println(
        "Customer " +
        customer.getName() +
        " has successfully checked out. Remaining balance: " +
        customer.getBalance()
      );
    } else {
      System.out.println("Customer cannot afford the total cost.");
    }
  }

  public void printReceipt(
    Cart cart,
    double subtotal,
    double totalCost,
    double shippingCost,
    double totalWeight
  ) {
    System.out.println("** Shipment notice **");
    for (Map.Entry<com.system.model.Product, Integer> entry : cart
      .getItems()
      .entrySet()) {
      com.system.model.Product product = entry.getKey();
      int quantity = entry.getValue();
      if (product instanceof IShippable) {
        System.out.println(
          quantity +
          "x " +
          product.getName() +
          "   " +
          ((IShippable) product).getWeight() +
          "kg"
        );
      }
    }
    System.out.println("\nTotal Package weight " + totalWeight + " kg\n");

    System.out.println(" **Checkout receipt** ");
    for (Map.Entry<com.system.model.Product, Integer> entry : cart
      .getItems()
      .entrySet()) {
      com.system.model.Product product = entry.getKey();
      int quantity = entry.getValue();
      System.out.println(
        quantity +
        "x " +
        product.getName() +
        "   " +
        product.getPrice() * quantity
      );
    }
    System.out.println("====================");
    System.out.println("Subtotal    " + subtotal);
    System.out.println("Shipping    " + shippingCost);
    System.out.println("Amount      " + totalCost);
    System.out.println("====================");
  }
}
