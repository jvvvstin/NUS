/**
 * This class encapsulates a Join coffee shop queue event in the coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY 24/25 Semester 1
 */
class JoinCoffeeShopQueueEvent extends Event {
  /**
   * The customer associated with this Join coffee shop queue event.
   * First customer has id 0. Next is 1, 2 etc.
   */
  private Customer customer;

  /**
   * The coffee shop associated with this Join coffee shop queue event.
   * Contains an array of available counters to serve 
   * the customers.
   */
  private CoffeeShop coffeeShop;

  /**
   * Constructor for a Join coffee shop queue event.
   *
   * @param time            The time this event occurs.
   * @param customer        The customer associated with this
   *                        event.
   * @param coffeeShop      The coffee shop associated with this 
   *                        event.
   */
  public JoinCoffeeShopQueueEvent(double time, Customer customer, 
      CoffeeShop coffeeShop) {
    super(time);
    this.customer = customer;
    this.coffeeShop = coffeeShop;
  }

  /**
   * Returns the string representation of the Join coffee shop queue event.
   *
   * @return A string representing the Join coffee shop queue event
   */
  @Override 
  public String toString() {
    String str = String.format("%s: %s joined queue %s", super.toString(),
        this.customer.toString(), this.coffeeShop.displayQueue());
    return str;
  }

  /**
   * The logic that the simulation should follow when simulating
   * this Join coffee shop queue event.
   *
   * @return An empty array of events to be simulated.
   */
  @Override
  public Event[] simulate() {
    // add the Customer to Coffee Shop Queue
    this.coffeeShop.addCustomerToQueue(this.customer);
    return new Event[] {};
  }
}
