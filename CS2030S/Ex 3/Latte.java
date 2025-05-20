/**
 * This class encapsulates a Latte.
 *
 * @author A0308249Y
 * @version CS2030S AY 24/25 Semester 1
 */
class Latte extends Order {
  /**
   * Constructor for a Latte.
   *
   * @param instruction The instruction for the Latte
   *                    (i.e. Hot, cold etc.)
   */
  public Latte(String instruction) {
    super(instruction);
  }

  /**
   * Returns the string representation of the Latte.
   *
   * @return A string representing the Latte.
   */
  @Override
  public String toString() {
    String str = String.format("%s Latte", super.toString());
    return str;
  }
}
