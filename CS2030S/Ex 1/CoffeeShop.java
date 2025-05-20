/**
 * This class encapsulates a Coffee Shop.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY 24/25 Semester 1
 */
class CoffeeShop {
  /**
   * The availability of counters in the coffee shop.
   */
  private Counter[] counters;

  /**
   * Constructor for a Coffee Shop.
   *
   * @param numOfCounters The number of counters in the Coffee Shop.
   */
  public CoffeeShop(int numOfCounters) {
    this.counters = this.generateAvailableCounters(numOfCounters);
  }

  /**
   * Returns an array of available counters of length numOfCounters.
   * e.g. numOfCounters = 5, returns an array of 5 available counters.
   *
   * @param numOfCounters The number of counters in the Coffee Shop.
   * @return An array of available counters of length numOfCounters.
   */
  public Counter[] generateAvailableCounters(int numOfCounters) {
    // generates an array of length numOfCounters
    Counter[] counters = new Counter[numOfCounters];

    // loops through numOfCounters times
    for (int i = 0; i < numOfCounters; i++) {
      // set each index of Counter in the counters array
      // to an available Counter
      counters[i] = new Counter(true);
    }
    return counters;
  }

  /**
   * Returns an instance of an available Counter if there is one.
   *
   * @return An available Counter if there is one.
   */
  public Counter getAvailableCounter() {
    // sets the default value of the available counter to be null
    Counter availCounter = null;

    // loops through all the counters in the coffee shop
    for (int i = 0; i < counters.length; i++) {
      // checks if the counter is available
      if (this.counters[i].getAvailable()) {
        // set the available counter to the current instance
        // if there is an available counter
        availCounter = this.counters[i];
        break;
      }
    }
    return availCounter;
  }
}
