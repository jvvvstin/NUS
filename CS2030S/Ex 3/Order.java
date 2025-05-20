/**
 * This class encapsulates an Order.
 *
 * @author A0308249Y
 * @version CS2030S AY 24/25 Semester 1
 */
abstract class Order {
  /**
   * The instruction for the Order.
   * i.e. (Hot, cold etc.)
   */
  private String instruction;  
  
  /**
   * Constructor for an Order.
   *
   * @param instruction The instruction for the Order.
   *                    (i.e. Hot, cold etc.)
   */
  public Order(String instruction) {
    this.instruction = instruction;
  }

  /**
   * Factory method to produce the appropriate Order, either Espresso or Latte.
   *
   * @param order       The index of the Order drink.
   *                    Index 0 is Espresso, 1 is Latte etc.
   * @param instruction The instruction for the Order.
   */
  public static Order fromInt(int order, String instruction) {
    if (order == 0) {
      return new Espresso(instruction);
    } else {
      return new Latte(instruction);
    }
  }

  /**
   * Returns the string representation of the Order.
   * 
   * @return A string representing the Order.
   */
  @Override
  public String toString() {
    String str = String.format("(%s) Coffee", this.instruction);
    return str;
  }
}
