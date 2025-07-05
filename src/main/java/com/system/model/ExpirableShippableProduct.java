import java.util.Date;

import com.system.interfaces.IExpirable;
import com.system.interfaces.IShippable;
import com.system.model.Product;

public class ExpirableShippableProduct
  extends Product
  implements IShippable, IExpirable {

  private double weight;
  private Date expiryDate;

  public ExpirableShippableProduct(
    String name,
    double price,
    Integer quantity,
    double weight,
    Date expiryDate
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
  public Date getExpiryDate() {
    return expiryDate;
  }

  @Override
  public boolean isExpired() {
    return new Date().after(expiryDate);
  }
}
