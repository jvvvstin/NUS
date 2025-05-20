/**
 * This class encapsulates a Join counter queue event in the coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY 24/25 Semester 1
 */
class JoinCounterQueueEvent extends Event {
  /**
   * The customer associated with this Join counter queue event.
   * First customer has id 0. Next is 1, 2 etc.
   */
  private Customer customer;

  /**
   * The coffee shop associated with this Join counter queue event.
   * Contains an array of available counters to serve 
   * the customers.
   */
  private CoffeeShop coffeeShop;

  /**
   * Constructor for a Join counter queue event.
   *
   * @param time            The time this event occurs.
   * @param customer        The customer associated with this
   *                        event.
   * @param coffeeShop      The coffee shop associated with this 
   *                        event.
   */
  public JoinCounterQueueEvent(double time, Customer customer, 
      CoffeeShop coffeeShop) {
    super(time);
    this.customer = customer;
    this.coffeeShop = coffeeShop;
  }

  /**
   * Returns the string representation of the Join counter queue event.
   *
   * @return A string representing the Join counter queue event
   */
  @Override 
  public String toString() {
    Counter counter = this.coffeeShop.getShortestQueueCounter();
    String str = String.format("%s: %s joined barista queue (at %s)", 
        super.toString(), this.customer.toString(), 
        counter.toString());
    return str;
  }

  /**
   * The logic that the simulation should follow when simulating
   * this JoinCounterQueue event.
   *
   * @return An empty array of events to be simulated.
   */
  @Override
  public Event[] simulate() {
    // retrieves the Counter with the shortest Queue
    Counter counter = this.coffeeShop.getShortestQueueCounter();

    // add the Customer to the Counter Queue
    counter.addCustomerToCounterQueue(this.customer);
    return new Event[] {};
  }
}
