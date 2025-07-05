package com.system.model;

import java.util.Map;

public class Cart {

  private Map<Product, Integer> items;

  public Map<Product, Integer> getItems() {
    return items;
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }
}
