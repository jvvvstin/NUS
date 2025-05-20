/**
 * This class encapsulates a Counter in a coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY24/25 Semester 1
 */
class Counter {
  /**
   * The id of a counter associated with this counter.
   * First counter has id 0. Next is 1, 2, etc.
   */
  private final int counterId;

  /**
   * The availability of the counter in the coffee shop.
   */
  private boolean available;

  /**
   * A counter to keep track of the last counterId instantiated.
   */
  private static int lastCounterId = 0;

  /**
   * Constructor for a Counter.
   *
   * @param available The availability of the counter
   */
  public Counter(boolean available) {
    this.available = available;
    this.counterId = Counter.lastCounterId;
    Counter.lastCounterId++;
  }
  
  /**
   * Returns the availability of the Counter.
   *
   * @return A boolean value representing the availability of 
   *         the counter
   */
  public boolean getAvailable() {
    return this.available;
  }

  /**
   * Updates the availability of a Counter.
   */
  public void updateAvailability() {
    this.available = !available;
  }

  /**
   * Returns the string representation of the Counter
   *
   * @Return A string representing the Counter
   */
  @Override
  public String toString() {
    String str = String.format("B%d", this.counterId);
    return str;
  }
}
