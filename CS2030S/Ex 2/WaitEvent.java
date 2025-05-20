/**
 * This class encapsulates a Wait event in the coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY 24/25 Semester 1
 */
class WaitEvent extends Event {
  /**
   * The customer associated with this Wait event.
   * First customer has id 0. Next is 1, 2 etc.
   */
  private Customer customer;

  /**
   * The coffee shop associated with this Wait event.
   * Contains an array of available counters to serve 
   * the customers.
   */
  private Queue coffeeShopQueue;

  /**
   * Constructor for a Wait event.
   *
   * @param time            The time this event occurs.
   * @param customer        The customer associated with this
   *                        event.
   * @param coffeeShop      The coffee shop associated with this 
   *                        event.
   * @param coffeeShopQueue The coffee shop queue associated with
   *                        this event.
   */
  public WaitEvent(double time, Customer customer, Queue coffeeShopQueue) {
    super(time);
    this.customer = customer;
    this.coffeeShopQueue = coffeeShopQueue;
  }

  /**
   * Returns the string representation of the Wait event.
   *
   * @return A string representing the Wait event
   */
  @Override 
  public String toString() {
    String str = String.format("%s: %s joined queue %s", super.toString(),
        this.customer.toString(), this.coffeeShopQueue.toString());
    return str;
  }

  /**
   * The logic that the simulation should follow when simulating
   * this Wait event.
   *
   * @return An empty array of events to be simulated.
   */
  @Override
  public Event[] simulate() {
    this.coffeeShopQueue.enq(this.customer);
    return new Event[] {};
  }
}
