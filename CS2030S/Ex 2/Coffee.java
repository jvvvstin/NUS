/**
 * This class encapsulates a Coffee.
 *
 * @author A0308249Y
 * @version CS2030S AY 24/25 Semester 1
 */
class Coffee {
  /**
   * The name of the coffee.
   * i.e. Espresso, Latte
   */
  private String coffeeName;

  /**
   * List of names of coffees.
   * Index 0 is Espresso, 1 is Latte etc.
   */
  private static String[] coffeeNames = new String[] {"Espresso", "Latte"};

  /**
   * Constructor for a Coffee
   *
   * @param coffeeId The index of a Coffee drink.
   */
  public Coffee(int coffeeId) {
    this.coffeeName = Coffee.coffeeNames[coffeeId];
  }

  /**
   * Returns the string representation of the Coffee.
   * 
   * @return A string representing the Coffee.
   */
  @Override
  public String toString() {
    String str = String.format("Coffee %s", this.coffeeName);
    return str;
  }
}
