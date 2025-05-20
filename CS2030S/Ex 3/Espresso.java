/**
 * This class encapsulates an Espresso.
 *
 * @author A0308249Y
 * @version CS2030S AY 24/25 Semester 1
 */
class Espresso extends Order {
  /**
   * Constructor for an Espresso.
   *
   * @param instruction The instruction for the Espresso.
   *                    (i.e. Hot, cold etc.)
   */
  public Espresso(String instruction) {
    super(instruction);
  }

  /**
   * Returns the string representation of the Espresso.
   *
   * @return A string representing the Espresso.
   */
  @Override
  public String toString() {
    String str = String.format("%s Espresso", super.toString());
    return str;
  }
}
