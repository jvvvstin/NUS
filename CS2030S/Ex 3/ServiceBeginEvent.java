/**
 * This class encapsulates a Service Begin event in the coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY24/25 Semester 1
 */
class ServiceBeginEvent extends Event {
  /**
   * The customer associated with this Service Begin event.
   * First customer has id 0. Next is 1, 2, etc.
   */
  private Customer customer;

  /**
   * The counter associated with this Service Begin event.
   * This field only matters if the event type is
   * SERVICE_BEGIN or SERVICE_END.
   */
  private Counter counter;
  
  /**
   * The coffee shop associated with this Service Begin event.
   * Contains an array of available counters to serve
   * the customers.
   */
  private CoffeeShop coffeeShop;

  /**
   * Constructor for a Service Begin event.
   *
   * @param time            The time this event occurs.
   * @param customer        The customer associated with this Service Begin 
   *                        event.
   * @param counter         The counter associated with this Service Begin 
   *                        event.
   * @param coffeeShop      The coffee shop associated with this Service
   *                        Begin event.
   */
  public ServiceBeginEvent(double time, Customer customer, Counter counter, 
      CoffeeShop coffeeShop) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.coffeeShop = coffeeShop;
  }
  
  /**
   * Returns the string representation of the Service Begin event.
   * 
   * @return A string representing the Service Begin event.
   */
  @Override
  public String toString() {
    String str = String.format("%s: %s ordered %s (by %s)", 
        super.toString(), this.customer.toString(), 
        this.customer.getCoffeeDrink(), this.counter.toString());
    return str;
  }

  /**
   * The logic that the simulation should follow when simulating
   * this Service Begin event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {
    // updates the availability of the counter from true to false
    this.counter.updateAvailability();

    // calculates the end time of the service
    double endTime = this.customer.calculateEndTime(this.getTime());

    // trigger the next event which is a Service End event
    return new Event[] {
      new ServiceEndEvent(endTime, this.customer, this.counter, 
          this.coffeeShop)
    };
  }
}
