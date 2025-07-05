package com.system.service;

import java.util.List;

import com.system.interfaces.IShippable;

public class ShippingService {

  // ASSUMPTION: 5 unites of curreny per kg
  private static final double SHIPPING_FEE_PER_KG = 5.0;

  /**
   * Calculates the shipping cost based on the weight of the item.
   *
   * @param weight The weight of the item in kilograms.
   * @return The calculated shipping cost.
   * @throws IllegalArgumentException if the weight is less than or equal to zero.
   */
  public double calculateShippingCost(double weight) {
    if (weight <= 0) {
      throw new IllegalArgumentException("Weight must be greater than zero.");
    }
    return weight * SHIPPING_FEE_PER_KG;
  }

  /**
   * Processes the shipment of a list of shippable items.
   *
   * @param items The list of items to be shipped.
   */
  public void processShipment(List<IShippable> items) {
    for (IShippable item : items) {
      System.out.println(
        "Preparing to ship item: " +
        item.getName() +
        " with weight: " +
        item.getWeight() +
        " kg"
      );
    }
  }
}
