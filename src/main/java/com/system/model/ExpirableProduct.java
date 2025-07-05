package com.system.model;

import java.time.LocalDate;

import com.system.interfaces.IExpirable;

public class ExpirableProduct extends Product implements IExpirable {

  private LocalDate expiryDate;

  public ExpirableProduct(
    String name,
    double price,
    Integer quantity,
    LocalDate expiryDate
  ) {
    super(name, price, quantity);
    this.expiryDate = expiryDate;
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
