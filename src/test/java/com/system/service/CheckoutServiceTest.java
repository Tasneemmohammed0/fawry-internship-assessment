package com.system.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.system.model.Cart;
import com.system.model.Customer;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceTest {

  @Mock
  private CartService mockCartService;

  @Mock
  private ShippingService mockShippingService;

  @InjectMocks
  private CheckoutService checkoutService;

  private Cart cart;
  private Customer customer;

  @BeforeEach
  void setUp() {
    cart = new Cart();
    customer = new Customer("Tasneem", 1000.0);
  }

  @Test
  void checkout_WhenCustomerCanAfford_ShouldProcessCheckout() {
    when(mockCartService.calculateTotalPrice(cart)).thenReturn(500.0);
    when(mockCartService.calculateTotalWeight(cart)).thenReturn(10.0);
    when(mockShippingService.calculateShippingCost(10.0)).thenReturn(50.0);

    checkoutService.checkout(customer, cart);

    assertEquals(450.0, customer.getBalance());
    verify(mockShippingService, times(1)).processShipment(any(List.class));
  }

  @Test
  void checkout_WhenCustomerCannotAfford_ShouldNotProcessCheckout() {
    when(mockCartService.calculateTotalPrice(cart)).thenReturn(1500.0);
    when(mockCartService.calculateTotalWeight(cart)).thenReturn(10.0);
    when(mockShippingService.calculateShippingCost(10.0)).thenReturn(50.0);

    checkoutService.checkout(customer, cart);

    // Balance shouldn't change since customer can't afford the total cost
    assertEquals(1000.0, customer.getBalance());
    verify(mockShippingService, never()).processShipment(any(List.class));
  }
}
