package com.system.interfaces;

import java.util.Date;

public interface IExpirable {
  boolean isExpired();
  Date getExpiryDate();
}
