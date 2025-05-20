/**
 * This class encapsulates a Service End event in the coffee shop
 * simulation.
 *
 * @author A0308249Y (Group 14B)
 * @version CS2030S AY24/25 Semester 1
 */
class ServiceEndEvent extends Event {
  /**
   * The customer associated with this Service End event.
   * First customer has id 0. Next is 1, 2 etc.
   */
  private Customer customer;

  /**
   * The counter associated with this Service End event.
   * This field only matters if the event type is
   * SERVICE_BEGIN or SERVICE_END.
   */
  private Counter counter;
  
  /**
   * The coffee shop associated with this Service End event.
   * Contains an array of available counters to serve
   * the customers.
   */
  private CoffeeShop coffeeShop;

  /**
   * Constructor for a Service End event.
   *
   * @param time            The time this event occurs.
   * @param customer        The customer associated with this Service End event.
   * @param counter         The counter associated with this Service End event.
   * @param coffeeShop      The coffee shop associated with this Service 
   *                        End event.
   */
  public ServiceEndEvent(double time, Customer customer, Counter counter, 
      CoffeeShop coffeeShop) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.coffeeShop = coffeeShop;
  }

  /**
   * Returns the string representation of the Service End event.
   *
   * @return A string representing the Service End event.
   */
  @Override
  public String toString() {
    String str = String.format("%s: %s served %s (by %s)",
        super.toString(), this.customer.toString(), 
        this.customer.getCoffeeDrink(), this.counter.toString());
    return str;
  }

  /**
   * The logic that the simulation should follow when simulating
   * this Service End event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {
    // updates the availability of the counter from false to true
    this.counter.updateAvailability();
    
    // checks if there is a waiting Customer at the Counter Queue
    if (!this.counter.isCounterQueueEmpty()) {
      // retrieves the next Customer in the Counter Queue
      Customer nextServeCust = this.counter.getNextCustomerInCounterQueue();
      // checks if the Coffee Shop Queue is not empty (i.e. there is a Customer
      // waiting to join the Counter Queue)
      if (!this.coffeeShop.isQueueEmpty()) {
        // if Coffee Shop Queue is not empty, retrieve the first Customer in 
        // the queue
        Customer nextCounterCust = this.coffeeShop.getNextCustomerInQueue();
        double reachQueueTime = this.getTime() + 0.05;
        // trigger the next sequence of events
        // 1. Departure of original Customer
        // 2. Service Begin of next Customer
        // 3. Join Counter Queue of the first Customer in entrance Queue
        return new Event[] {
          // Departure of original Customer
          new DepartureEvent(this.getTime(), this.customer),
          // Service Begin of the new Customer that was previously first in the 
          // Counter Queue
          new ServiceBeginEvent(this.getTime(), nextServeCust, this.counter, 
              this.coffeeShop),
          // Join Counter Queue of the Customer that was previously first in the
          // entrance Queue
          new JoinCounterQueueEvent(reachQueueTime, nextCounterCust, 
              this.coffeeShop)
        };
      } else {
        // else, (Coffee Shop Queue is empty i.e. has no Customer to join the 
        // Counter Queue)
        // trigger the next sequence of events
        // 1. Departure of original Customer
        // 2. Service Begin of next Customer
        return new Event[] {
          // Departure of original Customer
          new DepartureEvent(this.getTime(), this.customer),
          // Service Begin of the new Customer, that was previously first in the
          // Counter Queue
          new ServiceBeginEvent(this.getTime(), nextServeCust, this.counter, 
              this.coffeeShop)
        };
      }
    } else {
      // else, (Counter Queue is empty i.e. has no Customer in the Counter Queue
      // to be served)
      // then, check if the Coffee Shop Queue is not empty (i.e. has a Customer
      // waiting at the Coffee Shop Queue)
      if (!this.coffeeShop.isQueueEmpty()) {
        // retrieves the next Customer in the Coffee Shop Queue
        Customer nextServeCust = this.coffeeShop.getNextCustomerInQueue();
        // trigger the next sequence of events
        // 1. Departure of original Customer
        // 2. Service Begin of next Customer
        return new Event[] {
          new DepartureEvent(this.getTime(), this.customer),
          new ServiceBeginEvent(this.getTime(), nextServeCust, this.counter, 
              this.coffeeShop)
        };
      }

      // if the above block did not run, means that there are no Customers
      // waiting at both the Counter and Coffee Shop Queue
      return new Event[] {
        // thus, just trigger the Departure of the original Customer
        new DepartureEvent(this.getTime(), this.customer)
      };
    }
  }
}
