/**
 * This class encapsulates a Counter in a coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY24/25 Semester 1
 */
class Counter implements Comparable<Counter> {
  /**
   * The id of a counter associated with this counter.
   * First counter has id 0. Next is 1, 2, etc.
   */
  private final int counterId;

  /**
   * The availability of the Counter in the Coffee Shop.
   */
  private boolean available;

  /**
   * A counter to keep track of the last counterId instantiated.
   */
  private static int lastCounterId = 0;

  /**
   * The Counter Queue associated with this Counter.
   */
  private CoffeeQueue<Customer> counterQueue;

  /**
   * Constructor for a Counter.
   *
   * @param available The availability of the counter.
   * @param maxLength The max length of the Counter Queue.
   */
  public Counter(boolean available, int maxLength) {
    this.available = available;
    this.counterId = Counter.lastCounterId;
    Counter.lastCounterId++;
    this.counterQueue = new CoffeeQueue<Customer>(maxLength);
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
   * Checks if the Counter Queue is full.
   *
   * @return A boolean value representing whether the Counter Queue is full.
   */
  public boolean isCounterQueueFull() {
    return this.counterQueue.isFull();
  }

  /**
   * Checks if the Counter Queue is empty.
   *
   * @return A boolean value representing whether the Counter Queue is empty.
   */
  public boolean isCounterQueueEmpty() {
    return this.counterQueue.isEmpty();
  }
  
  /**
   * Add the Customer to the Counter Queue of this Counter.
   *
   * @param c Customer to be added to the Counter Queue.
   * @return A boolean value representing whether the Customer was successfully
   *         added to the Counter Queue or not.
   */
  public boolean addCustomerToCounterQueue(Customer c) {
    return this.counterQueue.enq(c);
  }

  /**
   * Retrieves the Customer that is next in the Queue.
   *
   * @return A Customer that is to be served next.
   */
  public Customer getNextCustomerInCounterQueue() {
    return (Customer) this.counterQueue.deq();
  }
  
  /**
   * Compares two Counter according to the size.
   * If result is negative, it means this < other.
   * If result is positive, it means that this > other.
   * Otherwise, they are compared based on their Counter ID.
   *
   * @param other The other Counter that it is being compared to.
   * @return The difference of the two sizes. 
   */
  @Override 
  public int compareTo(Counter other) {
    int difference = this.counterQueue.compareTo(other.counterQueue);
    if (difference == 0) {
      return Integer.valueOf(this.counterId).compareTo(
          Integer.valueOf(other.counterId));
    }
    return difference;
  }

  /**
   * Returns the string representation of the Counter
   *
   * @Return A string representing the Counter
   */
  @Override
  public String toString() {
    String str = String.format("B%d %s", this.counterId, 
        this.counterQueue.toString());
    return str;
  }
}
