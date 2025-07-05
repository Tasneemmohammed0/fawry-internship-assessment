package com.system.model;

import com.system.interfaces.IShippable;

public class ShippableProduct extends Product implements IShippable {

  private String name;
  private final double weight;

  public ShippableProduct(
    String name,
    double price,
    Integer quantity,
    double weight
  ) {
    super(name, price, quantity);
    this.weight = weight;
  }

  @Override
  public double getWeight() {
    return weight;
  }
}
