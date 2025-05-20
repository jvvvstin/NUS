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
  private Seq<Counter> counters;
  
  /**
   * The queue of the coffee shop.
   */
  private CoffeeQueue<Customer> coffeeQueue;
  
  /**
   * Constructor for a Coffee Shop.
   *
   * @param numOfCounters The number of counters in the Coffee Shop.
   * @param coffeeQueue   The queue of the Coffee Shop.
   */
  public CoffeeShop(int numOfCounters, int maxLength, 
      CoffeeQueue<Customer> coffeeQueue) {
    this.counters = this.generateAvailableCounters(numOfCounters, maxLength);
    this.coffeeQueue = coffeeQueue;
  }

  /**
   * Returns an array of available counters of length numOfCounters.
   * e.g. numOfCounters = 5, returns an array of 5 available counters.
   *
   * @param numOfCounters The number of counters in the Coffee Shop.
   * @return An array of available counters of length numOfCounters.
   */
  public Seq<Counter> generateAvailableCounters(int numOfCounters, 
      int maxLength) {
    // generates an array of length numOfCounters
    Seq<Counter> counters = new Seq<Counter>(numOfCounters);

    // loops through numOfCounters times
    for (int i = 0; i < numOfCounters; i++) {
      // set each index of Counter in the counters array
      // to an available Counter
      counters.set(i, new Counter(true, maxLength));
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
    for (int i = 0; i < counters.getNumOfCounters(); i++) {
      // checks if the counter is available
      if (this.counters.get(i).getAvailable()) {
        // set the available counter to the current instance
        // if there is an available counter
        availCounter = this.counters.get(i);
        break;
      }
    }
    return availCounter;
  }

  public boolean areCountersQueuesFull() {
    return this.counters.areCountersQueuesFull();
  }

  public Counter getShortestQueueCounter() {
    return this.counters.min();
  }

  /**
   * Returns the string representation of the queue in the coffee shop.
   *
   * @return A string consisting of the string representation of every
   *         customer in the queue.
   */
  public String displayQueue() {
    return this.coffeeQueue.toString();
  }
  
  public String displayCounterQueue(int index) {
    return this.counters.get(index).toString();
  }

  /**
   * Checks if the queue in the coffee shop is full.
   *
   * @return true if the queue is full; false otherwise.
   */
  public boolean isQueueFull() {
    return this.coffeeQueue.isFull();
  }

  /**
   * Checks if the queue in the coffee shop is empty.
   *
   * @return true if the queue is empty; false otherwise.
   */
  public boolean isQueueEmpty() {
    return this.coffeeQueue.isEmpty();
  }

  /**
   * Retrieves the next customer that is in the queue.
   *
   * @return null if the queue is empty;
   *         the customer that is next in queue otherwise.
   */
  public Customer getNextCustomerInQueue() {
    return (Customer) this.coffeeQueue.deq();
  }

  /**
   * Add the Customer customer into the queue.
   *
   * @param customer The customer that is to added to the queue.
   * @return false if the queue is full;
   *         true if customer is addedd successfully.
   */
  public boolean addCustomerToQueue(Customer customer) {
    return this.coffeeQueue.enq(customer);
  }
}
