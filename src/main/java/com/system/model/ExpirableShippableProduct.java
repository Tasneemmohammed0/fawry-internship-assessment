package com.system.model;

import java.time.LocalDate;

import com.system.interfaces.IExpirable;
import com.system.interfaces.IShippable;

public class ExpirableShippableProduct
  extends Product
  implements IShippable, IExpirable {

  private double weight;
  private LocalDate expiryDate;

  public ExpirableShippableProduct(
    String name,
    double price,
    Integer quantity,
    double weight,
    LocalDate expiryDate
  ) {
    super(name, price, quantity);
    this.weight = weight;
    this.expiryDate = expiryDate;
  }

  @Override
  public double getWeight() {
    return weight;
  }

  @Override
  public LocalDate getExpiryDate() {
    return expiryDate;
  }

  @Override
  public boolean isExpired() {
    return LocalDate.now().isAfter(expiryDate);
  }
}
