package com.system.service;

import java.util.List;

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
    double totalCost = cartService.calculateTotalPrice(cart);
    if (totalCost <= 0) {
      System.out.println("Cart is empty or total cost is zero.");
      return;
    }
    System.out.println("Total cost before shipping: " + totalCost);

    double totalWeight = cartService.calculateTotalWeight(cart);
    double shippingCost = 0.0;
    if (totalWeight > 0) {
      shippingCost = shippingService.calculateShippingCost(totalWeight);
      System.out.println("Shipping fees: " + shippingCost);
      totalCost += shippingCost;
    }

    if (customer.canAfford(totalCost)) {
      System.out.println("Total paid amount: " + totalCost);

      // Ship the items
      List<IShippable> itemsToShip = cartService.getShippableItems(cart);
      shippingService.processShipment(itemsToShip);

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
}
