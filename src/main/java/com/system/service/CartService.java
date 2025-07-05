import com.system.model.Cart;
import com.system.model.Product;

/**
 * CartService class provides methods to manage the shopping cart.
 * It allows adding and removing products from the cart with appropriate checks.
 */
public class CartService {

  /**
   * Method to add a product to the cart
   *
   * @param cart - the cart to which the product will be added
   * @param product - the product to be added
   * @param quantity - the quantity of the product to be added
   *
   * @throws IllegalArgumentException if the quantity is negative or if the product is out of stock
   */
  public void addProductToCart(Cart cart, Product product, int quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }

    int currentQuantityInCart = cart.getItems().getOrDefault(product, 0);
    int newTotalQuantity = currentQuantityInCart + quantity;

    if (!product.isInStock(quantity)) {
      throw new IllegalArgumentException("Product is out of stock");
    }

    cart.getItems().put(product, newTotalQuantity);
    System.out.println(
      "Added product: " +
      product.getName() +
      " with quantity: " +
      quantity +
      " to the cart."
    );
  }

  /**
   * Method to remove a product from the cart
   *
   * @param cart - the cart from which the product will be removed
   * @param product - the product to be removed
   * @param quantity - the quantity of the product to be removed
   */
  public void removeProductFromCart(Cart cart, Product product, int quantity) {
    if (cart.isEmpty()) {
      throw new IllegalArgumentException("Cart is empty");
    }

    if (!cart.getItems().containsKey(product)) {
      throw new IllegalArgumentException("Product not found in the cart");
    }

    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }

    // Check if the quantity to remove is greater than the current quantity in the cart
    int currentQuantityInCart = cart.getItems().get(product);
    if (currentQuantityInCart < quantity) {
      throw new IllegalArgumentException(
        "Not enough quantity in the cart to remove"
      );
    }

    // Calculate the new quantity after removal and update the cart
    int newQuantity = currentQuantityInCart - quantity;
    if (newQuantity == 0) {
      cart.getItems().remove(product);
      System.out.println(
        "Removed product: " + product.getName() + " from the cart."
      );
    } else {
      cart.getItems().put(product, newQuantity);
      System.out.println(
        "Removed " +
        quantity +
        " of product: " +
        product.getName() +
        " from the cart. Remaining quantity: " +
        newQuantity
      );
    }
  }
}
