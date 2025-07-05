package com.system.interfaces;

import java.time.LocalDate;

public interface IExpirable {
  boolean isExpired();
  LocalDate getExpiryDate();
}
