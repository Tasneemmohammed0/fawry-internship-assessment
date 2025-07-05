package com.system.model;

public class Product {

  private String name;
  private double price;
  private Integer quantity;

  public Product(String name, double price, Integer quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public Boolean isInStock(Integer quantity) {
    return this.quantity >= quantity;
  }
}
